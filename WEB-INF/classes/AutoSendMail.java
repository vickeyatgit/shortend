import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;
import java.util.concurrent.*;
import java.util.*;



public class AutoSendMail implements ServletContextListener {
    //scheduler variable to send
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // init
        scheduler = Executors.newSingleThreadScheduledExecutor();
    // scheduler.scheduleAtFixedRate(new DailyJob(), 0, 1, TimeUnit.DAYS);
        // scheduler.scheduleAtFixedRate(new HourlyJob(), 0, 1, TimeUnit.HOURS);
    //scheduler.scheduleAtFixedRate(new MinJob(), 0, 1, TimeUnit.MINUTES);
    //delay
    //period
    // scheduler.scheduleAtFixedRate(new HourlyJob("hello"), 0, 5, TimeUnit.SECONDS);
    int start=0;
    for(int i=10 ;i>1;i--){
        scheduler.scheduleAtFixedRate(new HourlyJob(start+1), 0, (5+i), TimeUnit.SECONDS);
        start = start+1;
    }

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
        // destroy
    }

    public class HourlyJob implements Runnable {
    String fromEmail,toEmail,subject,body,attachCheck,attachment;
    int i=0;
    public HourlyJob(int he){
        System.out.println(he);
        i=he;
    }
    // in runnable
    @Override
    public void run() {
        System.out.println("Job trigged by scheduler "+i);
    }
}

}

