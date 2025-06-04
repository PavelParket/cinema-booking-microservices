-- Create seat type enum
CREATE TYPE seat_type AS ENUM (
   'STANDARD',
   'VIP'
);
-- Create seats table
CREATE TABLE seat_templates (
    id BIGSERIAL PRIMARY KEY,
    seat_number VARCHAR(10) NOT NULL,
    row INTEGER NOT NULL,
    available BOOLEAN NOT NULL,
    seat_type seat_type NOT NULL,
    price_multiplier DECIMAL(3, 2) NOT NULL,
);
