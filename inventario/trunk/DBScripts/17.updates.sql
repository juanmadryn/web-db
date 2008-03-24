-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO estados VALUES
("0006.0007", "Con OC Completa", "La solicitud de compra ha generado una Orden de Compra Completa", NULL, "0006");

INSERT INTO acciones_apps VALUES
(19, "Completar OC", "A partir de una solicitud de compra aprobada se genera una Orden de Compra", NULL, "0006");

INSERT INTO transicion_estados VALUES
("0006.0006", 19, "0006.0007", "Completar OC", "No validar");


ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;