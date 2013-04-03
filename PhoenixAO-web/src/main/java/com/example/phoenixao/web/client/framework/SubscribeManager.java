/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.client.framework;

import com.google.gwt.core.client.GWT;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.atmosphere.gwt.client.AtmosphereClient;
import org.atmosphere.gwt.client.AtmosphereListener;

/**
 *
 * @author wjirawong
 */
public class SubscribeManager {

    private Map<String, AtmosphereClient> clientService;
    private SubScribeManagerCallback callback;
    private String serviceName;

    public SubscribeManager() {
        clientService = new HashMap<String, AtmosphereClient>();
    }

    /**
     *
     * @param serviceName
     * @param callback
     */
    public void subscribe(String serviceName, SubScribeManagerCallback callback) {
        this.serviceName = serviceName;
        this.callback = callback;
        initSubscribeService();
    }

    public void unSubscribe(String serviceName) {
        AtmosphereClient client = clientService.get(serviceName);
        client.stop();
    }

    public void unSubscribeAll() {
        for (Map.Entry pairs : clientService.entrySet()) {
            AtmosphereClient client = (AtmosphereClient) pairs.getValue();
            System.out.println(client.getUrl());
            client.stop();
        }
    }

    private void initSubscribeService() {
        SubscribeListener subscribeListener = new SubscribeListener(callback);
        AtmosphereClient client = new AtmosphereClient(GWT.getHostPageBaseURL() + "subscribe?id=" + serviceName, subscribeListener);
        clientService.put(serviceName, client);
        client.start();
    }

    private class SubscribeListener implements AtmosphereListener {

        private SubScribeManagerCallback callback;

        public SubscribeListener(SubScribeManagerCallback callback) {
            this.callback = callback;
        }

        @Override
        public void onConnected(int heartbeat, String connectionUUID) {
            callback.onSuccess("Connected");
        }

        @Override
        public void onBeforeDisconnected() {
        }

        @Override
        public void onDisconnected() {
        }

        @Override
        public void onError(Throwable exception, boolean connected) {
            callback.onFailure(exception);
        }

        @Override
        public void onHeartbeat() {
        }

        @Override
        public void onRefresh() {
        }

        @Override
        public void onAfterRefresh(String connectionUUID) {
        }

        @Override
        public void onMessage(List<?> messages) {
            for (Object message : messages) {
                callback.onSuccess(message);
            }
        }
    }
}
