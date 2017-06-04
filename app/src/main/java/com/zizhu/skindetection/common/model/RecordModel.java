package com.zizhu.skindetection.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2016/11/23.
 */
public class RecordModel {

    public String time;
    public int water;
    public int oil;
    public boolean visible;
    public List<RecordModel> recordModels = new ArrayList<RecordModel>();

    public RecordModel(){

    }

    public RecordModel(String time, int water, int oil){
        this.time = time;
        this.water = water;
        this.oil = oil;
    }

}
