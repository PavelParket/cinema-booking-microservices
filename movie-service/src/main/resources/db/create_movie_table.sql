CREATE SEQUENCE IF NOT EXISTS movies_id_seq;

CREATE TABLE IF NOT EXISTS movies (
   id BIGINT PRIMARY KEY DEFAULT nextval('movies_id_seq'),
   title VARCHAR(255) NOT NULL,
   duration_minutes INTEGER NOT NULL,
   director VARCHAR(255) NOT NULL,
   release DATE NOT NULL,
   country VARCHAR(255) NOT NULL,
   age_rating INTEGER NOT NULL
);