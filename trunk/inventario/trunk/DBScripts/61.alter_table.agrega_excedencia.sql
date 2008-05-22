-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`detalles_rc` ADD COLUMN `cantidad_excedencia` FLOAT  DEFAULT NULL AFTER `cantidad_recibida`;

ALTER TABLE `inventario`.`detalles_rc` ADD COLUMN `unidad_medida_id` INTEGER UNSIGNED NOT NULL AFTER `cantidad_excedencia`,
 ADD CONSTRAINT `FK_detalles_rc_unidad_medida_id` FOREIGN KEY `FK_detalles_rc_unidad_medida_id` (`unidad_medida_id`)
    REFERENCES `unidades_medida` (`unidad_medida_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;