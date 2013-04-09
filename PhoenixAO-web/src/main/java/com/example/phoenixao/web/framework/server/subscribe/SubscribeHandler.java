/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.framework.server.subscribe;

import com.example.phoenixao.web.implement.server.model.ServiceSubscribe;
import com.example.phoenixao.web.implement.server.repository.DataSubscribeService;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.cpr.DefaultBroadcaster;
import org.atmosphere.gwt.server.AtmosphereGwtHandler;
import org.atmosphere.gwt.server.GwtAtmosphereResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author wjirawong
 */
public class SubscribeHandler extends AtmosphereGwtHandler {

    private DataSubscribeService dataSubscribeService;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("org.atmosphere.gwt").setLevel(Level.OFF);
        Logger.getLogger("org.jgroups.protocols").setLevel(Level.ALL);
        Logger.getLogger("").getHandlers()[0].setLevel(Level.ALL);
        logger.trace("Updated logging levels");
        dataSubscribeService = WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean(DataSubscribeService.class);
    }

    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        String subscribeName = resource.getRequest().getParameter("id");
        List<ServiceSubscribe> serviceSubscribeBySubscribeName = dataSubscribeService.getServiceSubscribeBySubscribeName(subscribeName);
        for (ServiceSubscribe s : serviceSubscribeBySubscribeName) {
            System.out.println("Registered Service : "+s.getServiceName());
        }


        System.out.println("onRequ - " + subscribeName);
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, subscribeName, true);
        broadcaster.setID(subscribeName);
        broadcaster.addAtmosphereResource(resource);
        BroadcasterFactory.getDefault().add(broadcaster, subscribeName);
        super.onRequest(resource);
    }

    @Override
    public int doComet(GwtAtmosphereResource resource) throws ServletException, IOException {
        return NO_TIMEOUT;
    }

    @Override
    public void cometTerminated(GwtAtmosphereResource cometResponse, boolean serverInitiated) {
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(cometResponse.getRequest().getParameter("id"));
        if (broadcaster != null) {
            BroadcasterFactory.getDefault().remove(cometResponse.getRequest().getParameter("id"));
        } else {
            System.out.println("Error : simple job null");
        }
        super.cometTerminated(cometResponse, serverInitiated);
    }
}
