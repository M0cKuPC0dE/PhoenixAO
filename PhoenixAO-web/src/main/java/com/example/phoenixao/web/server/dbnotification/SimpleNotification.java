/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.dbnotification;

import com.example.phoenixao.web.framework.server.annotation.SubscribeService;
import com.example.phoenixao.web.framework.server.subscribe.SubscribeBroadcaster;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wjirawong
 */
@SubscribeService
@Service("simpleNotification")
public class SimpleNotification implements DatabaseChangeListener {
    
    @Autowired
    private SubscribeBroadcaster broadcaster;

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
        broadcaster.broadcast(dce.getTableChangeDescription()[0].getTableName());
    }
}
