DROP DATABASE gestion_nominas;
CREATE DATABASE gestion_nominas;
USE gestion_nominas;

CREATE TABLE empleados(
    nombre VARCHAR(50) NOT NULL,
    dni VARCHAR(9) NOT NULL,
    sexo CHAR(1) NOT NULL,
    categoria INT NOT NULL DEFAULT 1,
    anyos INT NOT NULL DEFAULT 0,
    CONSTRAINT nombre_pk PRIMARY KEY (dni)
);

CREATE TABLE nominas(
    dni VARCHAR(9) NOT NULL,
    sueldo INT NOT NULL,
    CONSTRAINT dni_fk FOREIGN KEY (dni) REFERENCES empleados(dni),
    CONSTRAINT dni_pk PRIMARY KEY (dni)
);

INSERT INTO empleados (nombre, dni, sexo, categoria, anyos) VALUES ("James Cosling", "32000032G", 'M', 4, 7);
INSERT INTO empleados (nombre, dni, sexo) VALUES ("Ada Lovelace", "32000031R", 'F');