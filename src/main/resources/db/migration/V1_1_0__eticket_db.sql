create table broken_laws (
    ticket_details_entity_ticket_id int8 not null,
    broken_laws varchar(255)
                         );

create table custom_user (
    user_id  bigserial not null,
    email varchar(255),
    first_name varchar(255),
    last_name varchar(255),
    password varchar(255),
    role varchar(255),
    primary key (user_id)
                         );

create table official (
    official_id  bigserial not null,
    birth_date timestamp,
    official_code varchar(255),
    official_name varchar(255),
    primary key (official_id)
                      );

create table payment (
    commission_rate int4 not null,
    payment_date timestamp,
    total_calculated_payment_with_commission float8 not null,
    total_payment float8 not null,
    ticket_details_entity_ticket_id int8 not null,
    primary key (ticket_details_entity_ticket_id));

create table ticket_details (
    ticket_id  bigserial not null,
    amount float8 not null,
    breaker_full_name varchar(255),
    commission_amount float8,
    is_ticket_detail_paid boolean,
    plate_id varchar(255),
    serial_number varchar(255),
    ticket_date timestamp,
    ticket_place varchar(255),
    ticket_registration_date timestamp,
    vehicle_type varchar(255),
    official_entity_official_id int8,
    primary key (ticket_id)
                            );

alter table custom_user drop constraint if exists UK_4lmxwkfme865tutn7t4k7k3v1;
alter table custom_user add constraint UK_4lmxwkfme865tutn7t4k7k3v1 unique (email);
alter table official drop constraint if exists UK_i5nfc80drlv3h52rn8nyhoua4;
alter table official add constraint UK_i5nfc80drlv3h52rn8nyhoua4 unique (official_code);
alter table ticket_details drop constraint if exists  UK_90i17vr27d9rpoml1cn50ti9l;
alter table ticket_details add constraint UK_90i17vr27d9rpoml1cn50ti9l unique (serial_number);
alter table broken_laws add constraint FKlryp8t0etrbyfw0npnfd9h3vv foreign key (ticket_details_entity_ticket_id) references ticket_details;
alter table payment add constraint FKl48libgfexms3t197gr4k99e8 foreign key (ticket_details_entity_ticket_id) references ticket_details;
alter table ticket_details add constraint FKni3ety88mo165wgfm4o0k9o92 foreign key (official_entity_official_id) references official;