-- Data for the 'circuitos_estados' table
USE infraestructura;
START TRANSACTION;

INSERT INTO circuitos_estados VALUES
("0010", "Comprobantes de movimiento de artículos", "Circuito para la gestión de comprobantes de movimiento de artículos", NULL);

INSERT INTO estados VALUES
("0010.0001", "Generado", "El comprobante de movimiento ha sido generado", NULL, "0010"),
("0010.0002", "Completo", "El comprobante de movimiento ha sido completado", NULL, "0010"),
("0010.0003", "Aprobado", "El comprobante de movimiento ha sido aprobado", NULL, "0010"),
("0010.0004", "Anulado", "El comprobante de movimiento ha sido rechazado", NULL, "0010");

INSERT INTO acciones_apps VALUES
(NULL, "Completar", "Finaliza la carga del comprobante de movimiento de articulo", NULL, "0010"),
(NULL, "Aprobar", "El comprobante de movimiento de articulo es aprobado", NULL, "0010"),
(NULL, "Anular", "El comprobante de movimiento de articulo es anulado", NULL, "0010");



ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;