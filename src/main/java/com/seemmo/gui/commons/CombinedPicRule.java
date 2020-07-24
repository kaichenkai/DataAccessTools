package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.commons.accessField.DeviceCode;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ContentLengthControl;
import com.seemmo.utils.IndexInputControl;
import com.seemmo.utils.RecogPicIndexControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 16:09
 * 合成图模式:
 */
public class CombinedPicRule {//单例
    public JLabel combinedPicRuleLabel;
    public JComboBox<String> combinedPicTypeText;
    //车牌特写图索引
    public JLabel carNumPicIndexLabel;
    public JComboBox<String> carNumPicIndex;
    //识别图索引
    public JLabel recogPicIndexLabel;
    public JTextField recogPicIndex;

    //单例
    private CombinedPicRule() {
        //compositeImageModeLabel
        combinedPicRuleLabel = new JLabel("合成图规则");
        combinedPicRuleLabel.setFont(FontClass.font14);
        combinedPicRuleLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST100, BaseConstant.CONST80, BaseConstant.CONST30);
        //compositeImageModeText
        combinedPicTypeText = new JComboBox<String>();
        combinedPicTypeText.addItem("1x2");
        combinedPicTypeText.addItem("1x3");
        combinedPicTypeText.addItem("1x4");
        combinedPicTypeText.addItem("2x1");
        combinedPicTypeText.addItem("2x2");
        combinedPicTypeText.addItem("2x3");
        combinedPicTypeText.addItem("3x1");
        combinedPicTypeText.addItem("4x1");
        combinedPicTypeText.setFont(FontClass.font14);
        combinedPicTypeText.setFocusable(false);//去掉按钮文字周围的焦点框
        combinedPicTypeText.setBounds(BaseConstant.CONST110, BaseConstant.CONST100, BaseConstant.CONST50, BaseConstant.CONST30);
        combinedPicTypeText.setVisible(false);//默认隐藏
        combinedPicTypeText.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String itemString = e.getItem().toString();//获取列表成员（字符串类）
                String[] elements = itemString.split("x");
                int row = Integer.parseInt(elements[0]);
                int column = Integer.parseInt(elements[1]);
                //改变车牌图索引的选项
                carNumPicIndex.removeAllItems();
                for (int i=0; i <= row * column; i++) {
                    carNumPicIndex.addItem(String.valueOf(i));
                }
                //改变设备编码 (设备编码 = 合成图类型 + 车牌索引值)
                String carNumPicIndexStr = carNumPicIndex.getSelectedItem().toString();
                String deviceCodeText = itemString + "_" + carNumPicIndexStr;
                DeviceCode.instance.defaultValue.setText(deviceCodeText);
            }
        });
        //车牌特写图索引Label
        carNumPicIndexLabel = new JLabel("车牌图位置");
        carNumPicIndexLabel.setFont(FontClass.font14);
        carNumPicIndexLabel.setBounds(BaseConstant.CONST180, BaseConstant.CONST100, BaseConstant.CONST70, BaseConstant.CONST30);
        carNumPicIndexLabel.setVisible(false);//默认隐藏
        //车牌特写图索引
        carNumPicIndex = new JComboBox<>();
        //根据合成图类型, 增加可选项
        String[] elements = combinedPicTypeText.getSelectedItem().toString().split("x");
        int row = Integer.parseInt(elements[0]);
        int column = Integer.parseInt(elements[1]);
        for (int i=0; i <= row * column; i++) {
            carNumPicIndex.addItem(String.valueOf(i));
        }
        carNumPicIndex.setFont(FontClass.font14);
        carNumPicIndex.setFocusable(false);//去掉按钮文字周围的焦点框
        carNumPicIndex.setBounds(BaseConstant.CONST260, BaseConstant.CONST100,  BaseConstant.CONST40, BaseConstant.CONST30);
        carNumPicIndex.setVisible(false);//默认隐藏
        carNumPicIndex.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String itemString = e.getItem().toString();//获取列表成员（字符串类）
                //改变设备编码 (设备编码 = 合成图类型 + 车牌索引值)
                String deviceCodeText;
                String combinedPicTypeStr = combinedPicTypeText.getSelectedItem().toString();
                deviceCodeText = combinedPicTypeStr;
                if (!"".equals(itemString)) {
                    deviceCodeText = combinedPicTypeStr + "_" + itemString;
                }
                DeviceCode.instance.defaultValue.setText(deviceCodeText);
            }
        });
        //识别图索引号Label
        recogPicIndexLabel = new JLabel("识别图顺序");
        recogPicIndexLabel.setFont(FontClass.font14);
        recogPicIndexLabel.setBounds(BaseConstant.CONST320, BaseConstant.CONST100, BaseConstant.CONST70, BaseConstant.CONST30);
        recogPicIndexLabel.setVisible(false);//默认隐藏
        //识别图索引号
        recogPicIndex = new JTextField();
        recogPicIndex.setFont(FontClass.font14);
        recogPicIndex.setDocument(new ContentLengthControl(BaseConstant.CONST10));//限制用户输入长度
        recogPicIndex.addKeyListener(new RecogPicIndexControl());//对用户输入的 index的值进行约束
        recogPicIndex.setBounds(BaseConstant.CONST400, BaseConstant.CONST100, BaseConstant.CONST55, BaseConstant.CONST30);
        recogPicIndex.setVisible(false);//默认隐藏
    }//私有化构造方法使得该类无法在外部通过new 进行实例化

    public static CombinedPicRule instance = new CombinedPicRule();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

    //public static 方法，提供给调用者,创建一次
//    public static CombinedPicRule createInstance() {
//        if (null == instance) {//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new CombinedPicRule();
//        }
//        return instance;
//    }
}
