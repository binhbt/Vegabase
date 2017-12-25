package com.vn.fa.base.holder;

import com.vn.fa.adapter.multipleviewtype.VegaDataBinder;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by binhbt on 12/7/2016.
 */
public abstract class VegaBinderView<T> extends VegaDataBinder<T> {
    public VegaBinderView(T data){
        super(data);
    }
    protected void sendEvent(Object event){
        EventBus.getDefault().post(event);
    }


}
