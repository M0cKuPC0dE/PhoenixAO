/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.atmosphere.client;

import java.util.List;
import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereListener;

/**
 *
 * @author wjirawong
 */
public class SubscribeManager {
    private AtmosphereClient client;
    private SubScribeManagerCallback callback;
    
    private String serviceName;
    
    private SubscribeManager(String serviceName,SubScribeManagerCallback callback){
        this.serviceName = serviceName;
        this.callback = callback;
    }
    
    /**
     *
     * @param serviceName
     * @param callback
     */
    public static void subscribeService(String serviceName,SubScribeManagerCallback callback){
        SubscribeManager subscribeManager = new SubscribeManager(serviceName, callback);
        subscribeManager.callback.onSuccess(serviceName);
        
        System.out.println(subscribeManager.callback.getTypeParameterClass().getName());
    }
    
    private void init(){
        SubscribeListener subscribeListener = new SubscribeListener();
    }
    
    private class SubscribeListener implements AtmosphereListener{

        @Override
        public void onConnected(int heartbeat, String connectionUUID) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onBeforeDisconnected() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onDisconnected() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onError(Throwable exception, boolean connected) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onHeartbeat() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onRefresh() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onAfterRefresh(String connectionUUID) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void onMessage(List<?> messages) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
}
