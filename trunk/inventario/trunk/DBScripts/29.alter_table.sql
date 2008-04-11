START TRANSACTION;

ALTER TABLE `inventario`.`ordenes_compra` ADD COLUMN `fecha_aprobacion` DATETIME  AFTER `fecha`;

ROLLBACK;
-- COMMIT;