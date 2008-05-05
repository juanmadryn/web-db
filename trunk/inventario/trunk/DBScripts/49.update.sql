-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
 	(701080, 'Condiciones de Compra','Condiciones de Compra','AbmcCondicionesCompra','ConfigurarInventario');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
	(701080, 1, NULL, NULL), -- administrador
	(701080, 4, NULL, NULL); -- comprador

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;