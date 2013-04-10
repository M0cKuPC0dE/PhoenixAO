/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.cronjob;

import com.example.phoenixao.web.framework.server.annotation.SubscribeService;
import com.example.phoenixao.web.framework.server.subscribe.SubscribeBroadcaster;
import java.util.Collection;
import java.util.Date;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author wjirawong
 */
@SubscribeService
@Component("listUserService")
public class ListUserService {
    @Autowired
    private SubscribeBroadcaster broadcaster;
    
    @Scheduled(cron = "*/5 * * * * ?")
    public void doTask(){
        StringBuilder builder = new StringBuilder();
        Collection<Broadcaster> lookupAll = BroadcasterFactory.getDefault().lookupAll();
        for(Broadcaster b : lookupAll){
            builder.append(b.getID());
            builder.append("<br>");
        }
        broadcaster.broadcast(builder.toString());
    }
}
