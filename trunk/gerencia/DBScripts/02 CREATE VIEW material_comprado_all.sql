DROP VIEW IF EXISTS `inventario`.`material_comprado_all`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `inventario`.`material_comprado_all` AS select `pt`.`proyecto_id` AS `proyecto_id`,`pt`.`proyecto` AS `proyecto`,`pt`.`tipo_proyecto` AS `tipo_proyecto`,`pt`.`nombre_proyecto` AS `nombre_proyecto`,`pt`.`descripcion_proyecto` AS `descripcion_proyecto`,`pt`.`observacioens_proyecto` AS `observacioens_proyecto`,`pt`.`estado_id_proyecto` AS `estado_id_proyecto`,`pt`.`estado_proyecto` AS `estado_proyecto`,`pt`.`cliente_id` AS `cliente_id`,`pt`.`cliente_codigo` AS `cliente_codigo`,`pt`.`cliente_nombre` AS `cliente_nombre`,`pt`.`cliente_descripcion` AS `cliente_descripcion`,`pt`.`vigencia_desde_proyecto` AS `vigencia_desde_proyecto`,`pt`.`vigencia_hasta_proyecto` AS `vigencia_hasta_proyecto`,`pt`.`duracion_proyecto` AS `duracion_proyecto`,`pt`.`tarea_id` AS `tarea_id`,`pt`.`nombre_tarea` AS `nombre_tarea`,`pt`.`descripcion_tarea` AS `descripcion_tarea`,`pt`.`observaciones_tarea` AS `observaciones_tarea`,`pt`.`estado_id_tarea` AS `estado_id_tarea`,`pt`.`estado_tarea` AS `estado_tarea`,`pt`.`clase_tarea_id` AS `clase_tarea_id`,`pt`.`clase_tarea_nombre` AS `clase_tarea_nombre`,`dsc`.`detalle_SC_id` AS `detalle_SC_id`,`dsc`.`articulo_id` AS `articulo_id`,`art`.`nombre` AS `articulo_nombre`,`art`.`descripcion` AS `articulo_descripcion`,`art`.`descripcion_completa` AS `articulo_descripcion_completa`,`art`.`clase_articulo_desc` AS `articulo_clase`,`dsc`.`descripcion` AS `descripcion`,`dsc`.`observaciones` AS `observaciones`,`dsc`.`articulo_id_solicitado` AS `articulo_id_solicitado`,`art_sol`.`nombre` AS `articulo_nombre_sol`,`art_sol`.`descripcion` AS `articulo_descripcion_sol`,`art_sol`.`clase_articulo_desc` AS `articulo_clase_sol`,`dsc`.`solicitud_compra_id` AS `solicitud_compra_id`,`sc`.`user_id_solicita` AS `solicitante_id`,`usu_sol`.`nro_legajo` AS `solicitante_legajo`,`usu_sol`.`nombre_completo` AS `solicitante_nombre`,`sc`.`estado` AS `estado_id_sm`,`esc`.`nombre` AS `estado_sm`,`sc`.`fecha_solicitud` AS `fecha_solicitud`,`sc`.`fecha_aprobacion` AS `fecha_aprobacion_sm`,`dsc`.`orden_compra_id` AS `orden_compra_id`,`infraestructura`.`getAtributoTabla`(_utf8'ordenes_compra',`dsc`.`orden_compra_id`,_utf8'N_ORDEN_CO') AS `orden_compra_tango`,`oc`.`entidad_id_proveedor` AS `proveedor_id`,`prov`.`codigo` AS `proveedor_codigo`,`prov`.`nombre` AS `proveedor_nombre`,`oc`.`user_id_comprador` AS `comprador_id`,`usu_oc`.`nro_legajo` AS `comprador_legajo`,`usu_oc`.`nombre_completo` AS `comprador_nombre`,`oc`.`estado` AS `estado_id_oc`,`eoc`.`nombre` AS `estado_oc`,`oc`.`fecha` AS `fecha_oc`,`oc`.`fecha_aprobacion` AS `fecha_aprobacion_oc`,`oc`.`fecha_estimada_entrega` AS `fecha_estimada_entrega`,`oc`.`fecha_entrega_completa` AS `fecha_entrega_completa`,`oc`.`descripcion` AS `descripcion_oc`,`oc`.`observaciones` AS `observaciones_oc`,`dsc`.`observaciones_oc` AS `observaciones2_oc`,`oc`.`condicion_compra_id` AS `condicion_compra_id`,`aec`.`fecha` AS `fecha_emision_oc`,`cond_oc`.`nombre` AS `condicion_compra_codigo`,`cond_oc`.`descripcion` AS `condicion_compra_descripcion`,`oc`.`descuento` AS `descuento_oc`,(select avg(`f`.`tasa_recepcion`) AS `avg(``f``.``tasa_recepcion``)` from (`inventario`.`detalle_sc` `d` join `inventario`.`fecha_ultima_recepcion` `f` on((`f`.`detalle_sc_id` = `d`.`detalle_SC_id`))) where (`d`.`orden_compra_id` = `dsc`.`orden_compra_id`) group by `d`.`orden_compra_id`) AS `tasa_promedio_recepcion_oc`,(select max(`f`.`fecha_ultima_recepcion`) AS `max(``f``.``fecha_ultima_recepcion``)` from (`inventario`.`detalle_sc` `d` join `inventario`.`fecha_ultima_recepcion` `f` on((`f`.`detalle_sc_id` = `d`.`detalle_SC_id`))) where (`d`.`orden_compra_id` = `dsc`.`orden_compra_id`) group by `d`.`orden_compra_id`) AS `fecha_ultima_recepcion_oc`,`dsc`.`cantidad_solicitada` AS `cantidad_solicitada`,`dsc`.`cantidad_pedida` AS `cantidad_pedida`,`dsc`.`cantidad_recibida` AS `cantidad_recibida`,`dsc`.`monto_unitario` AS `monto_unitario`,`dsc`.`monto_ultima_compra` AS `monto_ultima_compra`,`dsc`.`fecha_ultima_compra` AS `fecha_ultima_compra`,(select max(`r`.`fecha`) AS `max(``r``.``fecha``)` from ((`inventario`.`detalle_sc` `d` left join `inventario`.`detalles_rc` `drc` on((`drc`.`detalle_sc_id` = `d`.`detalle_SC_id`))) left join `inventario`.`recepciones_compras` `r` on((`r`.`recepcion_compra_id` = `drc`.`recepcion_compra_id`))) where (`d`.`detalle_SC_id` = `dsc`.`detalle_SC_id`) group by `d`.`detalle_SC_id`) AS `fecha_ultima_recepcion_detalle`,`dsc`.`unidad_medida_id` AS `um_id`,`um`.`nombre` AS `um`,`um`.`descripcion` AS `um_descripcion`,`dsc`.`unidad_medida_id_pedida` AS `unidad_medida_id_pedida`,`dsc`.`iva` AS `iva`,`dsc`.`descuento` AS `descuento`,`dsc`.`cotizacion_compra_id` AS `cotizacion_compra_id` from (((((((((((((`inventario`.`detalle_sc` `dsc` join `proyectos`.`proyectos_tareas_all` `pt` on((`pt`.`tarea_id` = `dsc`.`tarea_id`))) join `inventario`.`articulos_all` `art` on((`art`.`articulo_id` = `dsc`.`articulo_id`))) left join `inventario`.`articulos_all` `art_sol` on((`art_sol`.`articulo_id` = `dsc`.`articulo_id_solicitado`))) join `inventario`.`solicitudes_compra` `sc` on((`sc`.`solicitud_compra_id` = `dsc`.`solicitud_compra_id`))) join `infraestructura`.`website_user` `usu_sol` on((`usu_sol`.`user_id` = `sc`.`user_id_solicita`))) join `infraestructura`.`estados` `esc` on((`esc`.`estado` = `sc`.`estado`))) left join `inventario`.`ordenes_compra` `oc` on((`oc`.`orden_compra_id` = `dsc`.`orden_compra_id`))) left join `infraestructura`.`entidad_externa` `prov` on((`prov`.`entidad_id` = `oc`.`entidad_id_proveedor`))) left join `infraestructura`.`website_user` `usu_oc` on((`usu_oc`.`user_id` = `oc`.`user_id_comprador`))) left join `infraestructura`.`estados` `eoc` on((`eoc`.`estado` = `oc`.`estado`))) left join `inventario`.`fecha_emision_oc` `aec` on((`aec`.`registro_id` = `dsc`.`orden_compra_id`))) left join `inventario`.`condiciones_compra` `cond_oc` on((`cond_oc`.`condicion_compra_id` = `oc`.`condicion_compra_id`))) join `inventario`.`unidades_medida` `um` on((`um`.`unidad_medida_id` = `dsc`.`unidad_medida_id`)));