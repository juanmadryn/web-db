-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
 	(702030, 'Stock','Stock de art�culos','ConsultaResumenesSaldoArticulos','MenuArticulos');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
	(702030, 1, NULL, NULL);
	

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;