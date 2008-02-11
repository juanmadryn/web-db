-- This stored procedure iterate over fichada table and updates resumen_horas_reloj with clocks marks info
DELIMITER $$
DROP FUNCTION IF EXISTS partesMO.parserHoraMinuto$$
CREATE FUNCTION partesMO.parserHoraMinuto (hora VARCHAR(5)) RETURNS float DETERMINISTIC
BEGIN
	DECLARE index2ptos,horaf,minf INT;
	SET index2ptos = LOCATE(':',hora);
	IF index2ptos = 0 THEN
		SET index2ptos = LOCATE('.',hora);
	END IF;
	IF index2ptos != 0 THEN
		SET horaf = CAST(SUBSTR(hora,1,index2ptos - 1) AS SIGNED);
		SET minf = CAST(SUBSTR(hora FROM index2ptos + 1) AS SIGNED);
		IF CHAR_LENGTH(SUBSTR(hora FROM index2ptos + 1)) = 1 THEN
			SET minf = minf * 10;
		END IF;
	ELSE
		SET horaf = CAST(hora AS SIGNED);
		SET minf = 0;
	END IF;
	RETURN horaf + ((minf * 100) / 60) / 100;
END$$
DELIMITER ;$$