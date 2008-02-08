package partesMO.util;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import partesMO.models.ResumenHorasRelojModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * Quartz job for periodically execute getFichadasFromTango
 * 
 * @author Francisco Ezequiel Paez
 *
 */
public class GetFichadasQuartzJob implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		DBConnection conexion = null;
		
		try {			
			conexion = DBConnection.getConnection("partesmo","partesmo");
			ResumenHorasRelojModel dsResHorRlj = new ResumenHorasRelojModel("partesmo","partesmo");

			// The calendar is not really necessary, as we could just
			// substract (24L * 3600L * 1000L) but i put it anyway only 
			// to avoid any WTF situation.			
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(arg0.getFireTime());
			c.roll(Calendar.DAY_OF_MONTH, false);			
			java.sql.Date sfecha = new java.sql.Date(c.getTimeInMillis());

			conexion.beginTransaction();
			dsResHorRlj.getFichadasFromTango(sfecha, sfecha, conexion);
			conexion.commit();		
		} catch (SQLException e) {
			MessageLog.writeErrorMessage(e, null);
		} catch (DataStoreException e) {
			MessageLog.writeErrorMessage(e, null);
		} finally {
			if (conexion != null) {
				conexion.rollback();
				conexion.freeConnection();
			}
		}
	}
}
