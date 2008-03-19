-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO estados VALUES
("0008.0001", "Generada", "La orden de compra ha sido generada", NULL, "0008"),
("0008.0002", "Completa", "La orden de compra ha sido completada", NULL, "0008"),
("0008.0003", "Aprobada", "La orden de compra ha sido aprobada", NULL, "0008"),
("0008.0004", "Rechazada", "La orden de compra ha sido rechazada", NULL, "0008"),
("0008.0005", "Observada", "La orden de compra ha sido observada", NULL, "0008"),
("0008.0006", "Emitida", "La orden de compra ha sido emitida", NULL, "0008");

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;