DROP DATABASE IF EXISTS prom;
CREATE DATABASE prom;
USE prom;

CREATE TABLE grupo (
    codigo INT PRIMARY KEY
);

CREATE TABLE alumno (
    usuario VARCHAR(50) NOT NULL PRIMARY KEY,
    contrasena VARCHAR(255) NOT NULL,
    puntos INT NULL,
    grupo INT NULL,
    FOREIGN KEY (grupo) REFERENCES grupo(codigo)
);
