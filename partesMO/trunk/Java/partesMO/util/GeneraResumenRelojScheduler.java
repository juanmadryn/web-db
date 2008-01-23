package partesMO.util;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import partesMO.models.ResumenHorasRelojModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

/**
 * Executed by Sofia's scheduler 
 * @author fep
 */
public class GeneraResumenRelojScheduler {

	public void generaResumenReloj() {
		DBConnection conexion = null;
		try {
			conexion = DBConnection.getConnection("partesmo","partesmo");
			ResumenHorasRelojModel dsResHorRlj = new ResumenHorasRelojModel("partesmo","partesmo");

			// I think that the calendar is not really necessary, it could be
			// replaced with System.getCurrentTime() but i put it anyway only 
			// to avoid any WTF situation
			GregorianCalendar cal = new GregorianCalendar();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			java.sql.Date fecha = new java.sql.Date(cal.getTimeInMillis());

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
