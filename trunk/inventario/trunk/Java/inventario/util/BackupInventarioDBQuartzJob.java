package inventario.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.salmonllc.properties.Props;

/**
 * Extrae información de condiciones de compra de la tabla CPA49 de Tango y la
 * agrega o actualiza en la tabla correspondiente en MySQL
 * 
 * @author Francisco Paez
 */
public class BackupInventarioDBQuartzJob implements Job {

	/**
	 * Parametros de conexion a Tango
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		backupInventarioDB();
	}

	/**
	 * Importa las condiciones de compra especificadas en Tango
	 * 
	 * @throws JobExecutionException
	 */
	public void backupInventarioDB() throws JobExecutionException {
		String user = Props.getProps("inventario", "inventario").getProperty("inventario.DBUser");
		String password = Props.getProps("inventario", "inventario").getProperty("inventario.DBPassword");
		user = "inventario";
		password = "inventario";
		System.out.println(user + " " + password);
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec("\"C:\\Archivos de programa\\MySQL\\MySQL Server 5.0\\bin\\mysqldump\" --opt --password="+user+" --user="+password+" inventario > C:\\archivo.sql");
			//proc.waitFor();
			//int exitVal = proc.exitValue();			
			//System.out.println("Process exitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
}
