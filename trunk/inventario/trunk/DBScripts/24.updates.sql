START TRANSACTION;

ALTER TABLE `inventario`.`almacenes` MODIFY COLUMN `observaciones` MEDIUMTEXT  
CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ALTER TABLE `inventario`.`centro_costo` MODIFY COLUMN `observaciones` MEDIUMTEXT  
CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ALTER TABLE `inventario`.`clase_articulo` MODIFY COLUMN `observaciones` MEDIUMTEXT  
CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ALTER TABLE `inventario`.`tipo_movimiento_articulo` MODIFY COLUMN `observaciones` MEDIUMTEXT  
CHARACTER SET latin1 COLLATE latin1_swedish_ci DEFAULT NULL;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;