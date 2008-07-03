package infraestructura.utils;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * 
 * @author Juan Manuel Cortez
 */
public class BackupTangoDBQuartzJob implements Job {

	/**
	 * Ejecuta backup de la base de datos de INVENTARIO
	 * 
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DatabaseBackupUtility.backupInventarioDB("tango");
	}

	
}
