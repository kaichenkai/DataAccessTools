package com.seemmo.gui.panel.logPanel;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/8/2020 17:39
 */
public class LogScrollPanel extends JScrollPane {
    //单例面板类
    private LogScrollPanel(){
        this.setBackground(Color.WHITE);//背景色
        this.setForeground(Color.ORANGE);//设置字体颜色
        this.setBorder(BorderFactory.createTitledBorder(null, " 日志区 ", TitledBorder.LEFT, TitledBorder.ABOVE_TOP, FontClass.boldFont16, ColorClass.color_18a5d6));
        this.setBounds(ScreenSize.getWidth() / 2, BaseConstant.CONST0, ScreenSize.getWidth() / 2, ScreenSize.getHeight() - BaseConstant.CONST45);
        this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);//水平滚动条
        this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);//垂直滚动条
        //添加第二层 JPane
        this.setViewportView(LogPanel.instance);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static LogScrollPanel instance = new LogScrollPanel();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
//    public static LogScrollPanel createInstance(){
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new LogScrollPanel();
//        }
//        return instance;
//    }
}
