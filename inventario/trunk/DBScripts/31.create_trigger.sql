USE inventario;

-- Elimina los atributos asociados a una OC o SC al momento de ser eliminadas

-- IMPORTANTE: ejecutar el script como usuario root o con privilegios sobre infraestructura.
-- Al momento de ejecutarse el trigger se utilizaran los privilegios de acceso del usuario 
-- que ejecuto CREATE TRIGGER.

DROP TRIGGER IF EXISTS inventario.elimina_atr_oc;
DROP TRIGGER IF EXISTS inventario.elimina_atr_sc;

DELIMITER $$ 
CREATE TRIGGER inventario.elimina_atr_oc BEFORE DELETE ON inventario.ordenes_compra FOR EACH ROW
BEGIN
	DELETE FROM infraestructura.atributos_entidad WHERE
	nombre_objeto = 'ordenes_compra' AND tipo_objeto = 'TABLA'
	AND objeto_id = OLD.orden_compra_id;
END$$

CREATE TRIGGER inventario.elimina_atr_sc BEFORE DELETE ON inventario.solicitudes_compra FOR EACH ROW
BEGIN
	DELETE FROM infraestructura.atributos_entidad WHERE
	nombre_objeto = 'solicitudes_compra' AND tipo_objeto = 'TABLA'
	AND objeto_id = OLD.solicitud_compra_id;
END$$

DELIMITER ;$$