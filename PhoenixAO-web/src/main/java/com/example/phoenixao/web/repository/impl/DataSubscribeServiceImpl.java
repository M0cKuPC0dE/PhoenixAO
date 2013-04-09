/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.repository.impl;

import com.example.phoenixao.web.model.ServiceSubscribe;
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
 */
@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class DataSubscribeServiceImpl implements DataSubscribeService {

    @PersistenceContext(unitName = "PhoenixAOPU")
    EntityManager em;

    @Override
    public List<ServiceSubscribe> getServiceSubscribeBySubscribeName(String subscribeName) {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int index = 0; index < stackTraceElements.length; index++) {
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@ "+stackTraceElements[index]);
        }
        List<ServiceSubscribe> resultList = em.createQuery("SELECT s FROM ServiceSubscribe s WHERE s.subscribeName =:subscribeName", ServiceSubscribe.class)
                .setParameter("subscribeName", subscribeName).getResultList();
        return resultList;
    }
}
