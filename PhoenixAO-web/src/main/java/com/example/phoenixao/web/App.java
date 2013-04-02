/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web;

import com.example.phoenixao.atmosphere.client.SubScribeManagerCallback;
import com.example.phoenixao.atmosphere.client.SubscribeManager;

/**
 *
 * @author wjirawong
 */
public class App {
    public static void main(String[] args) {
        //SubscribeManager ss = new SubscribeManager();
        SubscribeManager.subscribeService("hahazzzzzzzzzzzz", new SubScribeManagerCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        SubscribeManager.subscribeService("heheheheheh", new SubScribeManagerCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
}
