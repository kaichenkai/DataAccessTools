package com.seemmo.gui.panel;

import com.seemmo.constants.BaseConstant;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 17:59
 * 分割配置面板 与 日志面板
 */
public class SplitPanel extends JSplitPane {
    //单例面板类
    private SplitPanel(){
        this.setBorder(null);
        this.setOrientation(JSplitPane.HORIZONTAL_SPLIT);//设置分割线方向 纵向分布
        this.setDividerSize(6);//设置分割线的宽度
        this.setDividerLocation(ScreenSize.getWidth()/2);//设置分割线位于中央
        this.setOneTouchExpandable(false);//设置那个杠杠上的两个黑点显示
        //this.setEnabled(true);//设置分割能不能移动，拖动左右面板
        this.setBackground(Color.WHITE);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static SplitPanel instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static SplitPanel createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new SplitPanel();
        }
        return instance;
    }
}
