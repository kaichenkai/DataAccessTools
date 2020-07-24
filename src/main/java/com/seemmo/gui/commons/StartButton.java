package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.panel.AIQualityConfigPanel;
import com.seemmo.gui.panel.AITrafficConfigPanel;
import com.seemmo.gui.panel.logPanel.LogTextPane;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.service.AIQualityService;
import com.seemmo.service.AITrafficService;
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
 * @create: 7/7/2020 17:57
 * 开启按钮
 */
public class StartButton extends JButton {
    public LogTextPane logging = LogTextPane.instance;

    //单例
    private StartButton() {
        this.setText("开始接入");
        this.setFont(FontClass.boldFont16);
        this.setForeground(Color.WHITE);
        this.setBackground(ColorClass.color_ffd11a);
//        this.setBounds(BaseConstant.CONST660, BaseConstant.CONST110, BaseConstant.CONST100, BaseConstant.CONST50);
        this.setBounds(ScreenSize.widthRatio(0.3754), ScreenSize.heightRatio(0.075), ScreenSize.widthRatio(0.0521), ScreenSize.heightRatio(0.0460));
        this.setBorderPainted(false);//边框
        this.setFocusPainted(false);//去掉按钮文字周围的焦点框
        this.addActionListener();
    }//私有化构造方法使得该类无法在外部通过new 进行实例化

    public static StartButton instance = new StartButton();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
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
                StartButton.instance.enabled(false);//设置为不可点击, 配置页不可切换
                AccessTestButton.instance.enabled(false);//设置为不可点击, 配置页不可切换

                //根据业务模式应用不同服务类
                switch (BootStrap.business) {
                    case BusinessConstant.AI_TRAFFIC_USINESS_MODE:
                        //开始接入之前, 需要先进行接入测试(合成图 or 序列图)
                        Thread aiTrafficServiceThread = new Thread(() -> {
                            if (ImageDataMode.instance.compositeModeText.isSelected()) {
                                if (AccessTestButton.aiTrafficCompositeImageModeTest) {
                                    AITrafficService aiTrafficService = AITrafficService.instance;
                                    boolean runStatus = aiTrafficService.run();
                                    //规则配置有误
                                    if (!runStatus) {
//                                        logging.error("\n请检查配置后, 再次尝试...");
                                        //恢复开关状态
                                        StartButton.instance.enabled(true);
                                        AccessTestButton.instance.enabled(true);
                                    } else {
                                        //在接入完成后, 会恢复开关状态(因为主线程不能阻塞)
                                    }
                                } else {
                                    logging.warning("请先进行AI预审(合成图模式)接入测试, 确定字段匹配正确后, 再开始接入!");
                                }
                            } else if (ImageDataMode.instance.sequenceModeText.isSelected()) {
                                if (AccessTestButton.aiTrafficSequenceImageModeTest) {
                                    AITrafficService aiTrafficService = AITrafficService.instance;
                                    boolean runStatus = aiTrafficService.run();
                                    //规则配置有误
                                    if (!runStatus) {
//                                        logging.error("\n请检查配置后, 再次尝试...");
                                        //恢复开关状态
                                        StartButton.instance.enabled(true);
                                        AccessTestButton.instance.enabled(true);
                                    } else {
                                        //在接入完成后, 会恢复开关状态(因为主线程不能阻塞)
                                    }
                                } else {
                                    logging.warning("请先进行AI预审(序列图模式)接入测试, 确定字段匹配正确后, 再开始接入!");
                                    //恢复开关状态
                                    StartButton.instance.enabled(true);
                                    AccessTestButton.instance.enabled(true);
                                }
                            } else {
                                logging.error(BusinessConstant.UNKNOWN_IMAGE_MODEL);
                                //恢复开关状态
                                StartButton.instance.enabled(true);
                                AccessTestButton.instance.enabled(true);
                            }
                        });
                        aiTrafficServiceThread.start();
                        break;
                    case BusinessConstant.AI_QUALITY_USINESS_MODE:
                        //开始接入之前, 需要先进行接入测试(合成图 or 序列图)
                        if (ImageDataMode.instance.compositeModeText.isSelected()) {
                            if (AccessTestButton.aiQualityCompositeImageModeTest) {
                                AIQualityService aiQualityService = AIQualityService.instance;
                                boolean runStatus = aiQualityService.run();
                                //规则配置有误
                                if (!runStatus) {
//                                        logging.error("\n请检查配置后, 再次尝试...");
                                    //恢复开关状态
                                    StartButton.instance.enabled(true);
                                    AccessTestButton.instance.enabled(true);
                                } else {
                                    //在接入完成后, 会恢复开关状态(因为主线程不能阻塞)
                                }
                            } else {
                                logging.warning("请先进行AI智检(合成图模式)接入测试, 确定字段匹配正确后, 再开始接入!");
                                StartButton.instance.enabled(true);//恢复开关状态
                                AccessTestButton.instance.enabled(true);//恢复开关状态
                                return;
                            }
                        } else if (ImageDataMode.instance.sequenceModeText.isSelected()) {
                            if (AccessTestButton.aiQualitySequenceImageModeTest) {
                                AIQualityService aiQualityService = AIQualityService.instance;
                                boolean runStatus = aiQualityService.run();
                                //规则配置有误
                                if (!runStatus) {
//                                        logging.error("\n请检查配置后, 再次尝试...");
                                    //恢复开关状态
                                    StartButton.instance.enabled(true);
                                    AccessTestButton.instance.enabled(true);
                                } else {
                                    //在接入完成后, 会恢复开关状态(因为主线程不能阻塞)
                                }
                            } else {
                                logging.warning("请先进行AI智检(序列图模式)接入测试, 确定字段匹配正确后, 再开始接入!");
                                StartButton.instance.enabled(true);//恢复开关状态
                                AccessTestButton.instance.enabled(true);//恢复开关状态
                                return;
                            }
                        } else {
                            logging.error(BusinessConstant.UNKNOWN_IMAGE_MODEL);
                            StartButton.instance.enabled(true);//恢复开关状态
                            AccessTestButton.instance.enabled(true);//恢复开关状态
                            return;
                        }
                        break;
                    default:
                        logging.error(BusinessConstant.BUSINESS_MODE_ERROR);
                        StartButton.instance.enabled(true);//恢复开关状态
                        AccessTestButton.instance.enabled(true);//恢复开关状态
                }
            }
        });

        //按钮悬停事件绑定MouseAdapter
        this.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                instance.setBackground(ColorClass.color_ffdb4d);
            }
            public void mouseExited(MouseEvent e) {
                instance.setBackground(ColorClass.color_ffd11a);
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
