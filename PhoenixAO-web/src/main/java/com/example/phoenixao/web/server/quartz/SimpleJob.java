/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.quartz;

import com.example.phoenixao.web.server.framework.SubscribeBroadcaster;
import java.util.Date;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author wjirawong
 */
public class SimpleJob implements Job {

    private SubscribeBroadcaster broadcaster = new SubscribeBroadcaster("sqartzJobService");

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        broadcaster.broadcast(new Date().toString());
    }
}
