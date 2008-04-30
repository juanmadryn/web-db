-- Agrega el usuario con rol comprador que creo la orden a nombre de otro comprador
USE inventario;
START TRANSACTION;

ALTER TABLE `inventario`.`ordenes_compra` ADD COLUMN `user_id_generador` INT(15) UNSIGNED AFTER `user_id_comprador`;

ALTER TABLE `inventario`.`ordenes_compra` ADD CONSTRAINT `ordenes_compra_ibfk_6` FOREIGN KEY `ordenes_compra_ibfk_6` (`user_id_generador`)
    REFERENCES `infraestructura`.`website_user` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ROLLBACK;

-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;