package com.ygst.cenggeche.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/9/6.
 */

public class MapUtils {

    public static Map newMap() {
        return new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 升序排序
                        return obj1.compareTo(obj2);
                    }
                });
    }
}
