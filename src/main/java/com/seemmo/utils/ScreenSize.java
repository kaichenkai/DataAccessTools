package com.seemmo.utils;

import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 15:01
 * 屏幕像素处理工具类
 */
public class ScreenSize {
    private static final Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * 获取当前屏幕的宽度(单位: dpi)
     */
    public static int getWidth(){
        return (int) ScreenSize.screensize.getWidth();
    }

    /**
     * 获取当前屏幕的高度(单位: dpi)
     */
    public static int getHeight(){
        return (int) ScreenSize.screensize.getHeight();
    }

    /**
     *
     */
}
