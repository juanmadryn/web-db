-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO acciones_apps VALUES
(20, "Revertir OC Completa", "Revierte una solicitud de compra con OC completa a una con OC parcial", NULL, "0006"),
(21, "Revertir OC Parcial", "Revierte una solicitud de compra con OC completa a una aprobada", NULL, "0006");

INSERT INTO transicion_estados VALUES
("0006.0007", 20, "0006.0006", "Revertir a con OC parcial", "inventario.reglasNegocio.ValRN_0205_1"),
("0006.0006", 21, "0006.0003", "Revertir a aprobada", "inventario.reglasNegocio.ValRN_0205_1");


ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;