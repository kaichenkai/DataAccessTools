package com.seemmo.utils;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.util.Enumeration;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 21:15
 * 设置全局字体
 */
//public class GlobalFont {
//    /**
//     * 统一设置字体，父界面设置之后，所有由父界面进入的子界面都不需要再次设置字体
//     */
//    public static void initGlobalFont(Font font) {
//        FontUIResource fontRes = new FontUIResource(font);
//        for (Enumeration<Object> keys = UIManager.getDefaults().keys();
//             keys.hasMoreElements(); ) {
//            Object key = keys.nextElement();
//            Object value = UIManager.get(key);
//            if (value instanceof FontUIResource) {
//                UIManager.put(key, fontRes);
//            }
//        }
//    }
//}
