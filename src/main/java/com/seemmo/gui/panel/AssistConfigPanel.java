package com.seemmo.gui.panel;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.commons.*;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:57
 * 辅助配置面板类
 */
public class AssistConfigPanel extends JPanel {
    //单例
    private AssistConfigPanel() {//私有化构造方法使得该类无法在外部通过new 进行实例化
        this.setLayout(null);
//        this.setBounds(BaseConstant.CONST490, BaseConstant.CONST160,
//                ConfigPanel.instance.getWidth() - BaseConstant.CONST500,
//                ConfigPanel.instance.getHeight() - BaseConstant.CONST550);//设置坐标, 尺寸
        this.setBounds(ScreenSize.widthRatio(0.245), ScreenSize.heightRatio(0.1240), ScreenSize.widthRatio(0.25), ScreenSize.heightRatio(0.6));
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createTitledBorder(null, " 辅助配置 ", TitledBorder.CENTER, TitledBorder.DEFAULT_POSITION, FontClass.boldFont20, ColorClass.color_18a5d6));
        this.addComponents();
    }

    public static AssistConfigPanel instance = new AssistConfigPanel();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
//    public static FieldArea createInstance(){
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new FieldArea();
//        }
//        return instance;
//    }

    public void addComponents(){
        //图片数据模式
        ImageDataMode imageDataMode = ImageDataMode.createInstance();
        this.add(imageDataMode.imageDataModeLabel);
        this.add(imageDataMode.compositeModeText);
        this.add(imageDataMode.sequenceModeText);

        //合成图规则(AI智检独有)
        CombinedPicRule combinedPicRule = CombinedPicRule.createInstance();
        this.add(combinedPicRule.combinedPicRuleLabel);
        this.add(combinedPicRule.combinedPicTypeText);
        this.add(combinedPicRule.carNumPicIndexLabel);
        this.add(combinedPicRule.carNumPicIndex);
        this.add(combinedPicRule.recogPicIndexLabel);
        this.add(combinedPicRule.recogPicIndex);

        //序列图
        SequencePicRule sequencePicRule = SequencePicRule.instance;
        this.add(sequencePicRule.sequenceImageModeLabel);
        this.add(sequencePicRule.imageSerialLabel);
        this.add(sequencePicRule.imageSerialIndex);
        this.add(sequencePicRule.imageSerialText1);
        this.add(sequencePicRule.imageSerialText2);
        this.add(sequencePicRule.imageSerialText3);
//        this.add(sequenceImageMode.imageSerialText4);
//        this.add(sequenceImageMode.imageSerialText5);

        //分隔符Label
        Separator separator = Separator.createInstance();
        this.add(separator.separatorLabel);
        this.add(separator.separatorText);
        this.add(separator.separatorCustomText);

        //时间格式化Label
        TimeFormat timeFormat = TimeFormat.createInstance();
        this.add(timeFormat.timeFormatLabel);
        this.add(timeFormat.timeFormatText);
        this.add(timeFormat.timeFormatCustomText);

        //开启线程数量
        ProcessNum processNum = ProcessNum.createInstance();
        this.add(processNum.processNumLabel);
        this.add(processNum.processNumText);
    }
}
