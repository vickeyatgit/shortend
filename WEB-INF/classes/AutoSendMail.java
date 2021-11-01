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
        scheduler = Executors.newSingleThreadScheduledExecutor();
    // scheduler.scheduleAtFixedRate(new DailyJob(), 0, 1, TimeUnit.DAYS);
        // scheduler.scheduleAtFixedRate(new HourlyJob(), 0, 1, TimeUnit.HOURS);
    //scheduler.scheduleAtFixedRate(new MinJob(), 0, 1, TimeUnit.MINUTES);
    //delay
    //period
    // scheduler.scheduleAtFixedRate(new HourlyJob("hello"), 0, 5, TimeUnit.SECONDS);
    // for(int i=0;i<3;i++){
    //     scheduler.scheduleAtFixedRate(new HourlyJob(i+1), 0, (5+i), TimeUnit.SECONDS);
    // }

    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    public class HourlyJob implements Runnable {
    String fromEmail,toEmail,subject,body,attachCheck,attachment;
    int i=0;
    public HourlyJob(int he){
        System.out.println(he);
        i=he;
    }
    @Override
    public void run() {
        // Do your hourly job here.
        System.out.println("Job trigged by scheduler "+i);
    }
}

}

