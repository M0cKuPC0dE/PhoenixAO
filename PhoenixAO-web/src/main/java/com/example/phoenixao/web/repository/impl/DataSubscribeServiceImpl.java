/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.repository.impl;

import com.example.phoenixao.web.model.ServiceSubscribe;
import com.example.phoenixao.web.model.ServiceSubscribeType;
import com.example.phoenixao.web.repository.DataSubscribeService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wjirawong
 * @serial :D make it serial kick it ass to serial.
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class DataSubscribeServiceImpl implements DataSubscribeService {

    @PersistenceContext(unitName = "PhoenixAOPU")
    EntityManager em;

    @Override
    public List<ServiceSubscribe> getServiceSubscribeBySubscribeName(String subscribeName) {
        List<ServiceSubscribe> resultList = em.createQuery("SELECT s FROM ServiceSubscribe s WHERE s.subscribeName =:subscribeName", ServiceSubscribe.class)
                .setParameter("subscribeName", subscribeName).getResultList();
        return resultList;
    }
    
    @Override
    public List<ServiceSubscribe> getServiceSubscribeByType(ServiceSubscribeType type){
        List resultList = em.createQuery("SELECT s FROM ServiceSubscribe s WHERE s.type=:type").setParameter("type", type).getResultList();
        return resultList;
    }
    
    @Override
    public String getServiceSubscribeNameByServiceName(String serviceName){
        ServiceSubscribe singleResult = (ServiceSubscribe) em.createQuery("SELECT s FROM ServiceSubscribe s WHERE s.serviceName=:serviceName")
                .setParameter("serviceName", serviceName)
                .getSingleResult();
        return singleResult.getSubscribeName();
    }
}
