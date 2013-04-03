/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.server.framework;

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

/**
 *
 * @author wjirawong
 */
public abstract class SubscribeHandler extends AtmosphereGwtHandler {

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        Logger.getLogger("").setLevel(Level.INFO);
        Logger.getLogger("org.atmosphere.gwt").setLevel(Level.OFF);
        Logger.getLogger("org.jgroups.protocols").setLevel(Level.ALL);
        Logger.getLogger("").getHandlers()[0].setLevel(Level.ALL);
        logger.trace("Updated logging levels");
    }

    @Override
    public void onRequest(AtmosphereResource resource) throws IOException {
        System.out.println("onRequ - "+resource.getRequest().getParameter("id"));
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(DefaultBroadcaster.class,resource.getRequest().getParameter("id"),true);
        broadcaster.setID(resource.getRequest().getParameter("id"));
        broadcaster.addAtmosphereResource(resource);
        BroadcasterFactory.getDefault().add(broadcaster, resource.getRequest().getParameter("id"));
        super.onRequest(resource);
    }

    @Override
    public int doComet(GwtAtmosphereResource resource) throws ServletException, IOException {
        return NO_TIMEOUT;
    }  

    @Override
    public void cometTerminated(GwtAtmosphereResource cometResponse, boolean serverInitiated) {
        System.out.println("termi - "+cometResponse.getRequest().getParameter("id"));
        Collection<Broadcaster> list = BroadcasterFactory.getDefault().lookupAll();
        for(Broadcaster b : list){
            System.out.println("############## "+b.getID());
        }
        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup(cometResponse.getRequest().getParameter("id"));
        if(broadcaster != null){
            BroadcasterFactory.getDefault().remove(cometResponse.getRequest().getParameter("id"));
        }else{
            System.out.println("Error : simple job null");
        }
        
        Collection<Broadcaster> listed = BroadcasterFactory.getDefault().lookupAll();
        for(Broadcaster b : listed){
            System.out.println("@@@@@@@@@@@@@@ "+b.getID());
        }
        super.cometTerminated(cometResponse, serverInitiated);
    }
    
    
    
    
}
