-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO transicion_estados VALUES
("0010.0001", 24, "0010.0002", "Completar", "No validar"),
("0010.0002", 25, "0010.0003", "Aprobar", "No validar"),
("0010.0001", 26, "0010.0004", "Anular", "No validar"),
("0010.0002", 27, "0010.0004", "Anular", "No validar");

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;