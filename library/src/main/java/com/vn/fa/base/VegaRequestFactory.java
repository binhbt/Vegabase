package com.vn.fa.base;

import com.vn.fa.base.net.FaRequest;

/**
 * Created by leobui on 11/10/2017.
 */

public abstract class VegaRequestFactory {
    public VegaRequestFactory(){
        init();
    }
    public abstract void init();
    //public abstract FaRequest getRequest(T vegaRequestType);
    public FaRequest getRequest(String className){
        try {
            Class<?> clazz = Class.forName(className);
            return (FaRequest)clazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
