-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO circuitos_estados VALUES
("0009", "Recepciones", "Circuito para la gesti�n de recepciones de compra", NULL);

INSERT INTO acciones_apps VALUES
(NULL, "Completar", "Finaliza la carga de la orden de compra", NULL, "0009"),
(NULL, "Recibir", "La orden de compra es aprobada", NULL, "0009"),
(NULL, "Anular", "La orden de compra es rechazada", NULL, "0009");

INSERT INTO estados VALUES
("0009.0001", "Generada", "La recepci�n ha sido generada", NULL, "0009"),
("0009.0002", "Completa", "La recepci�n ha sido completada", NULL, "0009"),
("0009.0003", "Recepcionada", "La recepci�n ha sido efectivamente realizada", NULL, "0009");
("0009.0004", "Anulada", "La recepci�n ha sido anulada", NULL, "0009");

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;