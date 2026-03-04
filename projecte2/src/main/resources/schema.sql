drop table if exists product;

create table product(
    id bigint auto_increment primary key,
    name varchar(20) not null,
    description varchar(100) null,
    stock int not null,
    price decimal not null,
    rating decimal null,
    condition enum('nou','bon_estat','acceptable','mal estat'),
    status boolean default false,
    dataCreated timestamp default current_timestamp,
    dataUpdated timestamp default current_timestamp on update current_timestamp
);