-- Create seat type enum
CREATE TYPE seat_type AS ENUM (
   'STANDARD',
   'VIP'
);
-- Create seats table
CREATE TABLE seats (
   id BIGSERIAL PRIMARY KEY,
   seat_number INTEGER NOT NULL,
   row INTEGER NOT NULL,
   available BOOLEAN NOT NULL,
   seat_type seat_type NOT NULL,
   price_multiplier DECIMAL(3, 2) NOT NULL,
   screening_id BIGINT NOT NULL,
   FOREIGN KEY (screening_id) REFERENCES screenings(id) ON DELETE CASCADE
);
-- Create index for seats
CREATE INDEX idx_seats_screening_id ON seats(screening_id);
