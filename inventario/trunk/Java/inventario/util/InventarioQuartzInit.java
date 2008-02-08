package inventario.util;

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
			// Launch the trigger everyday at 1 am
			Trigger replicateSta11Trigger = TriggerUtils.makeDailyTrigger(1, 0);			
			replicateSta11Trigger.setName("replicateSta11Trigger");

			Scheduler scheduler = factory.getScheduler();
			scheduler.scheduleJob(replicateSta11Detail, replicateSta11Trigger);

			scheduler.start();

			System.out.println("Inventario: Quartz start successful!");
		} catch (SchedulerException e) {
			e.printStackTrace();
			System.out.println("Inventario: Quartz initialization error");
		}
	}

}
