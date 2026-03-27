-- One-time migration for legacy identity-service schema.
-- Run this script manually on the existing database before starting app.

CREATE EXTENSION IF NOT EXISTS pgcrypto;

BEGIN;

-- users.id: varchar -> uuid (preserve values when valid UUID text)
ALTER TABLE users ADD COLUMN IF NOT EXISTS id_new UUID;
UPDATE users
SET id_new = CASE
    WHEN id::text ~* '^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$' THEN id::uuid
    ELSE gen_random_uuid()
END
WHERE id_new IS NULL;

-- legacy column names -> new contract
ALTER TABLE users ADD COLUMN IF NOT EXISTS password_hash VARCHAR(255);

ALTER TABLE users ADD COLUMN IF NOT EXISTS full_name VARCHAR(255);

ALTER TABLE users ADD COLUMN IF NOT EXISTS avatar_url VARCHAR(500);

ALTER TABLE users ADD COLUMN IF NOT EXISTS role VARCHAR(32);
UPDATE users SET role = COALESCE(role, roles) WHERE role IS NULL;
UPDATE users SET role = 'ROLE_CUSTOMER' WHERE role IS NULL;

ALTER TABLE users ADD COLUMN IF NOT EXISTS is_verified BOOLEAN;
UPDATE users SET is_verified = COALESCE(is_verified, false) WHERE is_verified IS NULL;

ALTER TABLE users ADD COLUMN IF NOT EXISTS is_active BOOLEAN;
UPDATE users SET is_active = COALESCE(is_active, true) WHERE is_active IS NULL;

ALTER TABLE users ADD COLUMN IF NOT EXISTS created_at TIMESTAMP;
UPDATE users SET created_at = COALESCE(created_at, now()) WHERE created_at IS NULL;

ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_at TIMESTAMP;
UPDATE users SET updated_at = COALESCE(updated_at, now()) WHERE updated_at IS NULL;

-- Migrate references to users.id in related tables.
ALTER TABLE captcha_logs ADD COLUMN IF NOT EXISTS user_id_new UUID;
UPDATE captcha_logs c
SET user_id_new = u.id_new
FROM users u
WHERE c.user_id::text = u.id::text AND c.user_id_new IS NULL;

ALTER TABLE system_logs ADD COLUMN IF NOT EXISTS user_id_new UUID;
UPDATE system_logs s
SET user_id_new = u.id_new
FROM users u
WHERE s.user_id::text = u.id::text AND s.user_id_new IS NULL;

ALTER TABLE vouchers ADD COLUMN IF NOT EXISTS organizer_id_new UUID;
UPDATE vouchers v
SET organizer_id_new = u.id_new
FROM users u
WHERE v.organizer_id::text = u.id::text AND v.organizer_id_new IS NULL;

ALTER TABLE vouchers_usages ADD COLUMN IF NOT EXISTS user_id_new UUID;
UPDATE vouchers_usages vu
SET user_id_new = u.id_new
FROM users u
WHERE vu.user_id::text = u.id::text AND vu.user_id_new IS NULL;

ALTER TABLE platform_config ADD COLUMN IF NOT EXISTS updated_by_new UUID;
UPDATE platform_config p
SET updated_by_new = u.id_new
FROM users u
WHERE p.updated_by::text = u.id::text AND p.updated_by_new IS NULL;

-- Replace legacy PK/FK columns with migrated UUID columns.
ALTER TABLE users DROP CONSTRAINT IF EXISTS users_pkey;
ALTER TABLE users DROP COLUMN IF EXISTS id;
ALTER TABLE users RENAME COLUMN id_new TO id;
ALTER TABLE users ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE captcha_logs DROP COLUMN IF EXISTS user_id;
ALTER TABLE captcha_logs RENAME COLUMN user_id_new TO user_id;

ALTER TABLE system_logs DROP COLUMN IF EXISTS user_id;
ALTER TABLE system_logs RENAME COLUMN user_id_new TO user_id;

ALTER TABLE vouchers DROP COLUMN IF EXISTS organizer_id;
ALTER TABLE vouchers RENAME COLUMN organizer_id_new TO organizer_id;

ALTER TABLE vouchers_usages DROP COLUMN IF EXISTS user_id;
ALTER TABLE vouchers_usages RENAME COLUMN user_id_new TO user_id;

ALTER TABLE platform_config DROP COLUMN IF EXISTS updated_by;
ALTER TABLE platform_config RENAME COLUMN updated_by_new TO updated_by;

-- Normalize role and add required constraints.
UPDATE users
SET role = CASE UPPER(role)
    WHEN 'ADMIN' THEN 'ROLE_ADMIN'
    WHEN 'CUSTOMER' THEN 'ROLE_CUSTOMER'
    WHEN 'ORGANIZER' THEN 'ROLE_ORGANIZER'
    WHEN 'STAFF' THEN 'ROLE_STAFF'
    WHEN 'GUEST' THEN 'ROLE_GUEST'
    ELSE role
END;

ALTER TABLE users ALTER COLUMN password_hash SET NOT NULL;
ALTER TABLE users ALTER COLUMN role SET NOT NULL;
ALTER TABLE users ALTER COLUMN is_verified SET NOT NULL;
ALTER TABLE users ALTER COLUMN is_active SET NOT NULL;
ALTER TABLE users ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE users ALTER COLUMN updated_at SET NOT NULL;

ALTER TABLE users
    ADD CONSTRAINT users_role_check
    CHECK (role IN ('ROLE_GUEST', 'ROLE_CUSTOMER', 'ROLE_ORGANIZER', 'ROLE_STAFF', 'ROLE_ADMIN'));

ALTER TABLE captcha_logs
    ADD CONSTRAINT fk_captcha_logs_user
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE system_logs
    ADD CONSTRAINT fk_system_logs_user
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE vouchers
    ADD CONSTRAINT fk_vouchers_organizer
    FOREIGN KEY (organizer_id) REFERENCES users(id);

ALTER TABLE vouchers_usages
    ADD CONSTRAINT fk_vouchers_usages_user
    FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE platform_config
    ADD CONSTRAINT fk_platform_config_updated_by
    FOREIGN KEY (updated_by) REFERENCES users(id);

COMMIT;
