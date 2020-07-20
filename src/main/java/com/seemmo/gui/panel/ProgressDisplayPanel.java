package com.seemmo.gui.panel;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.commons.*;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 17:59
 * 配置内容
 */
public class ProgressDisplayPanel extends JPanel {
    //单例面板类
    private ProgressDisplayPanel(){
        this.setLayout(null);//设为空布局后,才能设置子标签的大小和位置
        this.setBorder(null);//去掉边框
//        this.setBounds(BaseConstant.CONST20, BaseConstant.CONST710,
//                ScreenSize.getWidth() / 2 - BaseConstant.CONST30,
//                BaseConstant.CONST260);//设置坐标, 尺寸
        this.setBounds(ScreenSize.widthRatio(0.01), ScreenSize.heightRatio(0.73), ScreenSize.widthRatio(0.485), ScreenSize.heightRatio(0.17));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createTitledBorder(null, " 开始 ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, FontClass.boldFont20, ColorClass.color_18a5d6));
        this.addComponents();
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static ProgressDisplayPanel instance = new ProgressDisplayPanel();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
//    public static ProgressDisplayPanel createInstance(){
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new ProgressDisplayPanel();
//        }
//        return instance;
//    }

    public void addComponents(){
        //测试按钮
        this.add(AccessTestButton.instance);

        //开始按钮
        this.add(StartButton.instance);

        //接入总量
        AccessTotal accessTotal = AccessTotal.createInstance();
        this.add(accessTotal.accessTotalLabel);
        this.add(accessTotal.accessTotalText);

        //接入成功
        AccessStats accessNum = AccessStats.createInstance();
        this.add(accessNum.accessSuccessNumLabel);
        this.add(accessNum.accessSuccessNumText);

        //接入进度条
        AccessProgress accessProgress = AccessProgress.createInstance();
        this.add(accessProgress.accessProgressLabel);
        this.add(accessProgress.accessProgressText);
    }
}
