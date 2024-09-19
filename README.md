# test

## default profile with H2
mvn spring-boot:run

## dev profile with MySQL
docker-compose up -d
mvn spring-boot:run -P dev