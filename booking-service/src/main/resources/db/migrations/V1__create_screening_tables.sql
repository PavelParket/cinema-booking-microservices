-- Create screening status enum type
CREATE TYPE screening_status AS ENUM (
   'SCHEDULED',
   'IN_PROGRESS',
   'COMPLETED',
   'CANCELLED'
);
-- Create screenings table
CREATE TABLE screenings (
   id BIGSERIAL PRIMARY KEY,
   movie_id BIGINT NOT NULL,
   start_time TIMESTAMP NOT NULL,
   duration INTERVAL NOT NULL,
   hall VARCHAR(255) NOT NULL,
   base_price DECIMAL(10, 2) NOT NULL,
   status screening_status NOT NULL
);
-- Create indexes for screenings
CREATE INDEX idx_screenings_movie_id ON screenings(movie_id);
CREATE INDEX idx_screenings_start_time ON screenings(start_time);