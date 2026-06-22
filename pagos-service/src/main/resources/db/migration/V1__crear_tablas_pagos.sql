CREATE TABLE estados_pago (
    id_estado_pago INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30) NOT NULL UNIQUE
);

CREATE TABLE metodos_pago (
    id_metodo_pago INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE pagos (
    id_pago BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    id_metodo_pago INT NOT NULL,
    id_estado_pago INT NOT NULL,
    monto DECIMAL(10,2) NOT NULL,
    fecha_pago DATETIME NOT NULL,
    codigo_transaccion VARCHAR(100),

    CONSTRAINT fk_pagos_metodo_pago
        FOREIGN KEY (id_metodo_pago)
        REFERENCES metodos_pago(id_metodo_pago),

    CONSTRAINT fk_pagos_estado_pago
        FOREIGN KEY (id_estado_pago)
        REFERENCES estados_pago(id_estado_pago)
);

INSERT INTO estados_pago (nombre) VALUES
('PENDIENTE'),
('PAGADO'),
('RECHAZADO'),
('ANULADO');

INSERT INTO metodos_pago (nombre) VALUES
('EFECTIVO'),
('TARJETA_DEBITO'),
('TARJETA_CREDITO'),
('TRANSFERENCIA');
