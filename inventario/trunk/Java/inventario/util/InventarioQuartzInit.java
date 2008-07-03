package inventario.util;

import infraestructura.utils.BackupInventarioDBQuartzJob;

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
public class InventarioQuartzInit extends HttpServlet implements Servlet {

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

			JobDetail replicateSta11Detail = new JobDetail(
					"ReplicateSta11QuartzJob", null,
					ReplicateSta11QuartzJob.class);
			JobDetail replicateCpa01Detail = new JobDetail(
					"ReplicateCpa01QuartzJob", null,
					ReplicateCpa01QuartzJob.class);
			JobDetail replicateCpa49Detail = new JobDetail(
					"ReplicateCpa49QuartzJob", null,
					ReplicateCpa49QuartzJob.class);			
			
			
			// Configura los triggers - makeDailyTrigger(hora, minuto)			
			Trigger replicateSta11Trigger = TriggerUtils.makeDailyTrigger(1, 0);
			replicateSta11Trigger.setName("replicateSta11Trigger");			
			Trigger replicateCpa01Trigger = TriggerUtils.makeDailyTrigger(1, 10);
			replicateCpa01Trigger.setName("replicateCpa01Trigger");			
			Trigger replicateCpa49Trigger = TriggerUtils.makeDailyTrigger(1, 15);
			replicateCpa49Trigger.setName("replicateCpa49Trigger");			
			
			Scheduler scheduler = factory.getScheduler();
			scheduler.scheduleJob(replicateSta11Detail, replicateSta11Trigger);
			scheduler.scheduleJob(replicateCpa01Detail, replicateCpa01Trigger);
			scheduler.scheduleJob(replicateCpa49Detail, replicateCpa49Trigger);
						
			scheduler.start();

			System.out.println("Inventario: Quartz start successful!");
		} catch (SchedulerException e) {
			System.out.println("Inventario: Quartz initialization error");
			throw new ServletException(e);			
		}
	}

}
