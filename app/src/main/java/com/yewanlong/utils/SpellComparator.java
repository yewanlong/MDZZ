package com.yewanlong.utils;

import java.util.Comparator;

/**
 * Created by Lkn on 2016/7/18.
 */
public class SpellComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        try {
            // 取得比较对象的汉字编码，并将其转换成字符串
            String s1 = new String(o1.toString().getBytes("GB2312"), "ISO-8859-1");
            String s2 = new String(o2.toString().getBytes("GB2312"), "ISO-8859-1");
            // 运用String类的 compareTo（）方法对两对象进行比较
            return s1.compareTo(s2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
