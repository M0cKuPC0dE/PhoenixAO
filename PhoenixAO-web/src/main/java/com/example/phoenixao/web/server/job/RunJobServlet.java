/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.job;

import com.example.phoenixao.web.server.framework.OracleNotification;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author wjirawong
 */
public class RunJobServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        //Squartz Job
        JobDetail jobDetail = JobBuilder
                .newJob(SimpleJob.class)
                .withIdentity("SimpleJob")
                .build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity("SimpleJob")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/1 * * * * ?"))
                .build();

        Scheduler scheduler;
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(RunJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        }


        //Oracle Notification
        try {
            OracleNotification notification = new OracleNotification();
            List<String> tables = new ArrayList<String>();
            tables.add("AIR_TEST");
            tables.add("AIR_TEST2");
            
            notification.registerDatabaseNotification(tables, new DatabaseChangeListener() {
                @Override
                public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
                    try {
                        System.out.println("Oracle Change");
                        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class,"oracleNotificationService");
                        broadcaster.broadcast(dce.getTableChangeDescription()[0].getTableName());
                    } catch (Exception ex) {
                        System.out.println("Ora - Error");
                    }

                }
            });
        } catch (Exception ex) {
            Logger.getLogger(RunJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
