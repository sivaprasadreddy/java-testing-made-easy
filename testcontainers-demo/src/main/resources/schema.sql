create table if not exists customers (
     id bigserial not null,
     name varchar not null,
     email varchar not null,
     password varchar not null,
     primary key (id),
     UNIQUE (email)
);