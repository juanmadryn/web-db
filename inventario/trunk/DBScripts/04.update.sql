DROP TABLE IF EXISTS `inventario`.`detalle_sc`;

DROP TABLE IF EXISTS `inventario`.`solicitudes_compra`;
CREATE TABLE  `inventario`.`solicitudes_compra` (
  `solicitud_compra_id` int(10) unsigned NOT NULL auto_increment,
  `user_id_comprador` int(10) unsigned default NULL,
  `user_id_solicita` int(10) unsigned NOT NULL,
  `estado` varchar(15) NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `fecha_aprobacion` date default NULL,
  `fecha_oc` date default NULL,
  `descripcion` varchar(255) default NULL,
  `observaciones` mediumtext,
  `centro_costo_id` int(10) unsigned default NULL,
  `proyecto_id` int(15) unsigned default NULL,
  PRIMARY KEY  (`solicitud_compra_id`),
  KEY `estado` (`estado`),
  KEY `user_id_solicita` (`user_id_solicita`),
  KEY `user_id_comprador` (`user_id_comprador`),
  KEY `FK_solicitudes_compra_centro_costo` (`centro_costo_id`),
  KEY `FK_solicitudes_compra_proyecto_id` USING BTREE (`proyecto_id`),
  CONSTRAINT `FK_solicitudes_compra_centro_costo` FOREIGN KEY (`centro_costo_id`) REFERENCES `centro_costo` (`centro_costo_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_solicitudes_compra_proyecto_id` FOREIGN KEY (`proyecto_id`) REFERENCES `proyectos`.`proyectos` (`proyecto_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `solicitudes_compra_ibfk_3` FOREIGN KEY (`estado`) REFERENCES `infraestructura`.`estados` (`estado`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `solicitudes_compra_ibfk_4` FOREIGN KEY (`user_id_solicita`) REFERENCES `infraestructura`.`website_user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `solicitudes_compra_ibfk_5` FOREIGN KEY (`user_id_comprador`) REFERENCES `infraestructura`.`website_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE  `inventario`.`detalle_sc` (
  `detalle_SC_id` int(10) unsigned NOT NULL auto_increment,
  `recepcion_compra_id` int(10) unsigned default NULL,
  `orden_compra_id` int(10) unsigned default NULL,
  `articulo_id` int(10) unsigned NOT NULL,
  `solicitud_compra_id` int(10) unsigned NOT NULL,
  `cantidad_solicitada` float NOT NULL default '0',
  `cantidad_pedida` float default NULL,
  `cantidad_recibida` float default NULL,
  `descripcion` varchar(255) default NULL,
  `observaciones` mediumtext,
  `tarea_id` int(15) unsigned default NULL,
  `monto_unitario` double unsigned default NULL,
  `monto_ultima_compra` double unsigned default NULL,
  `fecha_ultima_compra` date default NULL,
  PRIMARY KEY  (`detalle_SC_id`),
  KEY `solicitud_compra_id` (`solicitud_compra_id`),
  KEY `articulo_id` (`articulo_id`),
  KEY `orden_compra_id` (`orden_compra_id`),
  KEY `recepcion_compra_id` (`recepcion_compra_id`),
  KEY `tarea_id` (`tarea_id`),
  CONSTRAINT `detalle_sc_ibfk_1` FOREIGN KEY (`solicitud_compra_id`) REFERENCES `solicitudes_compra` (`solicitud_compra_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `detalle_sc_ibfk_2` FOREIGN KEY (`articulo_id`) REFERENCES `articulos` (`articulo_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `detalle_sc_ibfk_3` FOREIGN KEY (`orden_compra_id`) REFERENCES `ordenes_compra` (`orden_compra_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `detalle_sc_ibfk_4` FOREIGN KEY (`recepcion_compra_id`) REFERENCES `recepciones_compras` (`recepcion_compra_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_detalle_sc_6` FOREIGN KEY (`tarea_id`) REFERENCES `proyectos`.`tareas_proyecto` (`tarea_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP VIEW IF EXISTS `inventario`.`solicitantes`;
CREATE VIEW `inventario`.`solicitantes` AS select `u`.`user_id` AS `user_id`,`u`.`login_name` AS `login_name`,`u`.`login_password` AS `login_password`,`u`.`nivel_visibilidad` AS `nivel_visibilidad`,`u`.`nombre_completo` AS `nombre_completo`,`u`.`email` AS `email`,`u`.`nro_legajo` AS `nro_legajo` from `infraestructura`.`website_user` `u` where ((select count(0) AS `count(*)` from `inventario`.`solicitudes_compra` `s` where (`s`.`user_id_solicita` = `u`.`user_id`)) > 0);