package partesMO.util;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import partesMO.models.PartesMoModel;

import com.salmonllc.sql.DBConnection;
import com.salmonllc.sql.DataStoreException;
import com.salmonllc.util.MessageLog;

public class LiquidarPeriodoQuartzJob implements Job {

	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		DBConnection conexion = null;
		try {
			conexion = DBConnection.getConnection("partesmo","partesmo");
			PartesMoModel dsParteMo = new PartesMoModel("partesmo","partesmo");

			GregorianCalendar c = new GregorianCalendar();
			c.setTime(ctx.getFireTime());
			c.roll(Calendar.MONTH, false);
			dsParteMo.liquidarPeriodo(c.get(Calendar.MONTH), c.get(Calendar.YEAR));
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
