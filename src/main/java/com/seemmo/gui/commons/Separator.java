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
public class Separator {//单例
    public JLabel separatorLabel;
    public JComboBox<String> separatorText;
    public JTextField separatorCustomText;
    //是否修改过separatorCustomText
    public boolean fixFlag = false;

    //单例
    private Separator(){
        //separatorLabel
        separatorLabel = new JLabel(BusinessConstant.SEPARATOR);
        separatorLabel.setFont(FontClass.font14);
        separatorLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST200, BaseConstant.CONST80, BaseConstant.CONST30);
        //separatorText
        separatorText = new JComboBox<>();
        separatorText.addItem(BusinessConstant.UNDERLINE);
        separatorText.addItem(BusinessConstant.DOLLAR_SIGN);
        separatorText.addItem(BusinessConstant.COMMA);
        separatorText.setFont(FontClass.font14);
        separatorText.setFocusable(false);//去掉按钮文字周围的焦点框
        separatorText.setBackground(Color.WHITE);
        separatorText.setBounds(BaseConstant.CONST110, BaseConstant.CONST200, BaseConstant.CONST120, BaseConstant.CONST30);
        //separatorCustomText
        separatorCustomText = new JTextField();
        separatorCustomText.setFont(FontClass.font14);
        separatorCustomText.setDocument(new ContentLengthControl(BaseConstant.CONST6));//限制用户输入长度
        separatorCustomText.setText(BusinessConstant.CUSTOM_SEPARATOR);
        separatorCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
        separatorCustomText.setBounds(BaseConstant.CONST260, BaseConstant.CONST200, BaseConstant.CONST100, BaseConstant.CONST30);
        separatorCustomText.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {//获取焦点时
                separatorCustomText.setForeground(Color.BLACK);//设置字体颜色
                if (!fixFlag) {//重复修改保持原有修改
                    separatorCustomText.setText("");
                }
                fixFlag = true;
            }

            @Override
            public void focusLost(FocusEvent e) {//失去焦点时
//                separatorCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
//                separatorCustomText.setText("自定义分隔符");
            }
        });
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static Separator instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static Separator createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new Separator();
        }
        return instance;
    }
}
