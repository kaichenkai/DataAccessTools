package com.seemmo.gui.commons.accessField;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ContentLengthControl;
import com.seemmo.utils.IndexInputControl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:45
 */
public class IllegalCode extends BaseField{
    private static Map<String, String> illegalCodeMap = new HashMap<>();
    private static List<String> illegalTypeList= new ArrayList<>();

    static {
        illegalTypeList.add("违章变道");
        illegalTypeList.add("逆行");
        illegalTypeList.add("占用公交车道");
        illegalTypeList.add("违章停车");
        illegalTypeList.add("接打电话");
        illegalTypeList.add("主驾未系安全带");
        illegalTypeList.add("副驾未系安全带");
        illegalTypeList.add("大货车闯禁行");
        illegalTypeList.add("号牌限行");
        illegalTypeList.add("不按导向车道行驶");
        illegalTypeList.add("超速");
        illegalTypeList.add("越过停止线");
        illegalTypeList.add("闯红灯");

        //
        illegalCodeMap.put("违章变道", "2004");
        illegalCodeMap.put("逆行", "2013");
        illegalCodeMap.put("占用公交车道", "2006");
        illegalCodeMap.put("违章停车", "2008");
        illegalCodeMap.put("接打电话", "3001");
        illegalCodeMap.put("主驾未系安全带", "3006");
        illegalCodeMap.put("副驾未系安全带", "3007");
        illegalCodeMap.put("大货车闯禁行", "6001");
        illegalCodeMap.put("号牌限行", "5002");
        illegalCodeMap.put("不按导向车道行驶", "2001");
        illegalCodeMap.put("超速", "4002");
        illegalCodeMap.put("越过停止线", "2014");
        illegalCodeMap.put("闯红灯", "1001");
    }
    //单例
    private IllegalCode(){
        //illegalCodeLabel
        label = new JLabel("违法类型代码");
        label.setFont(FontClass.font14);
        label.setBounds(BaseConstant.CONST20, BaseConstant.CONST240, BaseConstant.CONST100, BaseConstant.CONST30);
        //illegalCodeIndex
        index = new JTextField();
        index.addKeyListener(new IndexInputControl());//对用户输入的 index的值进行约束
        index.setDocument(new ContentLengthControl(BaseConstant.CONST2));//限制用户输入长度
        index.setFont(FontClass.font14);
        index.setBounds(BaseConstant.CONST140, BaseConstant.CONST240, BaseConstant.CONST120, BaseConstant.CONST30);
        //illegalCodeDefaultValue
        defaultValue = new JTextField();
        defaultValue.setFont(FontClass.font14);
        defaultValue.setDocument(new ContentLengthControl(BaseConstant.CONST10));//限制用户输入长度
        defaultValue.setBounds(BaseConstant.CONST300, BaseConstant.CONST240, BaseConstant.CONST90, BaseConstant.CONST30);
        //illegalCodeComboBoxValue
        comboBoxValue = new JComboBox<>();
        comboBoxValue.setToolTipText(BusinessConstant.PLEASE_SELECT);
        comboBoxValue.addItem("");
        for (String illegalType: IllegalCode.illegalTypeList) {
            comboBoxValue.addItem(illegalType);
        }
        comboBoxValue.setFont(FontClass.font14);
        comboBoxValue.setFocusable(false);//去掉按钮文字周围的焦点框
        comboBoxValue.setBackground(Color.WHITE);
        comboBoxValue.setBounds(BaseConstant.CONST260, BaseConstant.CONST240, BaseConstant.CONST130, BaseConstant.CONST30);
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static IllegalCode instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static IllegalCode createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new IllegalCode();
        }
        return instance;
    }

    public String getSelectValue() {
        String selectValue = this.comboBoxValue.getSelectedItem().toString();
        if (!"".equals(selectValue)) {
            return IllegalCode.illegalCodeMap.get(selectValue);
        }
        return null;
    }
}
