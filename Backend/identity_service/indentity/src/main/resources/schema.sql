CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    phone VARCHAR(50),
    avatar_url VARCHAR(500),
    role VARCHAR(32) NOT NULL CHECK (role IN ('ROLE_GUEST', 'ROLE_CUSTOMER', 'ROLE_ORGANIZER', 'ROLE_STAFF', 'ROLE_ADMIN')),
    is_verified BOOLEAN NOT NULL DEFAULT FALSE,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS vouchers (
    id UUID PRIMARY KEY,
    organizer_id UUID REFERENCES users(id),
    code VARCHAR(100) UNIQUE NOT NULL,
    discount_type VARCHAR(16) NOT NULL CHECK (discount_type IN ('PERCENT', 'FIXED')),
    discount_value NUMERIC(18, 2) NOT NULL,
    min_order_value NUMERIC(18, 2),
    usage_limit INT,
    used_count INT NOT NULL DEFAULT 0,
    valid_from TIMESTAMP NOT NULL,
    valid_until TIMESTAMP NOT NULL,
    apply_on VARCHAR(32) NOT NULL CHECK (apply_on IN ('ORIGINAL_PRICE', 'AFTER_PROMOTION')),
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS voucher_usages (
    id UUID PRIMARY KEY,
    voucher_id UUID NOT NULL REFERENCES vouchers(id),
    order_id UUID NOT NULL,
    user_id UUID REFERENCES users(id),
    discount_applied NUMERIC(18, 2) NOT NULL,
    used_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS captcha_logs (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    action VARCHAR(255) NOT NULL,
    token VARCHAR(1000) NOT NULL,
    passed BOOLEAN NOT NULL,
    score REAL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS system_logs (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES users(id),
    action VARCHAR(255) NOT NULL,
    entity_type VARCHAR(255),
    entity_id UUID,
    metadata JSONB,
    ip_address VARCHAR(100),
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS platform_config (
    id UUID PRIMARY KEY,
    config_key VARCHAR(255) UNIQUE NOT NULL,
    config_value TEXT NOT NULL,
    description TEXT,
    updated_by UUID REFERENCES users(id),
    updated_at TIMESTAMP NOT NULL
);
