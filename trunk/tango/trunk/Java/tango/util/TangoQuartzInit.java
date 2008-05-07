package tango.util;

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
public class TangoQuartzInit extends HttpServlet implements Servlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -944367551302525151L;

	@Override
	public void init() throws ServletException {	
		super.init();
		try {	
			System.out.println("Tango: Quartz initializing...");

			StdSchedulerFactory factory = (StdSchedulerFactory) 
			getServletContext().getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);

			JobDetail replicateLegajoDetail = new JobDetail(
					"ReplicateLegajoQuartzJob", null,
					ReplicateLegajoQuartzJob.class);
			
			// Configura los triggers - makeDailyTrigger(hora, minuto)			
			Trigger replicateCpa49Trigger = TriggerUtils.makeDailyTrigger(1, 30);
			replicateCpa49Trigger.setName("replicateLegajoTrigger");
			
			Scheduler scheduler = factory.getScheduler();
			scheduler.scheduleJob(replicateLegajoDetail, replicateCpa49Trigger);
			
			scheduler.start();

			System.out.println("Tango: Quartz start successful!");
		} catch (SchedulerException e) {
			System.out.println("Tango: Quartz initialization error");
			throw new ServletException(e);			
		}
	}

}
