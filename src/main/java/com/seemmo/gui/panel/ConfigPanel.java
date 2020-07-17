package com.seemmo.gui.panel;

import com.seemmo.gui.commons.AccessURL;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 17:59
 * 配置内容
 */
public class ConfigPanel extends JPanel {
    //单例面板类
    private ConfigPanel(Color color){
        this.setLayout(null);
        this.setBackground(color);
        this.setBorder(null);//去掉边框
        this.addComponents();
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static ConfigPanel instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static ConfigPanel createInstance(Color color){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new ConfigPanel(color);
        }
        return instance;
    }

    /**
     * 添加菜单组件
     */
    public void addComponents(){
        //导航菜单
    }
}
