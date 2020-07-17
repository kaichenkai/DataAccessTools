package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ContentLengthControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 16:09
 */
public class TimeFormat {//单例
    public JLabel timeFormatLabel;
    public JComboBox<String> timeFormatText;
    public JTextField timeFormatCustomText;
    //是否修改过timeFormatCustomText
    public boolean fixFlag = false;

    //单例
    private TimeFormat(){
        //timeFormatLabel
        timeFormatLabel = new JLabel("时间格式");
        timeFormatLabel.setFont(FontClass.font14);
        timeFormatLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST250, BaseConstant.CONST80, BaseConstant.CONST30);
        //timeFormatText
        timeFormatText = new JComboBox<String>();
        timeFormatText.addItem("yyyyMMddHHmmss");
        timeFormatText.addItem("yyyy_MM_dd HH:mm:ss");
        timeFormatText.addItem("UNIX时间戳(毫秒)");
        timeFormatText.setFont(FontClass.font14);
        timeFormatText.setFocusable(false);//去掉按钮文字周围的焦点框
        timeFormatText.setBackground(Color.WHITE);
        timeFormatText.setBounds(BaseConstant.CONST110, BaseConstant.CONST250, BaseConstant.CONST160, BaseConstant.CONST30);
        //timeFormatCustomText
        timeFormatCustomText = new JTextField();
        timeFormatCustomText.setFont(FontClass.font14);
        timeFormatCustomText.setDocument(new ContentLengthControl(BaseConstant.CONST50));//限制用户输入长度
        timeFormatCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
        timeFormatCustomText.setText(BusinessConstant.CUSTOM_TIME_FORMAT);
        timeFormatCustomText.setBounds(BaseConstant.CONST300, BaseConstant.CONST250, BaseConstant.CONST145, BaseConstant.CONST30);
        timeFormatCustomText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {//获取焦点时
                timeFormatCustomText.setForeground(Color.BLACK);//设置字体颜色
                if (!fixFlag) {
                    timeFormatCustomText.setText("");
                }
                fixFlag = true;
            }

            @Override
            public void focusLost(FocusEvent e) {//失去焦点时
//                timeFormatCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
//                timeFormatCustomText.setText("自定义时间格式");
            }
        });

    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static TimeFormat instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static TimeFormat createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new TimeFormat();
        }
        return instance;
    }
}
