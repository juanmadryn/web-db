-- Allow user partesMO to create stored procedures and functions
GRANT CREATE RUTINE ON partesMO.* TO 'partesMO'@'localhost';

-- The following sentences are not needed if automatic_sp_privileges != 0
-- GRANT EXECUTE ON PROCEDURE partesmo.myproc TO 'partesMO'@'localhost';
-- GRANT ALTER ROUTINE ON PROCEDURE partesmo.myproc TO 'partesMO'@'localhost'

-- Only if a java.sql.SQLException is thrown when using a JDBC driver
-- GRANT select ON mysql.proc to 'partesmo'@'localhost' IDENTIFIED BY 'partesmo';
