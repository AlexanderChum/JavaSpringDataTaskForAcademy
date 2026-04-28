CREATE TABLE books (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    publication_year INT NOT NULL
);