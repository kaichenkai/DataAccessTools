package com.seemmo.gui.commons.accessField;

import com.seemmo.constants.BaseConstant;
import com.seemmo.utils.ContentLengthControl;
import com.seemmo.utils.IndexInputControl;

import javax.swing.*;

/**
 * @author: kaichenkai
 * @create: 7/8/2020 14:00
 */
public abstract class BaseField implements BaseFieldInterface{
    public JLabel label;
    public JLabel warningLabel;
    public JTextField index;
    public JTextField defaultValue;
    public JCheckBox checkBoxValue;
    public JComboBox<String> comboBoxValue;

    @Override
    public int getIndex() {
        String index = this.index.getText().toString();
        if ("".equals(index)) {
            return -1;
        } else {
            return Integer.parseInt(index);
        }

    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue.getText().toString();
    }

    @Override
    public boolean getCheckBoxValue() {
        return this.checkBoxValue.isSelected();
    }

    /**
     * 获取一个使用的值
     * @param elements
     * @return
     * @throws IndexOutOfBoundsException
     */
    public String getUseValue(String[] elements) throws IndexOutOfBoundsException {//向上抛越界异常
        //优先使用默认值
        String defaultValue = this.getDefaultValue();
        if (!"".equals(defaultValue)) {
            return defaultValue;
        }
        int index = this.getIndex();
        if (index >= 0) {
            return elements[index];
        }
        //
        return null;
    }
}
