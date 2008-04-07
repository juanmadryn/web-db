DROP TABLE IF EXISTS `inventario`.`unidades_medida`;
CREATE TABLE  `inventario`.`unidades_medida` (
  `unidad_medida_id` int(10) unsigned NOT NULL auto_increment,
  `nombre` varchar(45) NOT NULL,
  `descripcion` varchar(255) default NULL,
  `observaciones` mediumtext,
  PRIMARY KEY  (`unidad_medida_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;