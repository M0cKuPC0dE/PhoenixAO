/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.phoenixao.atmosphere.client;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 *
 * @author wjirawong
 */
public abstract class SubScribeManagerCallback<T> {
    
    public abstract void onSuccess(T result);

    public abstract void onFailure(Throwable caught);
    
    public final Class<T> getTypeParameterClass(){
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        return (Class<T>) paramType.getActualTypeArguments()[0];
    }
}
