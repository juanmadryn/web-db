CREATE TABLE `inventario`.`tmp_fechaprecio` (
  `cod_articu` VARCHAR(15) NOT NULL,
  `fecha_mov` DATETIME NOT NULL,
  `precio_net` DOUBLE UNSIGNED NOT NULL,
  `done` CHAR(1),
  PRIMARY KEY (`cod_articu`)
)
ENGINE = InnoDB;