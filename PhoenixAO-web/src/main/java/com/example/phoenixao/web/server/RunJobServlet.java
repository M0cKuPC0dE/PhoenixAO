/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server;

import com.example.phoenixao.web.server.quartz.SimpleJob;
import com.example.phoenixao.web.server.framework.OracleNotification;
import com.example.phoenixao.web.server.framework.SubscribeBroadcaster;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
            
            DatabaseChangeListener dbChange = WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean("simpleNotification", DatabaseChangeListener.class);
            notification.registerDatabaseNotification(tables, dbChange);
        } catch (Exception ex) {
            Logger.getLogger(RunJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
