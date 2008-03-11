DROP TABLE IF EXISTS `inventario`.`instancias_aprobacion`;
CREATE TABLE  `inventario`.`instancias_aprobacion` (
  `instancia_aprobacion_id` int(10) unsigned NOT NULL auto_increment,
  `solicitud_compra_id` int(10) unsigned NOT NULL,
  `user_firmante` int(10) unsigned NOT NULL,
  `estado` varchar(15) NOT NULL,
  `fecha_entrada` date NOT NULL,
  `fecha_accion` date default NULL,
  PRIMARY KEY  (`instancia_aprobacion_id`),
  KEY `FK_instancias_aprobacion_solicitudes_compra` (`solicitud_compra_id`),
  KEY `FK_instancias_aprobacion_website_user` (`user_firmante`),
  KEY `FK_instancias_aprobacion_estados` (`estado`),
  CONSTRAINT `FK_instancias_aprobacion_estados` FOREIGN KEY (`estado`) REFERENCES `infraestructura`.`estados` (`estado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_instancias_aprobacion_solicitudes_compra` FOREIGN KEY (`solicitud_compra_id`) REFERENCES `solicitudes_compra` (`solicitud_compra_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_instancias_aprobacion_website_user` FOREIGN KEY (`user_firmante`) REFERENCES `infraestructura`.`website_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;