ALTER TABLE `inventario`.`detalle_cotizacion` ADD COLUMN `cotizacion_seleccionada_proveedor1` INTEGER(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `marca_proveedor1`,
 ADD COLUMN `cotizacion_seleccionada_proveedor2` INTEGER(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `maraca_proveedor2`,
 ADD COLUMN `cotizacion_seleccionada_proveedor3` INTEGER(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `marca_proveedor3`,
 ADD COLUMN `cotizacion_seleccionada_proveedor4` INTEGER(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `marca_proveedor4`,
 ADD COLUMN `cotizacion_seleccionada_proveedor5` INTEGER(1) UNSIGNED NOT NULL DEFAULT 0 AFTER `marca_proveedor5`;
