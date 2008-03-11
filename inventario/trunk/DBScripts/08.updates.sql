USE infraestructura;
START TRANSACTION;

INSERT INTO infraestructura.menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (700540, 'Cadenas de aprobación','ABMC de las cadenas de aprobacion de SC','AbmcCadenasAprobacion','ConfigurarInventario');
 
INSERT INTO infraestructura.acceso_menu (acceso_menu_id,rol_id,menu_id,user_id)
VALUES (null,2,700540,null), (null,1,700540,null);
       

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;