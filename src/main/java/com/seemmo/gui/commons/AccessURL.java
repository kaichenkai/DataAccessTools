package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.utils.FontClass;

import javax.swing.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:00
 */
public class AccessURL {//单例
    public JLabel accessUrlLabel;
    public JTextField accessUrlText;

    //单例
    private AccessURL(){
        //accessUrlLabel
        accessUrlLabel = new JLabel("推送地址: ");
        accessUrlLabel.setFont(FontClass.font16);
        accessUrlLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST20, BaseConstant.CONST100, BaseConstant.CONST40);
        //accessUrlText
        accessUrlText = new JTextField();
        accessUrlText.setFont(FontClass.font16);
        accessUrlText.setText(BusinessConstant.URL_PREFIX + BusinessConstant.AITRAFFIC_ACCESS_URL + BusinessConstant.URL_SUFFIX);
//        accessUrlText.setText("https://10.10.4.39/client/instesv/illegallogic");
        accessUrlText.setToolTipText("请输入推送地址");//悬停提示信息
        accessUrlText.setBounds(BaseConstant.CONST120, BaseConstant.CONST20, BaseConstant.CONST500, BaseConstant.CONST40);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static AccessURL instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static AccessURL createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new AccessURL();
        }
        return instance;
    }

    public String getUrl(){
        return this.accessUrlText.getText().toString();
    }
}
