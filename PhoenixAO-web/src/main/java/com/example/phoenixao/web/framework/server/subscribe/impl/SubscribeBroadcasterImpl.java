/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.framework.server.subscribe.impl;

import com.example.phoenixao.web.framework.server.annotation.SubscribeService;
import com.example.phoenixao.web.framework.server.subscribe.SubscribeBroadcaster;
import com.example.phoenixao.web.repository.DataSubscribeService;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.atmosphere.cpr.MetaBroadcaster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author wjirawong
 */
@Service("subscribeBroadcaster")
public class SubscribeBroadcasterImpl implements SubscribeBroadcaster {

    @Autowired
    private DataSubscribeService dataSubscribeService;

    @Override
    public void broadcast(Object message) {
        try {
            String subscribeServiceName = getSubscribeService();
            MetaBroadcaster.getDefault().broadcastTo("/subscribe/"+subscribeServiceName, message);
//            Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, subscribeServiceName);
//            if (broadcaster != null) {
//                broadcaster.broadcast(message);
//            } else {
//                System.out.println("not found broadcaster");
//            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String getSubscribeService() throws ClassNotFoundException {
        String subscribeServiceName = null;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int index = 0; index < stackTraceElements.length; index++) {
            Class<?> clazz = Class.forName(stackTraceElements[index].getClassName());
            if (clazz.isAnnotationPresent(SubscribeService.class)) {
                String serviceName = null;
                Service serviceAnnotation = clazz.getAnnotation(Service.class);
                Component componentAnnotation = clazz.getAnnotation(Component.class);

                if (serviceAnnotation != null) {
                    serviceName = serviceAnnotation.value();
                } else if (componentAnnotation != null) {
                    serviceName = componentAnnotation.value();
                }

                if (serviceName != null) {
                    subscribeServiceName = dataSubscribeService.getServiceSubscribeNameByServiceName(serviceName);
                }
                break;
            }
        }
        return subscribeServiceName;
    }
}
