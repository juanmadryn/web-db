-- Agrega en detalle_sc un articulo_id nuevo que representa el artículo solicitado
-- Ver Issue 25: Permitir cambio de artículo por parte del compardor en la coticazión / OC
START TRANSACTION;

ALTER TABLE `inventario`.`detalle_sc` 
	ADD COLUMN `articulo_id_solicitado` INTEGER NOT NULL AFTER `articulo_id`;

UPDATE `inventario`.`detalle_sc`
	SET `articulo_id_solicitado` = `articulo_id`;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;