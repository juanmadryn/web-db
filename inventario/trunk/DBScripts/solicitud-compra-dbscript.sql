-- ------------------------------------------------------------
-- Registra solicitudes de compra y el circuito asociado a la aprobación de las misma. 
-- ------------------------------------------------------------

CREATE TABLE inventario.solicitudes_compra (
  solicitud_compra_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id_comprador INTEGER(10) UNSIGNED NOT NULL,
  user_id_solicita INTEGER(10) UNSIGNED NOT NULL,
  estado VARCHAR(15) NOT NULL,
  centro_costo_id INTEGER UNSIGNED NULL,
  proyecto_id INTEGER(15) UNSIGNED NULL,
  tarea_id INTEGER(15) UNSIGNED NULL,
  fecha_solicitud DATE NOT NULL,
  fecha_aprobacion DATE NULL,
  fecha_oc DATE NULL,
  descripcion VARCHAR(255) NULL,
  observaciones MEDIUMTEXT NULL,
  PRIMARY KEY(solicitud_compra_id),
  FOREIGN KEY(tarea_id, proyecto_id)
    REFERENCES proyectos.tareas_proyecto(tarea_id, proyecto_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(centro_costo_id)
    REFERENCES inventario.centro_costo(centro_costo_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(estado)
    REFERENCES infraestructura.estados(estado)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(user_id_solicita)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(user_id_comprador)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
TYPE=InnoDB;

CREATE TABLE inventario.ordenes_compra (
  orden_compra_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  entidad_id_proveedor INTEGER(15) UNSIGNED NOT NULL,
  centro_costo_id INTEGER UNSIGNED NOT NULL,
  proyecto_id INTEGER(15) UNSIGNED NOT NULL,
  tarea_id INTEGER(15) UNSIGNED NOT NULL,
  user_id_comprador INTEGER(10) UNSIGNED NOT NULL,
  estado VARCHAR(15) NOT NULL,
  fecha DATE NOT NULL,
  fecha_estimada_entrega DATE NULL,
  fecha_entrega_completa DATE NULL,
  descripcion VARCHAR(255) NULL,
  observaciones MEDIUMTEXT NULL,
  PRIMARY KEY(orden_compra_id),
  FOREIGN KEY(estado)
    REFERENCES infraestructura.estados(estado)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(user_id_comprador)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(tarea_id, proyecto_id)
    REFERENCES proyectos.tareas_proyecto(tarea_id, proyecto_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(centro_costo_id)
    REFERENCES inventario.centro_costo(centro_costo_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(entidad_id_proveedor)
    REFERENCES infraestructura.entidad_externa(entidad_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
TYPE=InnoDB;

CREATE TABLE inventario.recepciones_compras (
  recepcion_compra_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  comprobante_movimiento_id INTEGER UNSIGNED NOT NULL,
  fecha DATE NULL,
  cantidad_recibida DOUBLE NULL,
  PRIMARY KEY(recepcion_compra_id),
  FOREIGN KEY(comprobante_movimiento_id)
    REFERENCES inventario.comprobante_movimiento_articulo(comprobante_movimiento_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE inventario.detalle_SC (
  detalle_SC_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  recepcion_compra_id INTEGER UNSIGNED NOT NULL,
  orden_compra_id INTEGER UNSIGNED NOT NULL,
  articulo_id INTEGER UNSIGNED NOT NULL,
  solicitud_compra_id INTEGER UNSIGNED NOT NULL,
  cantidad_solicitada DOUBLE NOT NULL DEFAULT '0',
  cantidad_pedida DOUBLE NOT NULL DEFAULT '0',
  cantidad_recibida DOUBLE NOT NULL DEFAULT '0',
  descripcion VARCHAR(255) NULL,
  observaciones MEDIUMTEXT NULL,
  PRIMARY KEY(detalle_SC_id),
  FOREIGN KEY(solicitud_compra_id)
    REFERENCES inventario.solicitudes_compra(solicitud_compra_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(articulo_id)
    REFERENCES inventario.articulos(articulo_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(orden_compra_id)
    REFERENCES inventario.ordenes_compra(orden_compra_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(recepcion_compra_id)
    REFERENCES inventario.recepciones_compras(recepcion_compra_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE inventario.configuración_cadena_aprobacion (
  configuracion_id INTEGER(15) UNSIGNED NOT NULL,
  FOREIGN KEY(configuracion_id)
    REFERENCES infraestructura.configuracion(configuracion_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

CREATE TABLE inventario.cadena_firmas (
  cadena_firma_id INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  user_id INTEGER(10) UNSIGNED NOT NULL,
  user_id_alternativo INTEGER(10) UNSIGNED NULL,
  orden INTEGER UNSIGNED NULL,
  configuracion_cadena_aprobacion_id INTEGER(15) UNSIGNED NOT NULL,
  PRIMARY KEY(cadena_firma_id),
  FOREIGN KEY(user_id_alternativo)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(user_id)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(configuracion_cadena_aprobacion_id)
    REFERENCES inventario.configuración_cadena_aprobacion(configuracion_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;