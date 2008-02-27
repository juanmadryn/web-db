-- Data for the 'estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO acciones_apps VALUES
(NULL, "Completar", "Finaliza la carga de la solicitud de compra", NULL, "0006"),
(NULL, "Aprobar", "La solicitud de compra es aprobada", NULL, "0006"),
(NULL, "Rechazar", "La solicitud de compra es rechazada", NULL, "0006"),
(NULL, "Observar", "La solicitud de compra es devuelta con observaciones", NULL, "0006"),
(NULL, "Emitir OC", "A partir de una solicitud de compra aprobada se genera una Orden de Compra", NULL, "0006");


ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;