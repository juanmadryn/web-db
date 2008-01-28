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

public class GeneraResumenRelojQuartzJob implements Job {

	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		DBConnection conexion = null;
		try {
			conexion = DBConnection.getConnection("partesmo","partesmo");
			ResumenHorasRelojModel dsResHorRlj = new ResumenHorasRelojModel("partesmo","partesmo");

			// The calendar is not really necessary, as we could just
			// substract (24L * 3600L * 1000L) but i put it anyway only 
			// to avoid any WTF situation.			
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(ctx.getFireTime());
			c.roll(Calendar.DAY_OF_MONTH, false);			
			java.sql.Date fecha = new java.sql.Date(c.getTimeInMillis());

			dsResHorRlj.generaResumenRelojes(fecha,fecha,conexion);		
		} catch (DataStoreException ex) {
			MessageLog.writeErrorMessage(ex, null);
		} catch (SQLException ex) {
			MessageLog.writeErrorMessage(ex, null);
		} finally {
			if (conexion != null) {
				conexion.rollback();
				conexion.freeConnection();				 
			}
		}
	}

}
