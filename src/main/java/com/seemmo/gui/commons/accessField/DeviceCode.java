package com.seemmo.gui.commons.accessField;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ContentLengthControl;
import com.seemmo.utils.IndexInputControl;

import javax.swing.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:45
 */
public class DeviceCode extends BaseField{
    //单例
    private DeviceCode(){
        //deviceCodeLabel
        label = new JLabel("设备编号");
        label.setFont(FontClass.font14);
        label.setBounds(BaseConstant.CONST20, BaseConstant.CONST160, BaseConstant.CONST100, BaseConstant.CONST30);
        //deviceCodeIndex
        index = new JTextField();
        index.addKeyListener(new IndexInputControl());//对用户输入的 index的值进行约束
        index.setDocument(new ContentLengthControl(BaseConstant.CONST2));//限制用户输入长度
        index.setFont(FontClass.font14);
        index.setBounds(BaseConstant.CONST140, BaseConstant.CONST160, BaseConstant.CONST120, BaseConstant.CONST30);
        //deviceCodeDefaultValue
        defaultValue = new JTextField();
        defaultValue.setFont(FontClass.font14);
        defaultValue.setDocument(new ContentLengthControl(BaseConstant.CONST50));//限制用户输入长度
        defaultValue.setBounds(BaseConstant.CONST300, BaseConstant.CONST160, BaseConstant.CONST90, BaseConstant.CONST30);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static DeviceCode instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static DeviceCode createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new DeviceCode();
        }
        return instance;
    }
}
