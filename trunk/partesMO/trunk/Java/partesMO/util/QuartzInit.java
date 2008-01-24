package partesMO.util;

import java.util.Date;

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

public class QuartzInit extends HttpServlet implements Servlet {
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = -944367551302525151L;
	
	@Override
	public void init() throws ServletException {	
		super.init();
		
		System.out.println("Quartz initializing...");
				
		try {
			StdSchedulerFactory factory = (StdSchedulerFactory) 
		      getServletContext().getAttribute(QuartzInitializerServlet.QUARTZ_FACTORY_KEY);
			
			JobDetail jobDetail = new JobDetail("RetryJob", null, GeneraResumenRelojQuartzJob.class);
			Trigger trigger = TriggerUtils.makeDailyTrigger(1, 0);			
			trigger.setStartTime(new Date());
			trigger.setName("MyTrigger");
			Scheduler scheduler = factory.getScheduler();
			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			
			System.out.println("Quartz start succesful");
		} catch (SchedulerException e) {
			e.printStackTrace();
			System.out.println("Quartz error");
		}

	}
	
}
