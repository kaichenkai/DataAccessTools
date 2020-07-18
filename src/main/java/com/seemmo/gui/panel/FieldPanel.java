package com.seemmo.gui.panel;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.commons.accessField.*;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:57
 * AI预审字段匹配 panel 类
 */
public class FieldPanel extends JPanel {
    //单例
    private FieldPanel() {//私有化构造方法使得该类无法在外部通过new 进行实例化
        this.setLayout(null);
//        this.setBounds(BaseConstant.CONST20, BaseConstant.CONST160,
//                ConfigPanel.instance.getWidth() - BaseConstant.CONST520,
//                ConfigPanel.instance.getHeight() - BaseConstant.CONST550);//设置坐标, 尺寸
        this.setBounds(ScreenSize.widthRatio(0.01), ScreenSize.heightRatio(0.1240), ScreenSize.widthRatio(0.24), ScreenSize.heightRatio(0.6));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createTitledBorder(null, " 字段匹配 ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, FontClass.boldFont20, ColorClass.color_18a5d6));
        this.addComponents();
    }

    public static FieldPanel instance = new FieldPanel();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
//    public static FieldArea createInstance(){
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new FieldArea();
//        }
//        return instance;
//    }

    public void addComponents(){
        //平台字段Label
        JLabel platformFieldLabel = new JLabel("平台字段");
        platformFieldLabel.setForeground(Color.BLACK);
        platformFieldLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST30, BaseConstant.CONST100, BaseConstant.CONST40);
        platformFieldLabel.setFont(FontClass.font16);
        this.add(platformFieldLabel);

        //接入字段索引值Label
        JLabel accessFieldLabel = new JLabel("接入字段索引值");
        accessFieldLabel.setForeground(Color.BLACK);
        accessFieldLabel.setBounds(BaseConstant.CONST140, BaseConstant.CONST30, BaseConstant.CONST120, BaseConstant.CONST40);
        accessFieldLabel.setFont(FontClass.font16);
        this.add(accessFieldLabel);

        //默认值Label
        JLabel defaultValueLabel = new JLabel("默认值");
        defaultValueLabel.setForeground(Color.BLACK);
        defaultValueLabel.setBounds(BaseConstant.CONST300, BaseConstant.CONST30, BaseConstant.CONST120, BaseConstant.CONST40);
        defaultValueLabel.setFont(FontClass.font16);
        this.add(defaultValueLabel);

        /**
         * 添加字段标签
         */
        Manufacturer manufacturer = Manufacturer.createInstance();
        this.add(manufacturer.label);
        this.add(manufacturer.index);
        this.add(manufacturer.defaultValue);
        //
        RecordId recordId = RecordId.createInstance();
        this.add(recordId.label);
        this.add(recordId.index);
        this.add(recordId.checkBoxValue);
        //
        DeviceCode deviceCode = DeviceCode.createInstance();
        this.add(deviceCode.label);
        this.add(deviceCode.index);
        this.add(deviceCode.defaultValue);
        //
        CarPlateNumber carPlateNumber = CarPlateNumber.createInstance();
        this.add(carPlateNumber.label);
        this.add(carPlateNumber.index);
        this.add(carPlateNumber.warningLabel);
        //
        CarPlateType carPlateType = CarPlateType.createInstance();
        this.add(carPlateType.label);
        this.add(carPlateType.index);
        this.add(carPlateType.defaultValue);
        //
        PlateColorCode plateColorCode = PlateColorCode.createInstance();
        this.add(plateColorCode.label);
        this.add(plateColorCode.index);
        this.add(plateColorCode.defaultValue);
        //
        CarColorCode carColorCode = CarColorCode.createInstance();
        this.add(carColorCode.label);
        this.add(carColorCode.index);
        this.add(carColorCode.defaultValue);
        //
        CarDirect carDirect = CarDirect.createInstance();
        this.add(carDirect.label);
        this.add(carDirect.index);
        this.add(carDirect.defaultValue);
        //
        CarWayCode carWayCode = CarWayCode.createInstance();
        this.add(carWayCode.label);
        this.add(carWayCode.index);
        this.add(carWayCode.defaultValue);
        //
        IllegalCode illegalCode = IllegalCode.createInstance();
        this.add(illegalCode.label);
        this.add(illegalCode.index);
        this.add(illegalCode.defaultValue);

        IllegalTime illegalTime = IllegalTime.createInstance();
        this.add(illegalTime.label);
        this.add(illegalTime.index);
        this.add(illegalTime.checkBoxValue);
    }
}
