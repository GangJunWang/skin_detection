package com.zizhu.skindetection.common.utils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zizhu.skindetection.base.BaseApplication;
import com.zizhu.skindetection.common.model.RecordModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by 1 on 2016/11/29.
 */
public class StorageUtil {

    public static final String RECORDDATA = "recorddata";

    public static List<RecordModel> getRecordModes(){

        String json = (String) SharedPreferencesUtils.getParam(BaseApplication.getInstance(), RECORDDATA, "");
        if(TextUtils.isEmpty(json)){
           return new ArrayList<RecordModel>();
        }
        List<RecordModel> tempModels = JSONArray.parseArray(json, RecordModel.class);
        Collections.reverse(tempModels);
        List<RecordModel> recordModels = new ArrayList<>();
        for (RecordModel tempModel : tempModels){
            if(tempModel.recordModels.size() != 0){
                RecordModel recordModel = new RecordModel();
                recordModel.time = tempModel.time;
                recordModel.oil = tempModel.oil;
                recordModel.water = tempModel.water;
                Collections.reverse(tempModel.recordModels);
                tempModel.time = tempModel.recordModels.get(0).time;
                tempModel.recordModels.remove(0);
                tempModel.recordModels.add(recordModel);
                recordModels.add(tempModel);
            }else {
                recordModels.add(tempModel);
            }
            for(RecordModel recordModel : tempModel.recordModels){
                recordModels.add(recordModel);
            }
        }
        return recordModels;
    }

    public static void addRecordMode(RecordModel recordModel){
        List<RecordModel> recordModels = null;
        String json = (String) SharedPreferencesUtils.getParam(BaseApplication.getInstance(), RECORDDATA, "");
        if(TextUtils.isEmpty(json)){
            recordModels = new ArrayList<RecordModel>();
        }else{
            recordModels = JSONArray.parseArray(json, RecordModel.class);
        }
        if(recordModels.size() > 0){
            RecordModel rm = recordModels.get(recordModels.size() - 1);
            Date date = DateUtils.getDateFromString(rm.time);
            if(DateUtils.isToday(date)){
                recordModel.visible = false;
                rm.recordModels.add(recordModel);
            }else{
                recordModel.visible = true;
                recordModels.add(recordModel);
            }
        }else{
            recordModel.visible = true;
            recordModels.add(recordModel);
        }
        SharedPreferencesUtils.setParam(BaseApplication.getInstance(), RECORDDATA, JSON.toJSONString(recordModels));
    }

}
