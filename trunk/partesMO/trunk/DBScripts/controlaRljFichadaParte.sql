DELIMITER $$ 
DROP PROCEDURE IF EXISTS partesMO.controlaRljFichadaParte$$
CREATE PROCEDURE partesMO.controlaRljFichadaParte (tolerancia_d FLOAT, tolerancia_h FLOAT, fecha_desde DATE, fecha_hasta DATE) 
BEGIN
	UPDATE resumen_horas_reloj 
	SET
		estado = 4,
		observaciones = 'No se encontraron partes'
	WHERE
		parte_ids IS NULL 
		AND fecha BETWEEN fecha_desde AND fecha_hasta;

	UPDATE resumen_horas_reloj 
	SET
		estado = 3,
		observaciones = 'No se encontraron fichadas'
	WHERE
		fichada_ids IS NULL
		AND fecha BETWEEN fecha_desde AND fecha_hasta;

	UPDATE resumen_horas_reloj 
	SET
		estado = 1,
		observaciones = 'Ok'
	WHERE
		fichada_ids IS NOT NULL AND parte_ids IS NOT NULL
		AND (ABS(parte_d - fichada_d) <= tolerancia_d)
		AND (ABS(parte_h - fichada_h) <= tolerancia_h)
		AND (ABS(horas_parte - horas_fichada) <= (tolerancia_d + tolerancia_h))
		AND fecha BETWEEN fecha_desde AND fecha_hasta;

	UPDATE resumen_horas_reloj 
	SET
		estado = 2,
		observaciones = 'Valores no concuerdan'
	WHERE
		estado IS NULL
		AND fecha BETWEEN fecha_desde AND fecha_hasta;	
END$$
DELIMITER ;$$