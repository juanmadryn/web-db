-- This stored procedure iterate over fichada table and updates resumen_horas_reloj with clocks marks info
DELIMITER $$
DROP PROCEDURE IF EXISTS partesMO.procesaFichadas$$
CREATE PROCEDURE partesMO.procesaFichadas (desde DATE, hasta DATE)
BEGIN
	DECLARE id_fichada INT(15);
	DECLARE id_parte_diario INT(15);
    DECLARE parteAnt INT(15);
	DECLARE fichada DATETIME;
    DECLARE nrolegajo INT(15);
	DECLARE apellido VARCHAR(20);
	DECLARE nombre VARCHAR(20);
	DECLARE origen_fichada VARCHAR(10);
	DECLARE hora_f, minuto_f INT;
	DECLARE horas_trabajadas, horaFichada, horaFichadaAnt, totalHoras, horaFichadaI FLOAT;
	DECLARE contadorFichadas INT;
	DECLARE no_more_rows BOOLEAN DEFAULT FALSE;
	declare cursor1 cursor for
		SELECT F.ID_PARTE_DIARIO,F.ID_FICHADA,F.FICHADA, F.NRO_LEGAJO, F.APELLIDO, F.NOMBRE, F.ORIGEN_FICHADA 
			FROM fichadas F WHERE DATE(F.FICHADA) BETWEEN desde AND hasta AND F.ID_PARTE_DIARIO IS NOT NULL 
			ORDER BY F.NRO_LEGAJO ASC, F.FICHADA ASC, F.ID_FICHADA ASC;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_rows = TRUE;
	DECLARE CONTINUE HANDLER FOR SQLWARNING BEGIN END; 

	OPEN cursor1;
	
	SET contadorFichadas = 0;
	SET horaFichadaAnt = 0.00;    
	SET parteAnt = 0;
		
	fichadaLoop: LOOP
		FETCH cursor1 INTO id_parte_diario, id_fichada, fichada, nrolegajo, apellido, nombre, origen_fichada;

		IF no_more_rows THEN
			CLOSE cursor1;
			LEAVE fichadaLoop;
		END IF;
		
		SET contadorFichadas = contadorFichadas + 1;
		
		SET hora_f = HOUR(TIME(fichada));
		SET minuto_f = MINUTE(TIME(fichada));

		SET horaFichada = hora_f + ((minuto_f * 100.00) / 60.00) / 100.00;

		-- If a new 'parte diario' is being processed, then update the start time
		IF parteAnt != id_parte_diario THEN
			SET horaFichadaI = horaFichada;
			SET horaFichadaAnt = 0.00;
		END IF;

		-- If two records n (start mark) and n+1 (end mark), were found,
		-- calculate the time elapsed between them. Otherwise, totalHoras is zero.
		SET totalHoras = 0.00;
		IF contadorFichadas % 2 = 0 THEN
			SET totalHoras = horaFichada - horaFichadaAnt;
		END IF;
		
		UPDATE resumen_horas_reloj SET
			fichada_d = horaFichadaI,
			fichada_h = horaFichada,
			horas_fichada = horas_fichada + totalHoras,
			fichada_ids = concat_ws(',',fichada_ids,id_fichada),
			horarios = concat_ws(' - ',horarios,date_format(fichada,'%H:%i'))
		WHERE nro_legajo = nrolegajo AND fecha = DATE(fichada) AND estado IS NULL;
		
		-- the ignore option is a mysql non-standard sql extension
		INSERT IGNORE INTO resumen_horas_reloj(fecha,nro_legajo,apeynom,fichada_d,fichada_h,horas_fichada,fichada_ids,horarios) 
			VALUES (DATE(fichada),nrolegajo,concat(apellido,", ",nombre),horaFichada,horaFichada,totalHoras,id_fichada,DATE_FORMAT(fichada, '%H:%i'));		
		
		SET horaFichadaAnt = horaFichada;
		SET parteAnt = id_parte_diario;
	END LOOP;
END$$
DELIMITER ;$$