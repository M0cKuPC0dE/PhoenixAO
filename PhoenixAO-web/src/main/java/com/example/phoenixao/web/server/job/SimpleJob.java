/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.job;

import java.util.Date;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author wjirawong
 */
public class SimpleJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class,"sqartzJobService");
        if(broadcaster != null){
            broadcaster.broadcast(new Date().toString());
        }else{
            System.out.println("Error : simple job null");
        }
    }
    
}
