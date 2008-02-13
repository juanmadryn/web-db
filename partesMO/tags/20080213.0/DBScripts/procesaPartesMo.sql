-- Preprocesa los partes de mano de obra entre las fechas especificadas
DELIMITER $$ 
DROP PROCEDURE IF EXISTS partesMO.procesaPartesMo$$
CREATE PROCEDURE partesMO.procesaPartesMo(desde DATE, hasta DATE) 
BEGIN
	DECLARE no_more_rows BOOLEAN DEFAULT FALSE;	
  	DECLARE parteid INT(15);
    DECLARE nrolegajo INT(15);
	DECLARE index2ptos INT;
    DECLARE fecha1 DATE;
	DECLARE hora_d, hora_h VARCHAR(5);
	DECLARE horadesde, horahasta, horahasta2, hora1rem float;
	DECLARE ape_y_nom VARCHAR(90);
	DECLARE cur1 CURSOR FOR 
		SELECT parte_id,nro_legajo,fecha,hora_desde,hora_hasta,apeynom
		FROM partesMO.partes_mo WHERE partes_mo.fecha between desde AND hasta 
		AND partes_mo.estado in ('0003.0002','0003.0004','0003.0006')
		ORDER BY partes_mo.nro_legajo asc, partes_mo.fecha asc, partes_mo.hora_desde+0 asc;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_rows = TRUE;
	
	OPEN cur1;

	partesLoop: LOOP
		FETCH cur1 INTO parteid,nrolegajo,fecha1,hora_d,hora_h,ape_y_nom;

		IF no_more_rows THEN
			CLOSE cur1;
			LEAVE partesLoop;
		END IF;
		
		SET horahasta2 = 0;

		SET horadesde = parserHoraMinuto(hora_d);
		SET horahasta = parserHoraMinuto(hora_h);
					
		IF horahasta < horadesde THEN
			SET horahasta2 = horahasta;				
			SET horahasta = 24;			
		END IF;		
		SET hora1rem = horahasta - horadesde;

		-- INSERT .. ON DUPLICATE KEY UPDATE is a non-standard mysql extension
		INSERT INTO partesMO.resumen_horas_reloj(fecha,nro_legajo,apeynom,parte_d,parte_h,horas_parte,parte_ids) 
			VALUES (fecha1,nrolegajo,ape_y_nom,horadesde,horahasta,hora1rem,parteid) 
			ON DUPLICATE KEY UPDATE parte_h = horahasta, horas_parte = horas_parte + hora1rem, 
				parte_ids = CONCAT_WS(',',parte_ids,parteid);
				
		IF horahasta2 THEN
			INSERT INTO partesMO.resumen_horas_reloj(fecha,nro_legajo,apeynom,parte_d,parte_h,horas_parte,parte_ids)
				VALUES (fecha1 + INTERVAL 1 DAY, nrolegajo, ape_y_nom, 0, horahasta2, horahasta2,parteid)
				ON DUPLICATE KEY UPDATE parte_h = horahasta2, horas_parte = horas_parte + horahasta2, 
					parte_ids = CONCAT_WS(',',parte_ids,parteid);
		END IF;
	END LOOP;
END$$