 -- Conexión: mysql e-web-on-003
 -- Host: localhost
 -- Guardado: 2009-01-25 20:14:58
 -- 
SELECT proyecto,nombre_proyecto,solicitante_nombre,estado_sm,
                 orden_compra_id,solicitud_compra_id,
                 proveedor_codigo,proveedor_nombre,comprador_nombre,estado_oc,observaciones_oc,
                 condicion_compra_descripcion,sum(ifnull(cantidad_pedida,ifnull(cantidad_solicitada,0))*ifnull(monto_unitario,0)) as total_compra,
                 fecha_solicitud,	fecha_aprobacion_sm,fecha_oc,fecha_aprobacion_oc,fecha_emision_oc,fecha_estimada_entrega,
                 fecha_entrega_completa
FROM `material_comprado`
WHERE 1 = 1
--AND orden_compra_id = 404
GROUP BY proyecto,nombre_proyecto,solicitante_nombre,estado_sm,
                 orden_compra_id,solicitud_compra_id,
                 proveedor_codigo,proveedor_nombre,comprador_nombre,estado_oc,observaciones_oc,
                 condicion_compra_descripcion,fecha_solicitud,	fecha_aprobacion_sm,fecha_oc,fecha_aprobacion_oc,fecha_emision_oc,fecha_estimada_entrega,
                 fecha_entrega_completa WITH ROLLUP
