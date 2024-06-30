nuevo sql

-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS geventos;
USE geventos;

-- Crear la tabla tipo_usuario
CREATE TABLE tipo_usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    tipo VARCHAR(20) NOT NULL
);

-- Crear la tabla usuario
CREATE TABLE usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(32) NOT NULL UNIQUE,
    password VARCHAR(64) NOT NULL,
    nombre VARCHAR(64) NOT NULL,
    apellido VARCHAR(64) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    tipo_usuario_id INT NOT NULL,
    FOREIGN KEY (tipo_usuario_id) REFERENCES tipo_usuario(id)
);

-- Crear la tabla evento
CREATE TABLE evento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(64) NOT NULL,
    descripcion TEXT NOT NULL,
    fecha DATETIME NOT NULL,
    estado VARCHAR(20) NOT NULL DEFAULT 'Activo',
    organizador_id INT NOT NULL,
    cantidad_tickets INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (organizador_id) REFERENCES usuario(id)
);

-- Crear la tabla compra
CREATE TABLE compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha DATETIME NOT NULL,
    total DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- Crear la tabla detalle_compra
CREATE TABLE detalle_compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    compra_id INT NOT NULL,
    evento_id INT NOT NULL,
    cantidad INT NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (compra_id) REFERENCES compra(id),
    FOREIGN KEY (evento_id) REFERENCES evento(id)
);

INSERT INTO `geventos`.`tipo_usuario`
(`id`, 
`tipo`)
VALUES
('2',
'Comprador');

INSERT INTO `geventos`.`tipo_usuario`
(`id`, 
`tipo`)
VALUES
('1',
'Organizador');
