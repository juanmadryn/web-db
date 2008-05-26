-- Data for the 'menu' table
START TRANSACTION;

ALTER TABLE `inventario`.`comprobante_movimiento_articulo` ADD COLUMN `user_id_confirma` INTEGER UNSIGNED AFTER `numero_impresion`,
 ADD CONSTRAINT `FK_comprobante_movimiento_articulo_website_user` FOREIGN KEY `FK_comprobante_movimiento_articulo_website_user` (`user_id_confirma`)
    REFERENCES `infraestructura`.`website_user` (`user_id`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;