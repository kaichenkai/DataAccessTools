package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ContentLengthControl;
import com.seemmo.utils.IndexInputControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 16:09
 * 图片数据模式: 合成图 or 序列图
 */
public class SequencePicRule {//单例
    public JLabel sequenceImageModeLabel;
    public JTextField imageSerialIndex;//图片序号对应的索引值
    public JLabel imageSerialLabel;//图片序号标识
    public JTextField imageSerialText1;
    public JTextField imageSerialText2;
    public JTextField imageSerialText3;
    public JTextField imageSerialText4;
    public JTextField imageSerialText5;
    //是否修改过imageSerialIndexText (用于重新获取焦点后不删除文本框内容)
    public boolean fixIndexFlag = false;
    //是否修改过序号标识
    public boolean fixSerialText1 = false;
    public boolean fixSerialText2 = false;
    public boolean fixSerialText3 = false;
    //单例
    private SequencePicRule() {
        //sequenceImageModeLabel
        sequenceImageModeLabel = new JLabel("序列图规则");
        sequenceImageModeLabel.setFont(FontClass.font14);
        sequenceImageModeLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST150, BaseConstant.CONST80, BaseConstant.CONST30);
        //imageSerialIndex
        //imageSerialIndex = new JTextField("序号索引值");
        imageSerialIndex = new JTextField();
        imageSerialIndex.addKeyListener(new IndexInputControl());//对用户输入的ManufacturerIndex的值进行约束
        imageSerialIndex.setDocument(new ContentLengthControl(BaseConstant.CONST5));//限制用户输入长度
        imageSerialIndex.setText(BusinessConstant.SERIAL_INDEX);
        imageSerialIndex.setFont(FontClass.font14);
        imageSerialIndex.setForeground(Color.LIGHT_GRAY);//设置字体颜色
        imageSerialIndex.setBounds(BaseConstant.CONST110, BaseConstant.CONST150, BaseConstant.CONST80, BaseConstant.CONST30);
        imageSerialIndex.setVisible(false);//默认隐藏
        imageSerialIndex.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {//获取焦点时
                imageSerialIndex.setForeground(Color.BLACK);//设置字体颜色
                if (!fixIndexFlag) {//重复修改保持原有修改
                    imageSerialIndex.setText("");
                }
                fixIndexFlag = true;
            }
            @Override
            public void focusLost(FocusEvent e) {//失去焦点时
//                separatorCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
            }
        });
        //序列图标识
        imageSerialLabel = new JLabel("序号标识");
        imageSerialLabel.setFont(FontClass.font14);
        imageSerialLabel.setBounds(BaseConstant.CONST210, BaseConstant.CONST150, BaseConstant.CONST60, BaseConstant.CONST30);
        imageSerialLabel.setVisible(false);//默认隐藏
        //imageSerialText1
        imageSerialText1 = new JTextField();
        imageSerialText1.setFont(FontClass.font14);
        imageSerialText1.setDocument(new ContentLengthControl(BaseConstant.CONST3));//限制用户输入长度
        imageSerialText1.setText(BusinessConstant.IMAGE_SERIAL_MARK_1);
        imageSerialText1.setForeground(Color.LIGHT_GRAY);//设置字体颜色
        imageSerialText1.setBounds(BaseConstant.CONST280, BaseConstant.CONST150, BaseConstant.CONST30, BaseConstant.CONST30);
        imageSerialText1.setVisible(false);//默认隐藏
        imageSerialText1.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {//获取焦点时
                imageSerialText1.setForeground(Color.BLACK);//设置字体颜色
                if (!fixSerialText1) {//重复修改保持原有修改
                    imageSerialText1.setText("");
                }
                fixSerialText1 = true;
            }
            @Override
            public void focusLost(FocusEvent e) {//失去焦点时
                //separatorCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
            }
        });
        //imageSerialText2
        imageSerialText2 = new JTextField();
        imageSerialText2.setFont(FontClass.font14);
        imageSerialText2.setDocument(new ContentLengthControl(BaseConstant.CONST3));//限制用户输入长度
        imageSerialText2.setText(BusinessConstant.IMAGE_SERIAL_MARK_2);
        imageSerialText2.setForeground(Color.LIGHT_GRAY);//设置字体颜色
        imageSerialText2.setBounds(BaseConstant.CONST320, BaseConstant.CONST150, BaseConstant.CONST30, BaseConstant.CONST30);
        imageSerialText2.setVisible(false);//默认隐藏
        imageSerialText2.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {//获取焦点时
                imageSerialText2.setForeground(Color.BLACK);//设置字体颜色
                if (!fixSerialText2) {//重复修改保持原有修改
                    imageSerialText2.setText("");
                }
                fixSerialText2 = true;
            }
            @Override
            public void focusLost(FocusEvent e) {//失去焦点时
                //separatorCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
            }
        });
        //imageSerialText3
        imageSerialText3 = new JTextField();
        imageSerialText3.setFont(FontClass.font14);
        imageSerialText3.setDocument(new ContentLengthControl(BaseConstant.CONST3));//限制用户输入长度
        imageSerialText3.setText(BusinessConstant.IMAGE_SERIAL_MARK_3);
        imageSerialText3.setForeground(Color.LIGHT_GRAY);//设置字体颜色
        imageSerialText3.setBounds(BaseConstant.CONST360, BaseConstant.CONST150, BaseConstant.CONST30, BaseConstant.CONST30);
        imageSerialText3.setVisible(false);//默认隐藏
        imageSerialText3.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {//获取焦点时
                imageSerialText3.setForeground(Color.BLACK);//设置字体颜色
                if (!fixSerialText3) {//重复修改保持原有修改
                    imageSerialText3.setText("");
                }
                fixSerialText3 = true;
            }
            @Override
            public void focusLost(FocusEvent e) {//失去焦点时
                //separatorCustomText.setForeground(Color.LIGHT_GRAY);//设置字体颜色
            }
        });
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static SequencePicRule instance = new SequencePicRule();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

    //public static 方法，提供给调用者,创建一次
//    public static SequenceImageMode createInstance() {
//        if (null == instance) {//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new SequenceImageMode();
//        }
//        return instance;
//    }

    /**
     * 获取序列图索引
     */
    public int getSerialIndex(){
        String serialIndexStr = this.imageSerialIndex.getText().toString();
        if (this.fixIndexFlag && !"".equals(serialIndexStr)) {
            return Integer.valueOf(serialIndexStr);
        } else {
            return -1;
        }
    }
}
