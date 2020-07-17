package com.seemmo.utils;


import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:52
 * 输入控制类, 约束用户的输入的内容
 */
public class RecogPicIndexControl extends KeyAdapter {
    public void keyTyped(KeyEvent event) {
//        String key="*0123456789"+(char)8;//添加 "*"  的位置可以随意
        String key="0123456789" + ",";//允许添加逗号
        if(key.indexOf(event.getKeyChar())<0){
            event.consume();//如果不是数字则取消
        }
    }
}
