package com.seemmo.gui.panel.logPanel;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/8/2020 17:39
 * 1. JPanel (第二层)
 */
public class LogPanel extends JPanel {
    //单例面板类
    private LogPanel(){
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 1));//设置为其内容实际的高度
        this.setBackground(Color.BLACK);
        //this.setBounds(ScreenSize.getWidth() / 2, BaseConstant.CONST0, ScreenSize.getWidth() / 2, ScreenSize.getHeight() - BaseConstant.CONST45);
        //添加第三层 JTextPanel
        this.add(LogTextPane.instance);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static LogPanel instance = new LogPanel();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
//    public static LogPanel createInstance(){
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new LogPanel();
//        }
//        return instance;
//    }
}
