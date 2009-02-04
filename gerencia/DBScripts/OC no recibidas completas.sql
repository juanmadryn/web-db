SELECT sum(t1.cantidad_recibida) as cantidad_recibida,t1.detalle_sc_id,t2.cantidad_pedida,t2.orden_compra_id
FROM inventario.detalles_rc t1,
     inventario.detalle_sc t2
where t1.detalle_sc_id = t2.detalle_sc_id
group by t1.detalle_sc_id,t2.cantidad_pedida,t2.orden_compra_id
having cantidad_recibida < cantidad_pedida