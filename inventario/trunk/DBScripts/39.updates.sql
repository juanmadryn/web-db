-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO transicion_estados VALUES
("0009.0001", 40, "0009.0002", "Completar", "No validar"),
("0009.0002", 41, "0009.0003", "Recibir", "No validar"),
("0009.0001", 42, "0009.0004", "Anular", "No validar"),
("0009.0002", 42, "0009.0004", "Anular", "No validar");

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;