-- Create booking status enum type
CREATE TYPE booking_status AS ENUM (
   'AVAILABLE',
   'IN_PROGRESS',
   'BOOKED'
);
-- Create bookings table
CREATE TABLE bookings (
   id BIGSERIAL PRIMARY KEY,
   user_id BIGINT NOT NULL,
   screening_id BIGINT NOT NULL,
   seat_number VARCHAR(10) NOT NULL,
   status booking_status NOT NULL,
   created_at TIMESTAMP NOT NULL,
   price DECIMAL(10, 2) NOT NULL,
   FOREIGN KEY (screening_id) REFERENCES screenings(id) ON DELETE CASCADE
);
-- Create indexes for bookings
CREATE INDEX idx_bookings_user_id ON bookings(user_id);
CREATE INDEX idx_bookings_screening_id ON bookings(screening_id);
CREATE INDEX idx_bookings_status ON bookings(status);