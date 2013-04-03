/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server;

import com.example.phoenixao.web.server.framework.SubscribeHandler;
import org.atmosphere.config.service.AtmosphereHandlerService;

/**
 *
 * @author wjirawong
 */
@AtmosphereHandlerService(path = "/subscribe")
public class SimpleSubscribeHandler extends SubscribeHandler{
}
