/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.web.client.framework;

/**
 *
 * @author wjirawong
 */
public abstract class SubScribeManagerCallback<T> {
    
    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable caught);
}
