package inventario.util;

import com.salmonllc.scheduler.ScheduleReachedEvent;
import com.salmonllc.scheduler.ScheduledObject;

public class MySchedule implements ScheduledObject {

	public void scheduleReached(ScheduleReachedEvent e) {
		System.out.println("Hola, soy MySchedule - aplicacion " + e.getApplicationName());
	}

}
