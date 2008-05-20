-- Data for the 'menu' table
START TRANSACTION;

INSERT INTO infraestructura.transicion_estados 
(estado_origen, estado_destino, accion, prompt_accion, validador)
VALUES ('0006.0003','0006.0005',17,'Revisar','inventario.reglasNegocio.ValRN_0216_1');

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;