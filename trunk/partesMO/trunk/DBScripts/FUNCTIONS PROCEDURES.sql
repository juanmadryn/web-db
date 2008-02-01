-- Calcula el total de horas trabajadas, retorna la cantidad de tiempo transcurrido en horas decimales 
DELIMITER $$
CREATE FUNCTION partesMO.horasTrabajadas (horaDesde int, minutoDesde int, horaHasta int, minutoHasta int) RETURNS float DETERMINISTIC
BEGIN
	DECLARE tmpHoras, tmpMinutosRemanentesDesde, tmpMinutosRemanentesHasta, tmpMinutos INT DEFAULT 0;
	DECLARE horas float DEFAULT 0;

	SET tmpHoras = horaHasta - (HoraDesde + 1);
	SET tmpMinutosRemanentesDesde = 60 - minutoDesde;
	SET tmpMinutosRemanentesHasta = minutoHasta;
	SET tmpMinutos = tmpMinutosRemanentesDesde + tmpMinutosRemanentesHasta;

	IF tmpMinutos > 59 THEN
		SET tmpMinutos = tmpMinutos - 60;
		SET tmpHoras = tmpHoras + 1;
	END IF;

	SET tmpMinutos = (tmpMinutos * 100 / 60);
	SET horas = tmpHoras + tmpMinutos / 100.00;

	RETURN horas;
END$$

-- Preprocesa los partes de mano de obra entre las fechas especificadas 
CREATE PROCEDURE partesMO.procesaPartesMoRlj (desde DATE, hasta DATE) 
BEGIN
	DECLARE done BOOLEAN DEFAULT FALSE;	
  	DECLARE parteid INT(15);
    DECLARE nrolegajo INT(15);
    DECLARE fecha1 DATE;
	DECLARE horadesde, horahasta, horahasta2, hora1rem float;
	DECLARE cur1 CURSOR FOR 
		SELECT parte_id,nro_legajo,fecha,hora_desde,hora_hasta
		FROM partesMO.partes_mo WHERE partes_mo.fecha between desde AND hasta 
		AND partes_mo.estado in ('0003.0002','0003.0004','0003.0006')
		ORDER BY partes_mo.nro_legajo asc, partes_mo.fecha asc, partes_mo.hora_desde+0 asc;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET no_more_rows = TRUE;
	
	OPEN cur1;
	START TRANSACTION;

	partesLoop: LOOP
		FETCH cur1 INTO parteid,nrolegajo,fecha1,horadesde,horahasta;

		IF no_more_rows THEN
			CLOSE cur1;
			LEAVE partesLoop;
		END IF;
		
		SET horahasta2 = 0;  		
					
		IF horahasta < horadesde THEN
			SET horahasta2 = horahasta;				
			SET horahasta = 24;							
		END IF;
		SET hora1rem = horahasta - horadesde;

		INSERT INTO partesMO.resumen_horas_reloj(fecha,nro_legajo,parte_d,parte_h,horas_parte,parte_ids) 
			VALUES (fecha1,nrolegajo,horadesde,horahasta,hora1rem,parteid) 
			ON DUPLICATE KEY UPDATE parte_h = horahasta, horas_parte = horas_parte + hora1rem, 
				parte_idsm = CONCAT_WS(',',parte_ids,parteid);
			
		IF horahasta2 THEN
			INSERT INTO partesMO.resumen_horas_reloj(fecha,nro_legajo,parte_d,parte_h,horas_parte,parte_ids)
				VALUES (fecha1 + INTERVAL 1 DAY, nrolegajo, 0, horahasta2, horahasta2,parteid)
				ON DUPLICATE KEY UPDATE parte_h = horahasta2, horas_parte = horas_parte + horahasta2, 
					parte_ids = CONCAT_WS(',',parte_ids,parteid);
		END IF;
	END LOOP;
	  	
	COMMIT;
END$$