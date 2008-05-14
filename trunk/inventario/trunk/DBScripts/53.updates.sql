-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
 (705030, 'Consulta de articulos pendientes','Consulta de articulos pendientes de recepción','ConsultaArticulosParaRecepcion','MenuRecepciones');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
(705030, 1, NULL, NULL);

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;