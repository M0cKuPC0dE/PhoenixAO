/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.framework.server.subscribe;

import com.example.phoenixao.web.model.ServiceSubscribe;
import com.example.phoenixao.web.repository.DataSubscribeService;
import java.io.IOException;
import java.util.Collection;
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
        String broadCaterId = getBroadCasterId(resource);
        List<ServiceSubscribe> serviceSubscribeBySubscribeName = dataSubscribeService.getServiceSubscribeBySubscribeName(resource.getRequest().getParameter("id"));
        for (ServiceSubscribe s : serviceSubscribeBySubscribeName) {
            System.out.println("Registered Service : " + s.getServiceName() +" by subscribe :"+broadCaterId);
        }

        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class, broadCaterId, true);
        broadcaster.addAtmosphereResource(resource);
        BroadcasterFactory.getDefault().add(broadcaster, broadCaterId);
        
        Collection<Broadcaster> lookupAll = BroadcasterFactory.getDefault().lookupAll();
        for(Broadcaster b : lookupAll){
            System.out.println(b.getID());
        }
        
        super.onRequest(resource);
    }

    @Override
    public int doComet(GwtAtmosphereResource resource) throws ServletException, IOException {
        return NO_TIMEOUT;
    }

    @Override
    public void cometTerminated(GwtAtmosphereResource cometResponse, boolean serverInitiated) {
        String broadCaterId = getBroadCasterId(cometResponse.getAtmosphereResource());
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(broadCaterId);
        if (broadcaster != null) {
            BroadcasterFactory.getDefault().remove(broadCaterId);
        } else {
            System.out.println("Error : simple job null");
        }
        super.cometTerminated(cometResponse, serverInitiated);
    }

    private String getBroadCasterId(AtmosphereResource resource) {
        String subscribeName = resource.getRequest().getParameter("id");
        String userName = resource.getRequest().getParameter("username");
        String broadCaterId;
        if (userName == null || "".equals(userName)) {
            broadCaterId = "/" + subscribeName;
        } else {
            broadCaterId = "/" + subscribeName + "/" + userName;
        }
        return broadCaterId;

    }
}
