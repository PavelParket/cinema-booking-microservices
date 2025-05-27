CREATE SEQUENCE IF NOT EXISTS movie_genres_id_seq;

CREATE TABLE IF NOT EXISTS movie_genres (
   id BIGINT PRIMARY KEY DEFAULT nextval('movie_genres_id_seq'),
   movie_id BIGINT NOT NULL,
   genre_id BIGINT NOT NULL,
   FOREIGN KEY (movie_id) REFERENCES movies(id) ON DELETE CASCADE,
   FOREIGN KEY (genre_id) REFERENCES genres(id) ON DELETE CASCADE
);