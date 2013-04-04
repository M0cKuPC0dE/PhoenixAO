/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.framework;

import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;

/**
 *
 * @author wjirawong
 */
public class SubscribeBroadcaster {

    private String service;

    public SubscribeBroadcaster(String service) {
        this.service = service;
    }

    public void broadcast(Object message) {
        try {
            Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, service);
            if (broadcaster != null) {
                broadcaster.broadcast(message);
            } else {
                System.out.println("not found service : " + service);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}
