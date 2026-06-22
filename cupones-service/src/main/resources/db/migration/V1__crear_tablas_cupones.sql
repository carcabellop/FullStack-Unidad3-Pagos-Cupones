CREATE TABLE estados_cupon (
    id_estado_cupon INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE tipos_descuento (
    id_tipo_descuento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE cupones (
    id_cupon BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(150) NOT NULL,
    id_tipo_descuento INT NOT NULL,
    valor_descuento DECIMAL(10,2) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    id_estado_cupon INT NOT NULL,
    uso_maximo INT NOT NULL,
    usos_actuales INT NOT NULL DEFAULT 0,

    CONSTRAINT fk_cupones_tipo_descuento
        FOREIGN KEY (id_tipo_descuento)
        REFERENCES tipos_descuento(id_tipo_descuento),

    CONSTRAINT fk_cupones_estado_cupon
        FOREIGN KEY (id_estado_cupon)
        REFERENCES estados_cupon(id_estado_cupon)
);

INSERT INTO estados_cupon (nombre) VALUES
('ACTIVO'),
('INACTIVO'),
('VENCIDO');

INSERT INTO tipos_descuento (nombre) VALUES
('PORCENTAJE'),
('MONTO_FIJO');
