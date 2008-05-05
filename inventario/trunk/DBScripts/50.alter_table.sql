USE inventario;
START TRANSACTION;

ALTER TABLE `inventario`.`ordenes_compra` ADD COLUMN `condicion_compra_id` INTEGER UNSIGNED DEFAULT NULL AFTER `observaciones`;

ALTER TABLE `inventario`.`ordenes_compra` ADD CONSTRAINT `ordenes_compra_ibfk_7` FOREIGN KEY `ordenes_compra_ibfk_7` (`condicion_compra_id`)
    REFERENCES condiciones_compra (condicion_compra_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;
