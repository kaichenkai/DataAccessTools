package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ContentLengthControl;
import com.seemmo.utils.IndexInputControl;
import com.seemmo.utils.RecogPicIndexControl;

import javax.swing.*;
import java.awt.*;

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
    public JTextField carNumPicIndex;
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
        combinedPicTypeText.addItem("");
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
        combinedPicTypeText.setBackground(Color.WHITE);
        combinedPicTypeText.setBounds(BaseConstant.CONST110, BaseConstant.CONST100, BaseConstant.CONST50, BaseConstant.CONST30);
        combinedPicTypeText.setVisible(false);//默认隐藏
        //车牌特写图索引Label
        carNumPicIndexLabel = new JLabel("车牌图索引");
        carNumPicIndexLabel.setFont(FontClass.font14);
        carNumPicIndexLabel.setBounds(BaseConstant.CONST180, BaseConstant.CONST100, BaseConstant.CONST70, BaseConstant.CONST30);
        carNumPicIndexLabel.setVisible(false);//默认隐藏
        //车牌特写图索引
        carNumPicIndex = new JTextField();
        carNumPicIndex.setFont(FontClass.font14);
        carNumPicIndex.addKeyListener(new IndexInputControl());//对用户输入的 index的值进行约束
        carNumPicIndex.setDocument(new ContentLengthControl(BaseConstant.CONST1));//限制用户输入长度
        carNumPicIndex.setBounds(BaseConstant.CONST260, BaseConstant.CONST100, BaseConstant.CONST30, BaseConstant.CONST30);
        carNumPicIndex.setVisible(false);//默认隐藏
        //识别图索引号Label
        recogPicIndexLabel = new JLabel("识别图索引");
        recogPicIndexLabel.setFont(FontClass.font14);
        recogPicIndexLabel.setBounds(BaseConstant.CONST310, BaseConstant.CONST100, BaseConstant.CONST70, BaseConstant.CONST30);
        recogPicIndexLabel.setVisible(false);//默认隐藏
        //识别图索引号
        recogPicIndex = new JTextField();
        recogPicIndex.setFont(FontClass.font14);
        recogPicIndex.setDocument(new ContentLengthControl(BaseConstant.CONST10));//限制用户输入长度
        recogPicIndex.addKeyListener(new RecogPicIndexControl());//对用户输入的 index的值进行约束
        recogPicIndex.setBounds(BaseConstant.CONST390, BaseConstant.CONST100, BaseConstant.CONST55, BaseConstant.CONST30);
        recogPicIndex.setVisible(false);//默认隐藏
    }//私有化构造方法使得该类无法在外部通过new 进行实例化

    public static CombinedPicRule instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

    //public static 方法，提供给调用者,创建一次
    public static CombinedPicRule createInstance() {
        if (null == instance) {//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new CombinedPicRule();
        }
        return instance;
    }
}
