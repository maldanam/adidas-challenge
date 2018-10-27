#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    \connect itineraries;

    insert into flight values (1, 'Barcelona', 'Madrid', '2018-10-29 12:00:00', '2018-10-29 12:45:00');
    insert into flight values (2, 'Barcelona', 'Zaragoza', '2018-10-29 12:00:00', '2018-10-29 12:30:00');
    insert into flight values (3, 'Barcelona', 'Valencia', '2018-10-29 12:00:00', '2018-10-29 12:30:00');

    insert into flight values (4, 'Madrid', 'Lisboa', '2018-10-29 13:00:00', '2018-10-29 13:45:00');
    insert into flight values (5, 'Zaragoza', 'Lisboa', '2018-10-29 13:00:00', '2018-10-29 13:55:00');
    insert into flight values (6, 'Zaragoza', 'Murcia', '2018-10-29 13:00:00', '2018-10-29 13:30:00');
    insert into flight values (7, 'Valencia', 'Murcia', '2018-10-29 13:00:00', '2018-10-29 13:30:00');

    insert into flight values (8, 'Lisboa', 'Valencia', '2018-10-29 14:00:00', '2018-10-29 15:45:00');
    insert into flight values (9, 'Lisboa', 'Barcelona', '2018-10-29 14:00:00', '2018-10-29 15:55:00');
    insert into flight values (10, 'Murcia', 'Sevilla', '2018-10-29 14:00:00', '2018-10-29 14:30:00');
    insert into flight values (11, 'Murcia', 'Madrid', '2018-10-29 14:00:00', '2018-10-29 14:30:00');

    insert into flight values (12, 'Valencia', 'Madrid', '2018-10-29 15:00:00', '2018-10-29 15:30:00');
    insert into flight values (13, 'Lisboa', 'Valencia', '2018-10-29 15:00:00', '2018-10-29 16:45:00');
    insert into flight values (14, 'Lisboa', 'Barcelona', '2018-10-29 15:00:00', '2018-10-29 16:55:00');
    insert into flight values (15, 'Murcia', 'Sevilla', '2018-10-29 15:00:00', '2018-10-29 15:30:00');
    insert into flight values (16, 'Murcia', 'Madrid', '2018-10-29 15:00:00', '2018-10-29 15:30:00');

EOSQL
