CREATE DATABASE el_prometeo WITH OWNER prometeo;

\connect el_prometeo

/* Entidades */

CREATE TABLE usuario(
id_usuario SERIAL PRIMARY KEY,
nombre VARCHAR(255) NOT NULL,
apellido_paterno VARCHAR(255) NOT NULL,
apellido_materno VARCHAR(255) NOT NULL,
telefono BIGINT UNIQUE,
correo VARCHAR(255) UNIQUE NOT NULL,
contrasenia VARCHAR(255) NOT NULL,
estado VARCHAR(255),
fecha_de_naciminiento DATE,
genero VARCHAR(1),
imagen TEXT /* Foto de perfil(?) */
);

CREATE TABLE agente(
id_agente SERIAL PRIMARY KEY,
ocupacion VARCHAR(255),
reputacion_agente DOUBLE PRECISION,
FOREIGN KEY (id_agente) REFERENCES usuario(id_usuario)
);

CREATE TABLE programador(
id_programador SERIAL PRIMARY KEY,
formacion TEXT,
reputacion_programador DOUBLE PRECISION,
FOREIGN KEY (id_programador) REFERENCES usuario(id_usuario)
);  

CREATE TABLE especialidad(
id_especialidad SERIAL PRIMARY KEY,
especialidad VARCHAR(255)
);

CREATE TABLE servicio(
id_servicio SERIAL PRIMARY KEY,
presupuesto DOUBLE PRECISION,
description TEXT,
titulo VARCHAR(255),
finalizado BOOLEAN
);

CREATE TABLE mensaje(
id_mensaje SERIAL PRIMARY KEY,
mensaje TEXT
);

/* Relaciones */

CREATE TABLE pide_servicio(
id_agente INTEGER NOT NULL,
id_servicio INTEGER NOT NULL,
PRIMARY KEY(id_agente,id_servicio),
FOREIGN KEY (id_agente) REFERENCES agente(id_agente),
FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);

CREATE TABLE presta_servicio(
id_programador INTEGER NOT NULL,
id_servicio INTEGER NOT NULL,
PRIMARY KEY(id_programador,id_servicio),
FOREIGN KEY (id_programador) REFERENCES programador(id_programador),
FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);

CREATE TABLE se_dedica(
id_programador INTEGER NOT NULL,
id_especialidad INTEGER NOT NULL,
PRIMARY KEY(id_programador,id_especialidad),
FOREIGN KEY (id_programador) REFERENCES programador(id_programador),
FOREIGN KEY (id_especialidad) REFERENCES especialidad(id_especialidad)
);

CREATE TABLE registro(
id_mensaje INTEGER NOT NULL PRIMARY KEY,
id_servicio INTEGER NOT NULL,
envio INTEGER,
recibio INTEGER,
FOREIGN KEY (id_mensaje) REFERENCES mensaje(id_mensaje),
FOREIGN KEY (id_servicio) REFERENCES servicio(id_servicio)
);
