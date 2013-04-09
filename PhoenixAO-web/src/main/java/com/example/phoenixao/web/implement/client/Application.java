/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.implement.client;

import com.example.phoenixao.web.framework.client.SubScribeManagerCallback;
import com.example.phoenixao.web.framework.client.SubscribeManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 *
 * @author wjirawong
 */
public class Application implements EntryPoint {

    SubscribeManager subscribeManager;
    private FlexTable flexTable;
    private Button subscribeSquartzButton;
    private Button unSubscribeSquartzButton;
    private Button subscribeOracleButton;
    private Button unSubscribeOracleButton;
    private Button unSubscribeAll;
    private Label squartzLabel;
    private Label oracleLabel;

    @Override
    public void onModuleLoad() {
        InitialWidget();
        squartzSubscribe();
        oracleSubscribe();
        unSubscribeAll();
    }

    private void InitialWidget() {
        subscribeManager = new SubscribeManager();
        flexTable = new FlexTable();

        subscribeSquartzButton = new Button("Subscribe Quartz");
        unSubscribeSquartzButton = new Button("UnSubscribe Quartz");

        subscribeOracleButton = new Button("Subscribe OracleNotification");
        unSubscribeOracleButton = new Button("UnSubscribe OracleNotification");

        unSubscribeAll = new Button("Unsubscribe All");

        squartzLabel = new Label("Quartz worker");
        oracleLabel = new Label("Oracle worker");

        flexTable.setWidget(0, 0, squartzLabel);
        flexTable.setWidget(0, 1, subscribeSquartzButton);
        flexTable.setWidget(0, 2, unSubscribeSquartzButton);
        flexTable.setWidget(1, 0, oracleLabel);
        flexTable.setWidget(1, 1, subscribeOracleButton);
        flexTable.setWidget(1, 2, unSubscribeOracleButton);
        flexTable.setWidget(2, 0, unSubscribeAll);

        unSubscribeSquartzButton.setEnabled(false);
        unSubscribeOracleButton.setEnabled(false);
        RootPanel.get().add(flexTable);

    }

    private void squartzSubscribe() {
        subscribeSquartzButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                subscribeSquartzButton.setEnabled(false);
                unSubscribeSquartzButton.setEnabled(true);
                subscribeManager.subscribe("sqartzJobService", new SubScribeManagerCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        squartzLabel.setText(result.toString());
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        System.out.println("error");
                    }
                });
            }
        });

        unSubscribeSquartzButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                subscribeSquartzButton.setEnabled(true);
                unSubscribeSquartzButton.setEnabled(false);
                subscribeManager.unSubscribe("sqartzJobService");
            }
        });
    }

    private void oracleSubscribe() {
        subscribeOracleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                subscribeOracleButton.setEnabled(false);
                unSubscribeOracleButton.setEnabled(true);
                subscribeManager.subscribe("oracleNotificationService", new SubScribeManagerCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        oracleLabel.setText(result);
                    }

                    @Override
                    public void onFailure(Throwable caught) {
                        System.out.println("error");
                    }
                });
            }
        });

        unSubscribeOracleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                subscribeOracleButton.setEnabled(true);
                unSubscribeOracleButton.setEnabled(false);
                subscribeManager.unSubscribe("oracleNotificationService");
            }
        });
    }

    private void unSubscribeAll() {
        unSubscribeAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                subscribeSquartzButton.setEnabled(true);
                unSubscribeSquartzButton.setEnabled(false);
                subscribeOracleButton.setEnabled(true);
                unSubscribeOracleButton.setEnabled(false);
                subscribeManager.unSubscribeAll();
            }
        });
    }
}
