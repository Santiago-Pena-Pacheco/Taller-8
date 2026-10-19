CREATE DATABASE IF NOT EXISTS tienda;
USE tienda;

CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE producto (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(80) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    cantidad INT NOT NULL DEFAULT 0,
    id_categoria INT NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria)
);

INSERT INTO categoria (nombre)
VALUES ('Lacteos'), ('Aseo'), ('Bebidas'), ('Granos');

INSERT INTO producto (nombre, precio, cantidad, id_categoria) VALUES
('Leche entera', 4500, 20, 1),
('Jabon rey', 2800, 15, 2),
('Gaseosa 1.5L', 5500, 12, 3);

SELECT p.id_producto, p.nombre, p.precio, p.cantidad, c.nombre AS categoria
FROM producto p JOIN categoria c ON p.id_categoria = c.id_categoria;
