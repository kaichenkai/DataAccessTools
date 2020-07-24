package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.utils.FontClass;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 17:45
 */
public class ProcessNum {
    public JLabel processNumLabel;
    public JComboBox<Integer> processNumText;

    //单例
    private ProcessNum(){
        //processNumLabel
        processNumLabel = new JLabel(BusinessConstant.THREAD_NUM);
        processNumLabel.setFont(FontClass.font14);
        processNumLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST300, BaseConstant.CONST80, BaseConstant.CONST30);
        //processNumText
        processNumText = new JComboBox<>();
        processNumText.addItem(1);
        processNumText.addItem(2);
        processNumText.addItem(3);
        processNumText.addItem(4);
        processNumText.addItem(5);
        processNumText.addItem(6);
        processNumText.addItem(7);
        processNumText.addItem(8);
        processNumText.setFont(FontClass.font14);
        processNumText.setFocusable(false);//去掉按钮文字周围的焦点框
        processNumText.setBackground(Color.WHITE);
        processNumText.setBounds(BaseConstant.CONST110, BaseConstant.CONST300, BaseConstant.CONST50, BaseConstant.CONST30);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static ProcessNum instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static ProcessNum createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new ProcessNum();
        }
        return instance;
    }
}
