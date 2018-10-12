-- Database: agencia_vuelo

-- DROP DATABASE agencia_vuelo;

CREATE DATABASE agencia_vuelo
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Spanish_El Salvador.1252'
    LC_CTYPE = 'Spanish_El Salvador.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
	
CREATE SCHEMA avr;
CREATE SCHEMA sis;
CREATE SCHEMA cat;
DROP SCHEMA public;

CREATE TABLE avr.personas(
	id bigserial,
	nombre varchar(50) NOT NULL,
	apellidoPaterno varchar(25) NOT NULL,
	apellidoMaterno varchar(25),
	genero char(1) NOT NULL, -- se añadio por requerimiento
	dui char(9) NOT NULL,
	nit char(17) NOT NULL,
	telefono char(9) NOT NULL,
	email varchar(60) NULL,
	direccion text NOT NULL,
	fechaNacimiento date NOT NULL,
	CONSTRAINT pk_persona PRIMARY KEY(id)
);

--Se cambio el id propio por el de la tabla persona
--Asi las tablas comparten el mismo valor
CREATE TABLE avr.clientes(
	idpersona bigint NOT NULL,
	pasaporte char(9), --Se añade por requerimiento
	visa char(8), --Se añade por requerimiento
	CONSTRAINT pk_cliente PRIMARY KEY(idpersona),
	CONSTRAINT fk_cliente_persona FOREIGN KEY(idpersona) REFERENCES avr.personas(id)
);

--Se cambio el id propio por el de la tabla persona
--Asi las tablas comparten el mismo valor
CREATE TABLE avr.empleados(
	idpersona bigint NOT NULL,
	seguro varchar(15) NOT NULL,
	afp varchar(15) NOT NULL,
	CONSTRAINT pk_empleado PRIMARY KEY(idpersona),
	CONSTRAINT fk_empleado_persona FOREIGN KEY(idpersona) REFERENCES avr.personas(id)
);

--Se usuara mas adelante para el login
CREATE TABLE sis.usuarios(
	nombre varchar(20) NOT NULL,
	contrasena varchar(64) NOT NULL,
	idempleado bigint NOT NULL,
	CONSTRAINT pk_usuario PRIMARY KEY(nombre),
	CONSTRAINT fk_usuario_empleado FOREIGN KEY(idempleado) REFERENCES avr.empleados(idpersona)
);

--Se usara como catalogo de datos
CREATE TABLE cat.aerolineas(
	id serial,
	nombre varchar(30) NOT NULL,
	CONSTRAINT pk_aerolinea PRIMARY KEY(id)
);