CREATE DATABASE IF NOT EXISTS videojuegos_db;
USE videojuegos_db;

CREATE TABLE IF NOT EXISTS videojuegos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    genero VARCHAR(50),
    precio DECIMAL(10,2) NOT NULL,
    unidades_disponibles INT NOT NULL DEFAULT 0,
    nivel_reposicion INT NOT NULL DEFAULT 0,
    suspendido INT NOT NULL DEFAULT 1   -- 1 = disponible, 0 = no disponible
);

CREATE TABLE IF NOT EXISTS ventas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha DATE NOT NULL,
    id_videojuego INT NOT NULL,
    cantidad INT NOT NULL,
    descuento INT NOT NULL DEFAULT 0,
    total DECIMAL(12,2) NOT NULL,
    FOREIGN KEY (id_videojuego) REFERENCES videojuegos(id)
);

-- Si ya tenías las tablas del TP anterior, en lugar de crearlas ejecutá:
-- ALTER TABLE videojuegos ADD COLUMN unidades_disponibles INT NOT NULL DEFAULT 0;
-- ALTER TABLE videojuegos ADD COLUMN nivel_reposicion INT NOT NULL DEFAULT 0;
-- ALTER TABLE videojuegos ADD COLUMN suspendido INT NOT NULL DEFAULT 1;
-- ALTER TABLE ventas ADD COLUMN descuento INT NOT NULL DEFAULT 0;

INSERT INTO videojuegos (nombre, genero, precio, unidades_disponibles, nivel_reposicion, suspendido) VALUES
('The Legend of Zelda', 'Aventura', 59.99, 20, 5, 1),
('FIFA 25', 'Deportes', 49.99, 3, 10, 1),
('Doom Eternal', 'Shooter', 39.99, 8, 4, 0);
