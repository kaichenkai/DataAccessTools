package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.panel.logPanel.LogTextPane;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.service.AIQualityTestService;
import com.seemmo.service.AITrafficTestService;
import com.seemmo.startup.BootStrap;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 17:57
 * 接入测试按钮
 */
public class AccessTestButton extends JButton {
    public static boolean aiTrafficCompositeImageModeTest = false;//ai预审合成图模式测试状态
    public static boolean aiTrafficSequenceImageModeTest = false;//序列图测试状态
    public static boolean aiQualityCompositeImageModeTest = false;//ai智检合成图模式测试状态
    public static boolean aiQualitySequenceImageModeTest = false;//序列图测试状态
    public LogTextPane logging = LogTextPane.instance;

    //单例
    private AccessTestButton() {
        this.setText("接入测试");
        this.setFont(FontClass.boldFont16);
        this.setForeground(Color.WHITE);
//        this.setBounds(BaseConstant.CONST120, BaseConstant.CONST110, BaseConstant.CONST100, BaseConstant.CONST50);
        this.setBounds(ScreenSize.widthRatio(0.0625), ScreenSize.heightRatio(0.075), ScreenSize.widthRatio(0.0521), ScreenSize.heightRatio(0.0460));
        this.setBorderPainted(false);//边框
        this.setFocusPainted(false);//去掉按钮文字周围的焦点框
        this.setBackground(ColorClass.color_18a5d6);
        this.addActionListener();
    }//私有化构造方法使得该类无法在外部通过new 进行实例化

    public static AccessTestButton instance = new AccessTestButton();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
//    public static StartButton createInstance(){
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new StartButton();
//        }
//        return instance;
//    }

    public void addActionListener() {
        //按钮点击事件
        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //根据业务模式应用不同服务类
                boolean runStatus;
                switch (BootStrap.business) {
                    case BusinessConstant.AI_TRAFFIC_USINESS_MODE:
                        AITrafficTestService aiTrafficTestService = AITrafficTestService.instance;
                        runStatus = aiTrafficTestService.run();
                        if (!runStatus) {//规则配置有误
                            if (ImageDataMode.instance.compositeModeText.isSelected()) {
                                AccessTestButton.aiTrafficCompositeImageModeTest = false;//设置为未测试的状态
                                logging.error("\nAI预审(合成图模式)接入测试未通过, 请检查配置后, 再次尝试...");
                            } else if (ImageDataMode.instance.sequenceModeText.isSelected()) {
                                AccessTestButton.aiTrafficSequenceImageModeTest = false;//设置为未测试的状态
                                logging.error("\nAI预审(序列图模式)接入测试通过, 请检查配置后, 再次尝试...");
                            } else {
                                logging.error(BusinessConstant.UNKNOWN_IMAGE_MODEL);
                            }
                        }else {
                            if (ImageDataMode.instance.compositeModeText.isSelected()) {
                                AccessTestButton.aiTrafficCompositeImageModeTest = true;//设置为已测试的状态
                                logging.warning("AI预审(合成图模式)接入测试通过, 请仔细核对字段信息, 然后开始接入!");
                            } else if (ImageDataMode.instance.sequenceModeText.isSelected()) {
                                AccessTestButton.aiTrafficSequenceImageModeTest = true;//设置为已测试的状态
                                logging.warning("AI预审(序列图模式)接入测试通过, 请仔细核对字段信息, 然后开始接入!");
                            } else {
                                logging.error(BusinessConstant.UNKNOWN_IMAGE_MODEL);
                            }
                        }
                        break;
                    case BusinessConstant.AI_QUALITY_USINESS_MODE:
                        AIQualityTestService aiQualityTestService = AIQualityTestService.instance;
                        runStatus = aiQualityTestService.run();
                        if (!runStatus) {//规则配置有误
                            if (ImageDataMode.instance.compositeModeText.isSelected()) {
                                AccessTestButton.aiQualityCompositeImageModeTest = false;//设置为未测试的状态
                                logging.error("\nAI智检(合成图模式)接入测试未通过, 请检查配置后, 再次尝试...");
                            } else if (ImageDataMode.instance.sequenceModeText.isSelected()) {
                                AccessTestButton.aiQualitySequenceImageModeTest = false;//设置为未测试的状态
                                logging.error("\nAI智检(序列图模式)接入测试通过, 请检查配置后, 再次尝试...");
                            } else {
                                logging.error(BusinessConstant.UNKNOWN_IMAGE_MODEL);
                            }
                        }else {
                            if (ImageDataMode.instance.compositeModeText.isSelected()) {
                                AccessTestButton.aiQualityCompositeImageModeTest = true;//设置为已测试的状态
                                logging.warning("AI智检(合成图模式)接入测试通过, 请仔细核对字段信息, 然后开始接入!");
                            } else if (ImageDataMode.instance.sequenceModeText.isSelected()) {
                                AccessTestButton.aiQualitySequenceImageModeTest = true;//设置为已测试的状态
                                logging.warning("AI智检(序列图模式)接入测试通过, 请仔细核对字段信息, 然后开始接入!");
                            } else {
                                logging.error(BusinessConstant.UNKNOWN_IMAGE_MODEL);
                            }
                        }
                        break;
                    default:
                        logging.error(BusinessConstant.BUSINESS_MODE_ERROR);
                }
            }
        });

        //按钮悬停事件绑定MouseAdapter
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                instance.setBackground(ColorClass.color_5fc8ed);
            }
            public void mouseExited(MouseEvent e) {
                instance.setBackground(ColorClass.color_18a5d6);
            }
        });
    }

    /**
     * 设置按钮是否可点击
     *
     * @param status
     */
    public void enabled(boolean status) {
        this.setEnabled(status);
    }
}
