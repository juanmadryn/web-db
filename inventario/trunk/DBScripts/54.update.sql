-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
 	(701090, 'Tabla de conversiones','Tabla de conversiones','AbmcConversiones','ConfigurarInventario');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
	(701090, 1, NULL, NULL); -- administrador

-- ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
COMMIT;