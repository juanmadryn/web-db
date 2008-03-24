-- Data for the 'atributos_rol' table
USE infraestructura;
START TRANSACTION;

INSERT INTO atributos_rol VALUES
(NULL, "TOTAL_ORDENCOMPRA", "Total Orden de Compra", NULL, NULL, "2007-01-01", NULL, 'F', 'F', 'F', NULL, "real", NULL, NULL, NULL, "ordenes_compra", "TABLA");

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;