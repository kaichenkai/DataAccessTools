package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 17:57
 */
public class AccessTotal {
    public JLabel accessTotalLabel;
    public JTextField accessTotalText;

    //单例
    private AccessTotal(){
        //accessNumLabel
        accessTotalLabel = new JLabel("接入总量: ");
        accessTotalLabel.setFont(FontClass.font16);
//        accessTotalLabel.setBounds(BaseConstant.CONST400, BaseConstant.CONST60, BaseConstant.CONST100, BaseConstant.CONST30);
        accessTotalLabel.setBounds(ScreenSize.widthRatio(0.2140), ScreenSize.heightRatio(0.050), ScreenSize.widthRatio(0.0421), ScreenSize.heightRatio(0.0278));
        accessTotalLabel.setBackground(Color.ORANGE);
        //accessNumText
        accessTotalText = new JTextField("0");
        accessTotalText.setEditable(false);//只读
        accessTotalText.setBorder(null);
        accessTotalText.setFont(FontClass.font16);
//        accessTotalText.setBounds(BaseConstant.CONST480, BaseConstant.CONST60, BaseConstant.CONST100, BaseConstant.CONST30);
        accessTotalText.setBounds(ScreenSize.widthRatio(0.2561), ScreenSize.heightRatio(0.050), ScreenSize.widthRatio(0.0621), ScreenSize.heightRatio(0.0278));
        accessTotalText.setBackground(Color.WHITE);
//        accessNumText.addActionListener();
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static AccessTotal instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static AccessTotal createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new AccessTotal();
        }
        return instance;
    }

    public Integer getAccessTotal(){
        return Integer.parseInt(AccessTotal.instance.accessTotalText.getText());
    }

    public void setAccessTotal(Integer accessTotal){
        AccessTotal.instance.accessTotalText.setText(String.valueOf(accessTotal));
    }
}
