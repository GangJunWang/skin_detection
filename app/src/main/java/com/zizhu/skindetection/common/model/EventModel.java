package com.zizhu.skindetection.common.model;

/**
 * Created by 1 on 2016/11/24.
 */
public class EventModel {
    public String type;
    public Object eventBus;

    public EventModel(String type, Object eventBus){
        this.type = type;
        this.eventBus = eventBus;
    }

}
