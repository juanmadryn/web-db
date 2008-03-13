USE infraestructura;
START TRANSACTION;

INSERT INTO roles(rol_id,nombre,descripcion,observaciones) VALUES (null,'COMPRADOR',null,null);
INSERT INTO roles(rol_id,nombre,descripcion,observaciones) VALUES (null,'SOLICITANTE',null,null);

ROLLBACK;
-- Si se ejecuto sin errores, comentar el rollback y descomentar el commit
-- COMMIT;