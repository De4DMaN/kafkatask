# Getting Started

## Development prerequisites
1. Java 11, for example Zulu: https://www.azul.com/downloads/?version=java-11-lts&package=jdk
2. Docker Desktop: https://www.docker.com/products/docker-desktop/

## Setup Code Style and Inspections

1. Import the file `.\CodeStyle.xml` `Settings > Editor > Code Style > Import Scheme > Intellij IDEA code style XML`
2. Enable formatter markers in
   comments `Preferences > Code Style > Formatter > Turn formatter on/off with markers in code comments`:
    1. Examples of usage:
        1. Java:
           `// @formatter:off`
           `// @formatter:on`
        2. HTML:
           `<!--@formatter:off-->`
           `<!--@formatter:on-->`

## Setup Plugins

Go to `Settings > Plugins`, search and install the following plugins:

1. Lombok Plugin
2. Save Actions (for Eclipse-like actions)
3. SonarLint
4. Mapstruct Support

#### Setup Save Actions Plugin

Go to Other `Settings > Save Actions` and activate following options:

**General:**

* Activate save actions on save
* Activate save actions on shortcut
* Activate save action on batch

**Formatting actions:**

* Optimize imports
* Reformat file
* Rearrange fields and methods

**Java inspection and quick fix:**

* Add final modifier to field
* Add final modifier to local variable or parameter
* Add final modifier to local variable or parameter if it is implicit
* Add missing @Override annotations
* Add blocks to if/while/for statements
* Remove explicit generic type for diamond
* Remove unnecessary semicolon

# Local environment

### Environmental variables
* `SCHEMA_REGISTRY` - host and port where Kafka schema registry is hosted 
* `BOOTSTRAP_SERVERS` - host and port where Kafka bootstrap servers are hosted 
* `JDBC_URL` - full MySQL database url with host, port and database name
* `TAX_PERCENT` - numeric (double) value for tax. Format is `10.4`, `2.5664`, `50`

### Kafka
If not using an external Kafka cluster or local instance a preconfigured Docker compose can be used, run the following commands from root project folder:
1. `cd docker-dev/confluent`
2. `docker-compose up -d`

### MySQL
If not using an external or local MySQL a preconfigured Docker compose can be used, run the following commands from root project folder:
1. `cd docker-dev/mysql`
2. `docker-compose up -d`

### Consumer
Consumer can be run from IDE or alternatively dockerized by running the following commands from the root project folder:
1. `mvn clean package -DskipTest`
2. `cd consumer`
3. `docker-compose up --build -d`

### Webserver
Webserver can be run from IDE or alternatively dockerized by running the following commands from the root project folder:
1. `mvn clean package -DskipTest`
2. `cd webserver`
3. `docker-compose up --build -d`

### Swagger
Swagger can be accessed from http://<webserver-host:webserver-port>/swagger-ui/index.html when the Webserver is running

# TODOs and Improvements

* Separate config files into profiled ones, i.e. application-prod.yml,  application-dev.yml, etc.
* Discuss and create E2E test, though for such a small project Integration test are enough
* Migrate from MySQL to a more coherent database, like Postgres. In case of just plain storage and minor aggregations - MongoDB
* Discuss and implement more robust Primary Key / ID assigment? Like hashing Name and Surname instead of using UUID, to be able to updated already existing clients in the database
* Migrate to Java 17
