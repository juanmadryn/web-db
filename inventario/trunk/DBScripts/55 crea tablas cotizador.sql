CREATE TABLE formas_pago (
  forma_pago_id INTEGER(15) UNSIGNED NOT NULL AUTO_INCREMENT,
  nombre VARCHAR(60) NOT NULL,
  descripcion VARCHAR(255) NULL,
  observaciones VARCHAR(255) NULL,
  PRIMARY KEY(forma_pago_id),
  UNIQUE INDEX formas_pago_UK1(nombre)
)
TYPE=InnoDB;

CREATE TABLE cotizaciones_compra (
  cotizacion_compra_id INTEGER(15) UNSIGNED NOT NULL AUTO_INCREMENT,
  estado VARCHAR(15) NOT NULL,
  entidad_id_proveedor1 INTEGER(15) UNSIGNED NULL,
  user_id_comprador INTEGER(10) UNSIGNED NULL,
  condicion_compra_id_proveedor1 INTEGER(10) UNSIGNED NULL,
  forma_pago_id_proveedor1 INTEGER(15) UNSIGNED NULL,
  plazo_entrega_proveedor1 INTEGER UNSIGNED NULL,
  bonificacion_proveedor1 DOUBLE NULL,
  total_proveedor1 DOUBLE NULL,
  observaciones_proveedor1 MEDIUMTEXT NULL,
  entidad_id_proveedor2 INTEGER(15) UNSIGNED NULL,
  condicion_compra_id_proveedor2 INTEGER(10) UNSIGNED NULL,
  forma_pago_id_proveedor2 INTEGER(15) UNSIGNED NULL,
  plazo_entrega_proveedor2 INTEGER UNSIGNED NULL,
  bonificacion_proveedor2 DOUBLE NULL,
  total_proveedor2 DOUBLE NULL,
  observaciones_proveedor2 MEDIUMTEXT NULL,
  entidad_id_proveedor3 INTEGER(15) UNSIGNED NULL,
  condicion_compra_id_proveedor3 INTEGER(10) UNSIGNED NULL,
  forma_pago_id_proveedor3 INTEGER(15) UNSIGNED NULL,
  plazo_entrega_proveedor3 INTEGER UNSIGNED NULL,
  bonificacion_proveedor3 DOUBLE NULL,
  total_proveedor3 DOUBLE NULL,
  observaciones_proveedor3 MEDIUMTEXT NULL,
  entidad_id_proveedor4 INTEGER(15) UNSIGNED NULL,
  condicion_compra_id_proveedor4 INTEGER(10) UNSIGNED NULL,
  forma_pago_id_proveedor4 INTEGER(15) UNSIGNED NULL,
  plazo_entrega_proveedor4 INTEGER UNSIGNED NULL,
  bonificacion_proveedor4 DOUBLE NULL,
  total_proveedor4 DOUBLE NULL,
  observaciones_proveedor4 MEDIUMTEXT NULL,
  entidad_id_proveedor5 INTEGER(15) UNSIGNED NULL,
  condicion_compra_id_proveedor5 INTEGER(10) UNSIGNED NULL,
  forma_pago_id_proveedor5 INTEGER(15) UNSIGNED NULL,
  plazo_entrega_proveedor5 INTEGER UNSIGNED NULL,
  bonificacion_proveedor5 DOUBLE NULL,
  total_proveedor5 DOUBLE NULL,
  observaciones_proveedor5 MEDIUMTEXT NULL,
  observaciones MEDIUMTEXT NULL,
  PRIMARY KEY(cotizacion_compra_id),
  FOREIGN KEY(entidad_id_proveedor1)
    REFERENCES infraestructura.entidad_externa(entidad_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(entidad_id_proveedor2)
    REFERENCES infraestructura.entidad_externa(entidad_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(entidad_id_proveedor3)
    REFERENCES infraestructura.entidad_externa(entidad_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(entidad_id_proveedor4)
    REFERENCES infraestructura.entidad_externa(entidad_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(entidad_id_proveedor5)
    REFERENCES infraestructura.entidad_externa(entidad_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(estado)
    REFERENCES infraestructura.estados(estado)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION,
  FOREIGN KEY(user_id_comprador)
    REFERENCES infraestructura.website_user(user_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(condicion_compra_id_proveedor1)
    REFERENCES condiciones_compra(condicion_compra_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(condicion_compra_id_proveedor2)
    REFERENCES condiciones_compra(condicion_compra_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(condicion_compra_id_proveedor3)
    REFERENCES condiciones_compra(condicion_compra_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(condicion_compra_id_proveedor4)
    REFERENCES condiciones_compra(condicion_compra_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(condicion_compra_id_proveedor5)
    REFERENCES condiciones_compra(condicion_compra_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(forma_pago_id_proveedor1)
    REFERENCES formas_pago(forma_pago_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(forma_pago_id_proveedor2)
    REFERENCES formas_pago(forma_pago_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(forma_pago_id_proveedor3)
    REFERENCES formas_pago(forma_pago_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(forma_pago_id_proveedor4)
    REFERENCES formas_pago(forma_pago_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(forma_pago_id_proveedor5)
    REFERENCES formas_pago(forma_pago_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT
)
TYPE=InnoDB;

CREATE TABLE detalle_cotizacion (
  detalle_SC_id INTEGER(10) UNSIGNED NOT NULL,
  cotizacion_compra_id INTEGER(15) UNSIGNED NOT NULL,
  monto_unitario_proveedor1 DOUBLE NULL,
  marca_proveedor1 VARCHAR(255) NULL,
  monto_unitario_proveedor2 DOUBLE NULL,
  maraca_proveedor2 VARCHAR(255) NULL,
  monto_unitario_proveedor3 DOUBLE NULL,
  marca_proveedor3 VARCHAR(255) NULL,
  monto_unitario_proveedor4 DOUBLE NULL,
  marca_proveedor4 VARCHAR(255) NULL,
  monto_unitario_proveedor5 DOUBLE NULL,
  marca_proveedor5 VARCHAR(255) NULL,
  PRIMARY KEY(detalle_SC_id),
  FOREIGN KEY(detalle_SC_id)
    REFERENCES detalle_sc(detalle_SC_id)
      ON DELETE RESTRICT
      ON UPDATE RESTRICT,
  FOREIGN KEY(cotizacion_compra_id)
    REFERENCES cotizaciones_compra(cotizacion_compra_id)
      ON DELETE NO ACTION
      ON UPDATE NO ACTION
)
TYPE=InnoDB;

ALTER TABLE detalle_sc ADD COLUMN cotizacion_compra_id INTEGER(15) UNSIGNED,
 ADD CONSTRAINT cotizacion_fk FOREIGN KEY (cotizacion_compra_id)
    REFERENCES cotizaciones_compra (cotizacion_compra_id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;