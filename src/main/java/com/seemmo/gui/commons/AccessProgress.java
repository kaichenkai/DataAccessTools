package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.utils.FontClass;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 17:57
 */
public class AccessProgress {
    public JLabel accessProgressLabel;
    public JTextField accessProgressText;

    //单例
    private AccessProgress(){
        //accessProgressLabel
        accessProgressLabel = new JLabel("接入进度: ");
        accessProgressLabel.setFont(FontClass.font16);
        accessProgressLabel.setBounds(BaseConstant.CONST400, BaseConstant.CONST140, BaseConstant.CONST100, BaseConstant.CONST30);
        accessProgressLabel.setBackground(Color.ORANGE);
        //accessProgressText
        accessProgressText = new JTextField("0.00%");
        accessProgressText.setEditable(false);//只读
        accessProgressText.setBorder(null);
        accessProgressText.setFont(FontClass.font16);
        accessProgressText.setBounds(BaseConstant.CONST480, BaseConstant.CONST140, BaseConstant.CONST100, BaseConstant.CONST30);
        accessProgressText.setBackground(Color.WHITE);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static AccessProgress instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static AccessProgress createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new AccessProgress();
        }
        return instance;
    }

    public String getAccessProgress(){
//        String progressStr = AccessProgress.instance.accessProgressText.getText();
//        double progressDouble = Double.parseDouble(progressStr.replace("%", ""));//去掉百分号
        return AccessProgress.instance.accessProgressText.getText();
    }

    public void setAccessProgress(String accessProgress){
//        String progress = accessProgress * 100 + "%";
        AccessProgress.instance.accessProgressText.setText(accessProgress);
    }
}
