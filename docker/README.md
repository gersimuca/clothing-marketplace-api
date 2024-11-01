# Docker Compose Configuration for PostgreSQL and pgAdmin

This Docker Compose configuration sets up two containers: PostgreSQL and pgAdmin. 
It's designed to provide a convenient environment for running a PostgreSQL database and managing it through pgAdmin.

## Prerequisites

Before using this configuration, ensure that you have Docker and Docker Compose installed on your system.

- [Install Docker](https://docs.docker.com/get-docker/)
- [Install Docker Compose](https://docs.docker.com/compose/install/)

## Usage

1. Clone this repository to your local machine:

    ```bash
    git clone <repository-url>
    ```

2. Navigate to the directory containing the `compose.yml` file.

3. Customize environment variables (if needed) in the `.env` file.

4. Run the following command to start the containers:

    ```bash
    docker compose -f .\compose.yml up -d
    ```

5. Once the containers are up and running, you can access pgAdmin in your web browser by navigating to `http://localhost:7000`.

6. Log in to pgAdmin using the email and password specified in the `.env` file.

## Configuration

### PostgreSQL Service (`postgresdb`)

- **Image**: PostgreSQL version 16.2.
- **Container Name**: postgrescontainer.
- **Environment Variables**:
    - `POSTGRES_USER`: Default value is `gersi`.
    - `POSTGRES_PASSWORD`: Default value is `gersi`.
    - `POSTGRES_DB`: Default value is `clothing_marketplace`.
- **Exposed Port**: 5432 (for PostgreSQL connections).
- **Volumes**: Mounts a volume for persisting PostgreSQL data and initializes the database with `schema.sql` if available.
- **Health Check**: Verifies the availability of the PostgreSQL server using `pg_isready`.

### pgAdmin Service (`pgadmin`)

- **Image**: pgAdmin 4 version 8.5.
- **Container Name**: pgadmincontainer.
- **Environment Variables**:
    - `PGADMIN_DEFAULT_EMAIL`: Default value is `gersimuca@aol.com`.
    - `PGADMIN_DEFAULT_PASSWORD`: Default value is `gersimuca`.
- **Exposed Port**: 6000 (for pgAdmin web interface).
- **Volumes**: Mounts a volume for persisting pgAdmin data.
- **Networks**: Connects to the `backend` network for communication with other services.

## Health Checks

Both the PostgreSQL and pgAdmin containers have health checks configured. The health checks ensure that the services are running properly and ready to handle requests.
Get Health check of the PostgreSQL container by running this command below

 ```bash
    docker inspect --format='{{json .State.Health}}' postgrescontainer
 ```

## Network Configuration

The containers are connected to a custom bridge network named `backend`. This network provides isolation and efficient communication between the services.