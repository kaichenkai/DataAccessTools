package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.utils.FontClass;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 17:57
 * 接入完成量 && 接入成功量
 */
public class AccessStats {
    public JLabel accessSuccessNumLabel;//接入成功
    public JTextField accessSuccessNumText;//接入成功数量
    public JTextField accessCompleteNumText;//接入完成数量


    //单例
    private AccessStats(){
        //accessSuccessNumLabel
        accessSuccessNumLabel = new JLabel("接入成功: ");
        accessSuccessNumLabel.setFont(FontClass.font16);
        accessSuccessNumLabel.setBounds(BaseConstant.CONST400, BaseConstant.CONST100, BaseConstant.CONST100, BaseConstant.CONST30);
        accessSuccessNumLabel.setBackground(Color.ORANGE);
        //accessSuccessNumText
        accessSuccessNumText = new JTextField("0");
        accessSuccessNumText.setEditable(false);//不可编辑
        accessSuccessNumText.setBorder(null);
        accessSuccessNumText.setFont(FontClass.font16);
        accessSuccessNumText.setBounds(BaseConstant.CONST480, BaseConstant.CONST100, BaseConstant.CONST100, BaseConstant.CONST30);
        accessSuccessNumText.setBackground(Color.WHITE);
//        accessNumText.addActionListener();
        //accessCompleteNumText(暂时不在页面展示)
        accessCompleteNumText = new JTextField("0");
        accessSuccessNumText.setEditable(false);//不可编辑
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static AccessStats instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static AccessStats createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new AccessStats();
        }
        return instance;
    }

    public Integer getAccessSuccessNum(){
        return Integer.parseInt(AccessStats.instance.accessSuccessNumText.getText());
    }

    public void setAccessSuccessNum(Integer accessNum){
        AccessStats.instance.accessSuccessNumText.setText(String.valueOf(accessNum));
    }

    public Integer getAccessCompleteNum(){
        return Integer.parseInt(AccessStats.instance.accessCompleteNumText.getText());
    }

    public void setAccessCompleteNum(Integer accessCompleteNum){
        AccessStats.instance.accessCompleteNumText.setText(String.valueOf(accessCompleteNum));
    }
}
