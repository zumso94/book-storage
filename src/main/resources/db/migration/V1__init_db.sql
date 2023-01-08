Create table Users(
    id serial not null primary key,
    user_name varchar(128) not null,
    email varchar(128) not null,
    password varchar(256) not null,
    user_role varchar(128) not null,
    status varchar(128) not null,
    activation_uuid varchar(128)
);

INSERT INTO Users (user_name, email, password, user_role, status, activation_uuid)
VALUES ('Admin', 'admin@gmail.com', '$2a$10$Zxbc.3EQHksXp0MytQTRVuDgecKWkOs/OcqgoRb121I8jxWm4N56O', 'ADMIN', 'ACTIVE', null);
