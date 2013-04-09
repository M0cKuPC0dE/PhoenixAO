/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.repository;

import com.example.phoenixao.web.model.ServiceSubscribe;
import java.util.List;

/**
 *
 * @author wjirawong
 */
public interface DataSubscribeService {
    public List<ServiceSubscribe> getServiceSubscribeBySubscribeName(String subscribeName);
}
