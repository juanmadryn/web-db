START TRANSACTION;

ALTER TABLE `inventario`.`solicitudes_compra` 
MODIFY COLUMN `fecha_aprobacion` DATETIME DEFAULT NULL;

ROLLBACK;
-- COMMIT;