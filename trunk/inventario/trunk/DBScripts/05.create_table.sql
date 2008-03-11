DROP TABLE IF EXISTS `inventario`.`cadenas_aprobacion`;
CREATE TABLE  `inventario`.`cadenas_aprobacion` (
  `cadena_aprobacion_id` int(10) unsigned NOT NULL auto_increment,
  `user_firmante` int(10) unsigned NOT NULL,
  `orden` int(10) unsigned NOT NULL,
  `configuracion_id` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`cadena_aprobacion_id`),
  KEY `FK_cadenas_aprobacion_website_user` (`user_firmante`),
  KEY `FK_cadenas_aprobacion_configuracion` (`configuracion_id`),
  CONSTRAINT `FK_cadenas_aprobacion_configuracion` FOREIGN KEY (`configuracion_id`) REFERENCES `infraestructura`.`configuracion` (`configuracion_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_cadenas_aprobacion_website_user` FOREIGN KEY (`user_firmante`) REFERENCES `infraestructura`.`website_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;