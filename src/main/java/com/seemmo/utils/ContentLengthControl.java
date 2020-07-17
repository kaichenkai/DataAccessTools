package com.seemmo.utils;


import com.seemmo.constants.BaseConstant;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * @author: kaichenkai
 * @create: 7/14/2020 15:41
 */
public class ContentLengthControl extends PlainDocument {
    private final int limit;
    public ContentLengthControl(int limit) {
        super(); //调用父类构造
        this.limit = limit;
    }
    public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
        if(str == null) return;
        //限制的长度
        if((getLength() + str.length()) <= this.limit) {
            super.insertString(offset, str, attr);//调用父类方法
        }
    }
}
