package com.seemmo.gui.listener;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.commons.*;
import com.seemmo.gui.panel.AIQualityConfigPanel;
import com.seemmo.gui.panel.AITrafficConfigPanel;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.startup.BootStrap;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * @author: kaichenkai
 * @create: 7/6/2020 15:58
 */
public class AIQualityListener extends JButton {
    //单例
    private AIQualityListener(String text, Color color) {
        this.setText(text);
        //this.setBounds(ScreenSize.getWidth() / 4 + BaseConstant.CONST10, BaseConstant.CONST0, BaseConstant.CONST200, BaseConstant.CONST50);
        this.setBounds(ScreenSize.widthRatio(0.2500) + ScreenSize.widthRatio(0.0052), ScreenSize.heightRatio(0.0100), ScreenSize.widthRatio(0.1041), ScreenSize.heightRatio(0.0462));
        this.setForeground(Color.WHITE);
        this.setFont(FontClass.font20);
        this.setBorderPainted(false);//去掉边框
        this.setFocusPainted(false);//去掉按钮文字周围的焦点框
        this.setBackground(color);
        //事件绑定
        this.addActionListener();
    }

    public static AIQualityListener instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

    //public static 方法，返回实例对象
    public static AIQualityListener getInstance(String text, Color color) {
        if (null == instance) {//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new AIQualityListener(text, color);
        }
        return instance;//返回 instance指向的对象
    }

    //添加事件
    public void addActionListener() {
        //按钮点击事件绑定
        this.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (StartButton.instance.isEnabled() && AccessTestButton.instance.isEnabled()) {//程序启动后不允许切换配置页
                    AIQualityConfigPanel.instance.setVisible(true);
                    AITrafficConfigPanel.instance.setVisible(false);
                    AIQualityConfigPanel.instance.addComponents();//添加组件
                    //切换颜色/字体
                    AIQualityListener.instance.setBackground(ColorClass.color_18a5d6);
                    AITrafficListener.instance.setBackground(ColorClass.color_bbbbbb);
                    AIQualityListener.instance.setFont(FontClass.boldFont20);
                    AITrafficListener.instance.setFont(FontClass.font20);
                    //切换业务模式
                    BootStrap.business = BusinessConstant.AI_QUALITY_USINESS_MODE;
                    //合成图模式显示
                    CombinedPicRule.instance.combinedPicTypeText.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                    CombinedPicRule.instance.carNumPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                    CombinedPicRule.instance.carNumPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                    CombinedPicRule.instance.recogPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                    CombinedPicRule.instance.recogPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                    //更换推送地址URL
                    String url = AccessURL.instance.getUrl();
                    url = url.replace(BusinessConstant.AITRAFFIC_ACCESS_URL, BusinessConstant.AIQUALITY_ACCESS_URL);//替换URL中间部分
                    AccessURL.instance.accessUrlText.setText(url);
                }
            }
        });

        //按钮悬停事件绑定MouseAdapter
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                AIQualityListener.instance.setFont(FontClass.boldFont20);
            }

            public void mouseExited(MouseEvent e) {
                if (!AIQualityConfigPanel.instance.isVisible()) {
                    AIQualityListener.instance.setFont(FontClass.font20);
                }
            }
        });
    }
}
