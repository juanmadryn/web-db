USE infraestructura;
START TRANSACTION;

INSERT INTO infraestructura.menu (menu_id,nombre,descripcion,url,grupo) VALUES
  (7080, 'Editar OC','Editar Ordenes Compra','EditarOrdenCompra','MenuInventario');
 
INSERT INTO infraestructura.acceso_menu (acceso_menu_id,rol_id,menu_id,user_id)
VALUES (null,2,7080,null), (null,1,7080,null);
       

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;