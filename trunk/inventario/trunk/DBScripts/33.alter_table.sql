USE inventario;
START TRANSACTION;
ALTER TABLE `inventario`.`detalle_sc` ADD COLUMN `observaciones_oc` MEDIUMTEXT  AFTER `observaciones`;
ROLLBACK;
-- COMMIT;
