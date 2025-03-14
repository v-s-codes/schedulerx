DO $$
BEGIN
   IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'schedulerx') THEN
      PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE schedulerx');
   END IF;
END $$;

\c schedulerx;

CREATE TABLE IF NOT EXISTS commands (
    id BIGINT PRIMARY KEY,
    schedule VARCHAR(255) NOT NULL,
    type VARCHAR(255) NOT NULL,
    params text[] DEFAULT '{}',
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS jobs (
    id BIGINT PRIMARY KEY,
    command_id BIGINT REFERENCES commands(id),
    timestamp TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL
);