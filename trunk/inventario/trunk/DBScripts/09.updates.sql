-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO circuitos_estados VALUES
("0007", "Cadenas de aprobaci�n de Solicitudes de compra", "Circuito para la gesti�n de la aprobaci�n de Solicitudes de Compra", NULL);

INSERT INTO estados VALUES
("0007.0001", "A firmar", "La firma est� pendiente", NULL, "0007"),
("0007.0002", "Firmada", "La firma se concret�", NULL, "0007"),

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;