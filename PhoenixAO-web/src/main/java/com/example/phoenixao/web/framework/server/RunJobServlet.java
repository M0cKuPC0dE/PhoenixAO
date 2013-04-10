/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.framework.server;

import com.example.phoenixao.web.framework.server.dbnotification.OracleNotification;
import com.example.phoenixao.web.model.ServiceSubscribe;
import com.example.phoenixao.web.model.ServiceSubscribeType;
import com.example.phoenixao.web.repository.DataSubscribeService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import oracle.jdbc.dcn.DatabaseChangeListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author wjirawong
 */
public class RunJobServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        //Oracle Notification
        try {
            OracleNotification notification = new OracleNotification();
            DataSubscribeService dataSubscribeService = WebApplicationContextUtils
                    .getWebApplicationContext(getServletContext())
                    .getBean(DataSubscribeService.class);
            
            List<ServiceSubscribe> ServiceSubscribes = dataSubscribeService
                    .getServiceSubscribeByType(ServiceSubscribeType.ORACLE_NOTIFICATION);
            
            for(ServiceSubscribe ServiceSubscribe : ServiceSubscribes){
                DatabaseChangeListener dbChange = WebApplicationContextUtils.getWebApplicationContext(getServletContext()).getBean(ServiceSubscribe.getServiceName(), DatabaseChangeListener.class);
                notification.registerDatabaseNotification(ServiceSubscribe.getTables(), dbChange);
            }
            
            
        } catch (Exception ex) {
            Logger.getLogger(RunJobServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
