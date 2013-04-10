/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.client;

import com.example.phoenixao.web.framework.client.SubScribeManagerCallback;
import com.example.phoenixao.web.framework.client.SubscribeManager;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

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
    private TextBox userTextBox;
    private HTML html;

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

        userTextBox = new TextBox();
        html = new HTML();

        subscribeSquartzButton = new Button("Subscribe Cron");
        unSubscribeSquartzButton = new Button("UnSubscribe Cron");

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
        flexTable.setText(2, 0, "User");
        flexTable.setWidget(2, 1, userTextBox);
        flexTable.setWidget(3, 0, unSubscribeAll);

        unSubscribeSquartzButton.setEnabled(false);
        unSubscribeOracleButton.setEnabled(false);
        RootPanel.get().add(flexTable);
        RootPanel.get().add(html);
        
        
        subscribeManager.subscribe("listUserSubscribe", new SubScribeManagerCallback<String>() {

            @Override
            public void onSuccess(String result) {
                html.setHTML(result);
            }

            @Override
            public void onFailure(Throwable caught) {
                caught.printStackTrace();
            }
        });

    }

    private void squartzSubscribe() {
        subscribeSquartzButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                subscribeSquartzButton.setEnabled(false);
                unSubscribeSquartzButton.setEnabled(true);
                subscribeManager.subscribe("cronSubscribe&username="+userTextBox.getValue(), new SubScribeManagerCallback<String>() {
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
                subscribeManager.unSubscribe("cronSubscribe");
            }
        });
    }

    private void oracleSubscribe() {
        subscribeOracleButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                subscribeOracleButton.setEnabled(false);
                unSubscribeOracleButton.setEnabled(true);
                subscribeManager.subscribe("oracleNotificationSubscribe&username="+userTextBox.getValue(), new SubScribeManagerCallback<String>() {
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
                subscribeManager.unSubscribe("oracleNotificationSubscribe");
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
