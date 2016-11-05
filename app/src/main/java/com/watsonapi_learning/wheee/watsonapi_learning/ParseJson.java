package com.watsonapi_learning.wheee.watsonapi_learning;

import android.util.Log;

//import com.alibaba.fastjson.*;

public class ParseJson {
    private String JsonString;

    //
    ParseJson(String jsonstring) {

        this.JsonString = jsonstring;
        Log.i("#####", "" + "ParseJson构造函数 " + this.JsonString);
    }

    // 解析 Json
//    public Document_tone Parse() {
//        JSONObject jsonObject = null;
//        // 当前最外层类名： "document_tone"
//
//        // 执行下列语句后 ：当前类名： "tone_categories"
//        jsonObject = JSONObject.fromObject(this.JsonString);
//        // jsonObject = JSONObject.parseObject(this.JsonString);
//        String STRING_document_tone = jsonObject.get("document_tone").toString();
//        System.err.println(STRING_document_tone);
//
//        // javabean
//        jsonObject = JSONObject.fromObject(STRING_document_tone);
//        Document_tone dt = new Document_tone();
//
//        Map<String, Class> classmap = new HashMap<String, Class>();
//        classmap.put("tone_categories", Tone_categories.class);
//        classmap.put("tones", Tones.class);
//        Log.i("####", "ParseJson类 BUG模块开始" + this.JsonString);
//
//        //  dt = (Document_tone) JSONObject.toBean(jsonObject,Document_tone.class);
//        dt = (Document_tone) JSONObject.toBean(jsonObject, Document_tone.class, classmap);
//
//
//         Log.i("####", "ParseJson类JSONObject.toBean(jsonObject, Document_tone.class, classmap) 通过！！\n" + dt.getTone_categories().get(0).toString());
//
//
//        return dt;
//
//    }// Map<Object,Object>_ParseJson_end

}// class_ParseJson_end
