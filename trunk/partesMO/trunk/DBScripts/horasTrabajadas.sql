-- Calcula el total de horas trabajadas, retorna la cantidad de tiempo transcurrido en horas decimales 
DELIMITER $$
DROP PROCEDURE IF EXISTS partesMO.horasTrabajadas$$ 
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
DELIMITER ;$$