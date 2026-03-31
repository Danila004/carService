create table AuthUsers (
    auth_user_id int primary key,
    user_name varchar(20),
    surname varchar(20),
    phone_number varchar(10) unique,
    password_hash varchar(64),
    user_type varchar(10)
);

create table UnAuthUsers (
    unauth_user_id int primary key,
    user_name varchar(20),
    surname varchar(20),
    phone_number varchar(10) unique
);

create table DateSlots (
    slot_id int primary key,
    visit_date timestamp,
    auth_user_id int references AuthUsers(auth_user_id),
    unauth_user_id int references UnAuthUsers(unauth_user_id),
    master_id int references AuthUsers(auth_user_id)
);

create table Brands (
    brand_id int primary key,
    brand_name varchar(20)
);

create table Models (
    model_id int primary key,
    model_name varchar(20),
    release_date date,
    brand_id int references Brands(brand_id)
);

create table Services (
    service_id int primary key,
    service_name varchar(100),
    description varchar(500)
);

create table Orders(
    order_id int primary key,
    visit_date timestamp,
    order_type varchar(15),
    auth_user_id int references AuthUsers(auth_user_id),
    unauth_user_id int references UnAuthUsers(unauth_user_id),
    master_id int references AuthUsers(auth_user_id),
    brand_id int references Brands(brand_id),
    model_id int references Models(model_id)
);

create table ServicesInOrder (
    service_id int,
    order_id int,

    primary key (service_id, order_id),
    foreign key (service_id) references Services(service_id),
    foreign key (order_id) references Orders(order_id)
);

create table Prices (
    service_id int,
    model_id int,
    price numeric(9,2),
    cycle_time interval,

    primary key (service_id, model_id),
    foreign key (service_id) references Services(service_id),
    foreign key (model_id) references Models(model_id)
);