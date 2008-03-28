ALTER TABLE `inventario`.`tmp_fechaprecio` DROP COLUMN `done`,
ADD COLUMN `unidad_medida` VARCHAR(10) NOT NULL AFTER `precio_net`;