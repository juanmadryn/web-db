-- Issue 21

USE infraestructura;
START TRANSACTION;

-- Se debe implementar un nuevo estado "Anulado" que desde cualquier estado
-- anterior a la generación de OC permita pasar a dicho estado.
-- Habilitar solo si no se va a utilizar el estado "Rechazado" ya existente
INSERT INTO estados VALUES
	("0006.0009", "Anulada", "La solicitud de compra ha sido anulada", NULL, "0006");
INSERT INTO acciones_apps VALUES
	(NULL, "Anular", "Anula la solicitud de compra", NULL, "0006");

-- Accion. Modificar el nombre de la acción según sea neceario en el 
-- servidor de producción.
SET @accion = 19;

-- Estado final. Modificar segun sea 'Rechazado' o 'Anulado'
SET @estado_final = '0006.0004';

-- Transiciones
INSERT IGNORE INTO transicion_estados VALUES
	("0006.0001", @accion, @estado_final, "Rechazar", "No validar"),
	("0006.0002", @accion, @estado_final, "Rechazar", "No validar"),
	("0006.0003", @accion, @estado_final, "Rechazar", "No validar"),
	("0006.0005", @accion, @estado_final, "Rechazar", "No validar"),
	("0006.0008", @accion, @estado_final, "Rechazar", "No validar");

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;