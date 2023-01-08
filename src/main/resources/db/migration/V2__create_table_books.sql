CREATE TABLE Books(
    id serial not null primary key,
    title varchar(255) not null,
    author varchar(128) not null,
    genre varchar(128),
    access varchar(128) not null,
    book_path varchar(255),
    user_name varchar(128) not null
);