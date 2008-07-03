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
			System.out.println("Inventario: Quartz initializing...");

			StdSchedulerFactory factory = (StdSchedulerFactory) 
			getServletContext().getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);

			JobDetail backupInventarioDBDetail = new JobDetail(
					"BackupInventarioDBQuartzJob", null,
					BackupInventarioDBQuartzJob.class);
			
			
			// Configura los triggers - makeDailyTrigger(hora, minuto)			
			Trigger backupInventarioDBTriger = TriggerUtils.makeDailyTrigger(1, 30);
			backupInventarioDBTriger.setName("backupInventarioDBTrigger");
			
			Scheduler scheduler = factory.getScheduler();			
			scheduler.scheduleJob(backupInventarioDBDetail, backupInventarioDBTriger);
			
			scheduler.start();

			System.out.println("Inventario: Quartz start successful!");
		} catch (SchedulerException e) {
			System.out.println("Inventario: Quartz initialization error");
			throw new ServletException(e);			
		}
	}

}
