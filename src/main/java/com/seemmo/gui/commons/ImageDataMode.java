package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.commons.accessField.DeviceCode;
import com.seemmo.gui.panel.AIQualityConfigPanel;
import com.seemmo.gui.utils.FontClass;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 16:09
 * 图片数据模式: 合成图 or 序列图
 */
public class ImageDataMode {//单例
    public JLabel imageDataModeLabel;
    public JCheckBox compositeModeText;
    public JCheckBox sequenceModeText;
    //是否修改过imageSerialIndexText
    public boolean fixFlag = false;

    //单例
    private ImageDataMode() {
        //imageDataModeLabel
        imageDataModeLabel = new JLabel("图片模式");
        imageDataModeLabel.setFont(FontClass.font14);
        imageDataModeLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST50, BaseConstant.CONST80, BaseConstant.CONST30);
        //compositeModeText
        compositeModeText = new JCheckBox("合成图", true);
        compositeModeText.setFont(FontClass.font14);
        compositeModeText.setFocusable(false);//去掉按钮文字周围的焦点框
        compositeModeText.setBounds(BaseConstant.CONST110, BaseConstant.CONST50, BaseConstant.CONST80, BaseConstant.CONST30);
        compositeModeText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sequenceModeText.setSelected(!sequenceModeText.isSelected());
                //序列图模式改变
                SequencePicRule.instance.imageSerialIndex.setVisible(sequenceModeText.isSelected());//选择序列图之后, 显示序号索引值输入框
                SequencePicRule.instance.imageSerialLabel.setVisible(sequenceModeText.isSelected());
                SequencePicRule.instance.imageSerialText1.setVisible(sequenceModeText.isSelected());//选择序列图之后显示序号标识输入框
                SequencePicRule.instance.imageSerialText2.setVisible(sequenceModeText.isSelected());
                SequencePicRule.instance.imageSerialText3.setVisible(sequenceModeText.isSelected());
                //合成图模式改变
                CombinedPicRule.instance.combinedPicTypeText.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.carNumPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.carNumPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.recogPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.recogPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                //AI智检模式 && 序列图模式 下, 改变设备编码 1x1
                if (AIQualityConfigPanel.instance.isVisible()){
                    if (ImageDataMode.instance.compositeModeText.isSelected()) {
                        //设置设备编码 (设备编码 = 合成图类型 + 车牌索引值)
                        String combinedPicTypeStr = CombinedPicRule.instance.combinedPicTypeText.getSelectedItem().toString();
                        String carNumPicIndexStr = CombinedPicRule.instance.carNumPicIndex.getSelectedItem().toString();
                        String deviceCodeText = combinedPicTypeStr + "_" + carNumPicIndexStr;
                        DeviceCode.instance.defaultValue.setText(deviceCodeText);
                    } else {
                        DeviceCode.instance.defaultValue.setText("1x1");
                    }
                }
            }
        });
        //sequenceModeText
        sequenceModeText = new JCheckBox("序列图", false);
        sequenceModeText.setFont(FontClass.font14);
        sequenceModeText.setFocusable(false);//去掉按钮文字周围的焦点框
        sequenceModeText.setBounds(BaseConstant.CONST220, BaseConstant.CONST50, BaseConstant.CONST80, BaseConstant.CONST30);
        sequenceModeText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compositeModeText.setSelected(!compositeModeText.isSelected());
                //序列图模式改变
                SequencePicRule.instance.imageSerialIndex.setVisible(sequenceModeText.isSelected());//选择序列图之后, 显示序号索引值输入框
                SequencePicRule.instance.imageSerialLabel.setVisible(sequenceModeText.isSelected());
                SequencePicRule.instance.imageSerialText1.setVisible(sequenceModeText.isSelected());//选择序列图之后显示序号标识输入框
                SequencePicRule.instance.imageSerialText2.setVisible(sequenceModeText.isSelected());
                SequencePicRule.instance.imageSerialText3.setVisible(sequenceModeText.isSelected());
                //合成图模式改变
                CombinedPicRule.instance.combinedPicTypeText.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.carNumPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.carNumPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.recogPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.recogPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                //AI智检模式 && 序列图模式 下, 改变设备编码 1x1
                if (AIQualityConfigPanel.instance.isVisible()){
                    if (ImageDataMode.instance.compositeModeText.isSelected()) {
                        //设置设备编码 (设备编码 = 合成图类型 + 车牌索引值)
                        String combinedPicTypeStr = CombinedPicRule.instance.combinedPicTypeText.getSelectedItem().toString();
                        String carNumPicIndexStr = CombinedPicRule.instance.carNumPicIndex.getSelectedItem().toString();
                        String deviceCodeText = combinedPicTypeStr + "_" + carNumPicIndexStr;
                        DeviceCode.instance.defaultValue.setText(deviceCodeText);
                    } else {
                        DeviceCode.instance.defaultValue.setText("1x1");
                    }
                }
            }
        });
    }//私有化构造方法使得该类无法在外部通过new 进行实例化

    public static ImageDataMode instance = new ImageDataMode();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

    //public static 方法，提供给调用者,创建一次
//    public static ImageDataMode createInstance() {
//        if (null == instance) {//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new ImageDataMode();
//        }
//        return instance;
//    }

    /**
     * 获取业务图片模式
     */
    public String getImageDataMode(){
        if (this.compositeModeText.isSelected()) {
            return "合成图";
        } else if (this.sequenceModeText.isSelected()) {
            return "序列图";
        } else {
            return BusinessConstant.UNKNOWN_IMAGE_MODEL;
        }
    }
}
