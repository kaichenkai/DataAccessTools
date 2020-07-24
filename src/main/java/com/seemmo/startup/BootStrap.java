package com.seemmo.startup;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.commons.*;
import com.seemmo.gui.commons.accessField.IllegalCode;
import com.seemmo.gui.commons.accessField.IllegalTime;
import com.seemmo.gui.commons.accessField.RecordId;
import com.seemmo.gui.frame.MainFrame;
import com.seemmo.gui.listener.AIQualityListener;
import com.seemmo.gui.listener.AITrafficListener;
import com.seemmo.gui.panel.AIQualityConfigPanel;
import com.seemmo.gui.panel.AITrafficConfigPanel;
import com.seemmo.gui.panel.ConfigPanel;
import com.seemmo.gui.panel.SplitPanel;
import com.seemmo.gui.panel.logPanel.LogScrollPanel;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 14:09
 * 接入工具启动类
 */
public class BootStrap {
    public static String business = getBusiness();//业务模式

    public static void main(String[] args) {
        //主面板
        MainFrame mainJFrame = new MainFrame("数据接入工具(3.0)", ScreenSize.getWidth(), ScreenSize.getWidth());

        //工具栏(导航菜单)
        //JToolBar toolBar = new MainMenu(ScreenSize.getWidth() / 2, BaseConstant.CONST50);

        //功能选项
        AITrafficListener aiTrafficListener = AITrafficListener.getInstance("AI预审", ColorClass.color_18a5d6);
        AIQualityListener aiQualityListener = AIQualityListener.getInstance("AI智检", ColorClass.color_bbbbbb);

        //主页面内容面板(分为两个部分: 配置面板和日志面板)
        //1.配置面板
        ConfigPanel configPanel = ConfigPanel.createInstance(Color.WHITE);
        configPanel.setBounds(BaseConstant.CONST0, BaseConstant.CONST50, ScreenSize.getWidth() / 2, ScreenSize.getHeight());
        configPanel.add(aiTrafficListener);//添加导航菜单
        configPanel.add(aiQualityListener);//添加导航菜单
        //2.日志面板
        LogScrollPanel logScrollPanel = LogScrollPanel.instance;

        //AI预审接入配置面板
        AITrafficConfigPanel aiTrafficConfigPanel = AITrafficConfigPanel.instance;//单例(不允许在外面调用构造方法)
        aiTrafficConfigPanel.setPreferredSize(configPanel.getSize());//setPreferredSize 全尺寸
        aiTrafficConfigPanel.setBackground(Color.WHITE);
        //在加载配置文件失败时, 默认显示预审界面
        aiTrafficConfigPanel.addComponents();//AI预审接入配置面板添加组件(标签组件只能被添加到一个面板中)

        //AI智检接入配置面板
        AIQualityConfigPanel aiQualityConfigPanel = AIQualityConfigPanel.instance;//单例
        aiQualityConfigPanel.setPreferredSize(configPanel.getSize());//setPreferredSize 全尺寸
        aiQualityConfigPanel.setBackground(Color.WHITE);
        //默认不显示智检接入配置,也不添加标签组件, 切换的时间添加
        aiQualityConfigPanel.setVisible(false);

        //配置面板叠加
        configPanel.add(aiTrafficConfigPanel);
        configPanel.add(aiQualityConfigPanel);

        //分割面板(左右分割)
        SplitPanel splitPanel = SplitPanel.createInstance();
        splitPanel.setLeftComponent(configPanel);//设置左边的配置面板
        splitPanel.setRightComponent(logScrollPanel);//设置右边的日志面板

        //加载配置文件
        mainJFrame.loadConfigInfo();

        //设置windows界面, 下拉框, 复选框样式
        setWindowStyle();
        //设置UI默认字体
        setUIFont(new javax.swing.plaf.FontUIResource(FontClass.font14));

        //添加到主面板
        mainJFrame.setContentPane(splitPanel);
        mainJFrame.setVisible(true);//显示主面板, 跑起来
    }

    /**
     * 获取用户选择的业务模式: AI预审 or AI智检
     */
    public static String getBusiness() {
        if (AITrafficConfigPanel.instance.isVisible()) {
            return BusinessConstant.AI_TRAFFIC_USINESS_MODE;
        } else if (AIQualityConfigPanel.instance.isVisible()) {
            return BusinessConstant.AI_QUALITY_USINESS_MODE;
        } else {
            return null;
        }
    }

    /**
     * 设置界面风格, 下拉框, 复选框样式
     */
    public static void setWindowStyle(){
        try {
            //界面风格
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//设置为当前系统风格
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//Windows风格
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel") ; //Mac风格
//            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel") ;//Java默认风格
            //下拉框, 复选框样式
            SwingUtilities.updateComponentTreeUI(Separator.instance.separatorText);//分隔符
            SwingUtilities.updateComponentTreeUI(TimeFormat.instance.timeFormatText);//时间格式
            SwingUtilities.updateComponentTreeUI(ProcessNum.instance.processNumText);//进程数量
            SwingUtilities.updateComponentTreeUI(ImageDataMode.instance.compositeModeText);//合成图
            SwingUtilities.updateComponentTreeUI(CombinedPicRule.instance.combinedPicTypeText);//合成图类型
            SwingUtilities.updateComponentTreeUI(CombinedPicRule.instance.carNumPicIndex);//车牌图索引
            SwingUtilities.updateComponentTreeUI(ImageDataMode.instance.sequenceModeText);//序列图
            SwingUtilities.updateComponentTreeUI(RecordId.instance.checkBoxValue);//RecordId
            SwingUtilities.updateComponentTreeUI(IllegalTime.instance.checkBoxValue);//违法时间
            SwingUtilities.updateComponentTreeUI(IllegalCode.instance.comboBoxValue);//违法类型编码
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 设置默认字体
     */
    public static void setUIFont (javax.swing.plaf.FontUIResource font){
        Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource)
                UIManager.put (key, font);
        }
    }
}
