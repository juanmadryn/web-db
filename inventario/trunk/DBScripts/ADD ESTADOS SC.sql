-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO estados VALUES
("0006.0001", "Generada", "La solicitud de compra ha sido generada", NULL, "0006"),
("0006.0002", "Completa", "La solicitud de compra ha sido completada", NULL, "0006"),
("0006.0003", "Aprobada", "La solicitud de compra ha sido aprobada", NULL, "0006"),
("0006.0004", "Rechazada", "La solicitud de compra ha sido rechazada", NULL, "0006"),
("0006.0005", "Observada", "La solicitud de compra ha sido observada", NULL, "0006"),
("0006.0006", "Con OC", "La solicitud de compra ha generado una Orden de Compra", NULL, "0006");

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;