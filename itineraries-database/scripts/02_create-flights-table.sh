#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    \connect itineraries;

    create table flight (
        id int4 not null,
        from_city varchar(255) not null,
        to_city varchar(255) not null,
        takeoff_time timestamp not null,
        landing_time timestamp not null,

        constraint valid_cities check (from_city <> to_city),
        constraint valid_schedule check (takeoff_time < landing_time),

        primary key(id));

    create index city_departures_idx on flight (lower(from_city));

    grant select, insert, update, delete on flight to maldanam;

EOSQL
