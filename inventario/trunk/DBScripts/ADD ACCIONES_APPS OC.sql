-- Data for the 'estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO acciones_apps VALUES
(NULL, "Completar", "Finaliza la carga de la orden de compra", NULL, "0008"),
(NULL, "Aprobar", "La orden de compra es aprobada", NULL, "0008"),
(NULL, "Rechazar", "La orden de compra es rechazada", NULL, "0008"),
(NULL, "Observar", "La orden de compra es devuelta con observaciones", NULL, "0008"),
(NULL, "Emitir OC", "La orden de compra es emitida", NULL, "0008");


ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;