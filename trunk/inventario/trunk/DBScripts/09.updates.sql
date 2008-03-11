-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO circuitos_estados VALUES
("0007", "Cadenas de aprobación de Solicitudes de compra", "Circuito para la gestión de la aprobación de Solicitudes de Compra", NULL);

INSERT INTO estados VALUES
("0007.0001", "A firmar", "La firma está pendiente", NULL, "0007"),
("0007.0002", "Firmada", "La firma se concretó", NULL, "0007"),

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;