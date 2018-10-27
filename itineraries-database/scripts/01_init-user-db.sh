#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
    CREATE USER maldanam PASSWORD 'ThePassword';
    CREATE DATABASE itineraries;
    GRANT ALL PRIVILEGES ON DATABASE itineraries TO maldanam;
EOSQL
