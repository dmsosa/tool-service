CREATE TABLE IF NOT EXISTS users (
id TEXT PRIMARY KEY UNIQUE NOT NULL,
username VARCHAR(40) UNIQUE NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(40) NOT NULL,
bio VARCHAR(1500),
role role
);