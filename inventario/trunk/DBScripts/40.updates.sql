-- Data for the 'menu' table
USE infraestructura;
START TRANSACTION;

INSERT INTO menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7050, 'Recepciones','Manejo de recepciones de compras','MenuRecepciones','MenuInventario'),
  (705010, 'Carga de recepciones','ABM recepciones','AbmRecepciones','MenuRecepciones'),
 (705020, 'Consulta de recepciones','Consulta de recepciones','ConsultaRecepcionesCompras','MenuRecepciones');

INSERT INTO acceso_menu (menu_id,rol_id,user_id,acceso_menu_id) VALUES
  (7050, 1, NULL, NULL),
  (705010, 1, NULL, NULL),
(705020, 1, NULL, NULL);

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;