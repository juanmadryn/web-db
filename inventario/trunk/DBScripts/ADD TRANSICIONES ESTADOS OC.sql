-- Data for the 'estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO transicion_estados VALUES
("0008.0001", 24, "0008.0002", "Completar", "No validar"),
("0008.0002", 25, "0008.0003", "Aprobar", "No validar"),
("0008.0002", 26, "0008.0004", "Rechazar", "No validar"),
("0008.0002", 27, "0008.0005", "Observar", "No validar"),
("0008.0003", 28, "0008.0006", "Emitir OC", "No validar");


ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;