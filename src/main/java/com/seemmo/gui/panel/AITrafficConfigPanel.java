package com.seemmo.gui.panel;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.commons.AccessURL;
import com.seemmo.gui.commons.ImageDir;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 15:58
 */
public class AITrafficConfigPanel extends JPanel{
    //单例面板类attribute
    private AITrafficConfigPanel(){
        this.setLayout(null);//设为空布局后,才能设置子标签的大小和位置
        this.setBounds(BaseConstant.CONST0, BaseConstant.CONST60, ScreenSize.getWidth() / 2, ScreenSize.getHeight());
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static AITrafficConfigPanel instance = new AITrafficConfigPanel();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

//    public static AITrafficPanel getInstance(){//public static 方法，返回实例对象
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new AITrafficPanel();
//        }
//        return instance;//返回 instance指向的对象
//    }

    /**
     * 添加面板组件
     */
    public void addComponents(){
        //推送地址
        AccessURL accessURL = AccessURL.createInstance();
        this.add(accessURL.accessUrlLabel);
        this.add(accessURL.accessUrlText);

        //数据文件夹
        ImageDir imageDir = ImageDir.createInstance();
        this.add(imageDir.imageDirLabel);
        this.add(imageDir.imageDirText);
        this.add(imageDir.imageDirSelectButton);

        //字段匹配面板
        this.add(FieldPanel.instance);
        FieldPanel.instance.addComponents();//添加子标签组件

        //辅助配置面板
        this.add(AssistConfigPanel.instance);

        //接入进度展示面板
        this.add(ProgressDisplayPanel.instance);
    }
}
