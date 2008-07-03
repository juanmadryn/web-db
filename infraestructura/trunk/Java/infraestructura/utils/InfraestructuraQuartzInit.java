package infraestructura.utils;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerUtils;
import org.quartz.ee.servlet.QuartzInitializerServlet;
import org.quartz.impl.StdSchedulerFactory;



/**
 * Set the jobs and start the scheduler. 
 * See http://www.opensymphony.com/quartz/api/ for details.
 * 
 * @author Francisco Ezequiel Paez
 * 
 */
public class InfraestructuraQuartzInit extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -944367551302525151L;

	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	@Override
	public void init() throws ServletException {	
		super.init();
		try {	
			System.out.println("Infraestructura: Quartz initializing...");

			StdSchedulerFactory factory = (StdSchedulerFactory) 
			getServletContext().getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);

			JobDetail backupInventarioDBDetail = new JobDetail(
					"BackupInventarioDBQuartzJob", null,
					BackupInventarioDBQuartzJob.class);
			JobDetail backupInfraestructuraDBDetail = new JobDetail(
					"BackupInfraestructuraDBQuartzJob", null,
					BackupInfraestructuraDBQuartzJob.class);
			JobDetail backupPartesEQDBDetail = new JobDetail(
					"BackupPartesEQDBQuartzJob", null,
					BackupPartesEQDBQuartzJob.class);
			JobDetail backupPartesMODBDetail = new JobDetail(
					"BackupPartesMODBQuartzJob", null,
					BackupPartesMODBQuartzJob.class);
			JobDetail backupProyectosDBDetail = new JobDetail(
					"BackupProyectosDBQuartzJob", null,
					BackupProyectosDBQuartzJob.class);
			JobDetail backupTangoDBDetail = new JobDetail(
					"BackupTangoDBQuartzJob", null,
					BackupTangoDBQuartzJob.class);
			
			// Configura los triggers - makeDailyTrigger(hora, minuto)			
			Trigger backupInventarioDBTriger = TriggerUtils.makeDailyTrigger(15, 52);
			backupInventarioDBTriger.setName("backupInventarioDBTrigger");
			Trigger backupInfraestructuraDBTriger = TriggerUtils.makeDailyTrigger(1, 30);
			backupInfraestructuraDBTriger.setName("backupInfraestructuraDBTrigger");
			Trigger backupPartesEQDBTriger = TriggerUtils.makeDailyTrigger(1, 30);
			backupPartesEQDBTriger.setName("backupPartesEQDBTrigger");
			Trigger backupPartesMODBTriger = TriggerUtils.makeDailyTrigger(1, 30);
			backupPartesMODBTriger.setName("backupPartesMODBTrigger");
			Trigger backupProyectosDBTriger = TriggerUtils.makeDailyTrigger(1, 30);
			backupProyectosDBTriger.setName("backupProyectosDBTrigger");
			Trigger backupTangoDBTriger = TriggerUtils.makeDailyTrigger(1, 30);
			backupTangoDBTriger.setName("backupTangoDBTrigger");
			
			
			Scheduler scheduler = factory.getScheduler();			
			scheduler.scheduleJob(backupInventarioDBDetail, backupInventarioDBTriger);
			scheduler.scheduleJob(backupInfraestructuraDBDetail, backupInfraestructuraDBTriger);
			scheduler.scheduleJob(backupPartesEQDBDetail, backupPartesEQDBTriger);
			scheduler.scheduleJob(backupPartesMODBDetail, backupPartesMODBTriger);
			scheduler.scheduleJob(backupProyectosDBDetail, backupProyectosDBTriger);
			scheduler.scheduleJob(backupTangoDBDetail, backupTangoDBTriger);
			
			//scheduler.start();

			System.out.println("Infraestructura: Quartz start successful!");
		} catch (SchedulerException e) {
			System.out.println("Infraestructura: Quartz initialization error");
			throw new ServletException(e);			
		}
	}

}
