CREATE TABLE airport (
    id VARCHAR(10) PRIMARY KEY,
    type VARCHAR(50),
    name VARCHAR(100),
    elevation_ft INT,
    continent VARCHAR(2),
    iso_country VARCHAR(2),
    iso_region VARCHAR(5),
    municipality VARCHAR(100),
    gps_code VARCHAR(10),
    iata_code VARCHAR(10),
    local_code VARCHAR(10),
    latitude DOUBLE,
    longitude DOUBLE
);