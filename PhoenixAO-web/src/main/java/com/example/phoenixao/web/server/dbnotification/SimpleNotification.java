/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.dbnotification;

import com.example.phoenixao.web.server.framework.SubscribeBroadcaster;
import oracle.jdbc.dcn.DatabaseChangeEvent;
import oracle.jdbc.dcn.DatabaseChangeListener;
import org.springframework.stereotype.Service;

/**
 *
 * @author wjirawong
 */
@Service("simpleNotification")
public class SimpleNotification implements DatabaseChangeListener {

    final SubscribeBroadcaster broadcaster = new SubscribeBroadcaster("oracleNotificationService");

    @Override
    public void onDatabaseChangeNotification(DatabaseChangeEvent dce) {
        broadcaster.broadcast(dce.getTableChangeDescription()[0].getTableName());
    }
}
