/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.cronjob;

import com.example.phoenixao.web.framework.server.annotation.SubscribeService;
import com.example.phoenixao.web.framework.server.subscribe.SubscribeBroadcaster;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author wjirawong
 */
@SubscribeService
@Component("cronJobService")
public class CronJobTask {
    @Autowired
    private SubscribeBroadcaster broadcaster;
    
    @Scheduled(cron = "*/5 * * * * ?")
    public void doTask(){
        broadcaster.broadcast(new Date().toString());
    }
}
