CREATE TABLE IF NOT EXISTS movie_genre (
   movie_id BIGINT NOT NULL,
   genre_id BIGINT NOT NULL,
   PRIMARY KEY (movie_id, genre_id),
   FOREIGN KEY (movie_id) REFERENCES movie(id) ON DELETE CASCADE,
   FOREIGN KEY (genre_id) REFERENCES genre(id) ON DELETE CASCADE
);