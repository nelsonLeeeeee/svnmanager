package com.wellhope.svnmanager.util;

import java.util.HashMap;
import java.util.Map;

public class Result {

    public static String OK = "OK";

    public static String ERROR = "ERROR";

    public String status;

    public Map<String,Object> resultMap = new HashMap<String,Object>();

    public Result() {
    }

    public Result(String status) {
        this.status = status;
    }

    public Result(String status, Map<String, Object> resultMap) {
        this.status = status;
        this.resultMap = resultMap;
    }


}
