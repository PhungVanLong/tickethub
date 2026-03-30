# Entity Relationships

## USERS_REF Entity
- **One-to-Many** with EVENTS (as organizer)
- **One-to-Many** with EVENT_APPROVALS (as admin)
- **One-to-Many** with EVENT_STAFF (as staff)

## EVENTS Entity
- **Many-to-One** with USERS_REF (organizer_id)
- **One-to-Many** with ANALYTICS_EVENTS
- **One-to-Many** with EVENT_STAFF
- **One-to-Many** with SEAT_MAPS
- **One-to-Many** with EVENT_APPROVALS

## EVENT_APPROVALS Entity
- **Many-to-One** with EVENTS (event_id)
- **Many-to-One** with USERS_REF (admin_id)

## EVENT_STAFF Entity (Composite Key: event_id + staff_id)
- **Many-to-One** with EVENTS (event_id)
- **Many-to-One** with USERS_REF (staff_id)

## SEAT_MAPS Entity
- **Many-to-One** with EVENTS (event_id)
- **One-to-Many** with TICKET_TIERS
- **One-to-Many** with SEATS

## SEATS Entity
- **Many-to-One** with SEAT_MAPS (seat_map_id)
- **Many-to-One** with TICKET_TIERS (ticket_tier_id)

## TICKET_TIERS Entity
- **Many-to-One** with SEAT_MAPS (seat_map_id)
- **One-to-Many** with SEATS

## ANALYTICS_EVENTS Entity
- **Many-to-One** with EVENTS (event_id)

## Relationship Diagram
```
USERS_REF (1) -----> (N) EVENTS (organizer)
USERS_REF (1) -----> (N) EVENT_APPROVALS (admin)
USERS_REF (1) -----> (N) EVENT_STAFF (staff)

EVENTS (1) -----> (N) ANALYTICS_EVENTS
EVENTS (1) -----> (N) EVENT_STAFF
EVENTS (1) -----> (N) SEAT_MAPS
EVENTS (1) -----> (N) EVENT_APPROVALS

SEAT_MAPS (1) -----> (N) TICKET_TIERS
SEAT_MAPS (1) -----> (N) SEATS

TICKET_TIERS (1) -----> (N) SEATS
```
