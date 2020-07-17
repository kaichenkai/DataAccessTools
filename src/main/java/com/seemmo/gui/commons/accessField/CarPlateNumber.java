package com.seemmo.gui.commons.accessField;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ContentLengthControl;
import com.seemmo.utils.IndexInputControl;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:45
 */
public class CarPlateNumber extends BaseField {
    //单例
    private CarPlateNumber(){
        //CarPlateNumberLabel
        label = new JLabel("车牌号码");
        label.setFont(FontClass.font14);
        label.setBounds(BaseConstant.CONST20, BaseConstant.CONST200, BaseConstant.CONST100, BaseConstant.CONST30);
        //CarPlateNumberIndex
        index = new JTextField();
        index.addKeyListener(new IndexInputControl());//对用户输入的 index的值进行约束
        index.setDocument(new ContentLengthControl(BaseConstant.CONST2));//限制用户输入长度
        index.setFont(FontClass.font14);
        index.setBounds(BaseConstant.CONST140, BaseConstant.CONST200, BaseConstant.CONST120, BaseConstant.CONST30);
        //warningLabel
        warningLabel = new JLabel("必须填写");
        warningLabel.setFont(FontClass.font14);
        warningLabel.setForeground(Color.RED);
        warningLabel.setBounds(BaseConstant.CONST300, BaseConstant.CONST200, BaseConstant.CONST90, BaseConstant.CONST30);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static CarPlateNumber instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static CarPlateNumber createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new CarPlateNumber();
        }
        return instance;
    }
}
