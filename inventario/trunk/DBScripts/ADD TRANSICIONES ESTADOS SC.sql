-- Data for the 'estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO transicion_estados VALUES
("0006.0001", 15, "0006.0002", "Completar", "No validar"),
("0006.0002", 16, "0006.0003", "Aprobar", "No validar"),
("0006.0002", 17, "0006.0004", "Rechazar", "No validar"),
("0006.0002", 18, "0006.0005", "Observar", "No validar"),
("0006.0003", 19, "0006.0006", "Generar OC", "No validar");


ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;