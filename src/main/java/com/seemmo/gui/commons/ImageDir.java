package com.seemmo.gui.commons;

import com.seemmo.constants.BaseConstant;
import com.seemmo.gui.listener.AIQualityListener;
import com.seemmo.gui.panel.AIQualityConfigPanel;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.utils.ScreenSize;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 10:00
 */
public class ImageDir {//单例
    public JLabel imageDirLabel;
    public JTextField imageDirText;
    public JButton imageDirSelectButton;

    //单例
    private ImageDir(){//私有化构造方法使得该类无法在外部通过new 进行实例化
        //imageDataDirLabel
        imageDirLabel = new JLabel("数据文件夹: ");
        imageDirLabel.setFont(FontClass.font16);
//        imageDirLabel.setBounds(BaseConstant.CONST20, BaseConstant.CONST80, BaseConstant.CONST100, BaseConstant.CONST40);
        imageDirLabel.setBounds(ScreenSize.widthRatio(0.0104), ScreenSize.heightRatio(0.0670), ScreenSize.widthRatio(0.0520), ScreenSize.heightRatio(0.0370));
        //imageDirText
        imageDirText = new JTextField("");
//        imageDirText = new JTextField("E:\\temp\\images");
        imageDirText.setFont(FontClass.font16);
        imageDirText.setToolTipText("请选择文件夹");//悬停提示信息
//        imageDirText.setBounds(BaseConstant.CONST120, BaseConstant.CONST80, BaseConstant.CONST500, BaseConstant.CONST40);
        imageDirText.setBounds(ScreenSize.widthRatio(0.0625), ScreenSize.heightRatio(0.0670), ScreenSize.widthRatio(0.2704), ScreenSize.heightRatio(0.0370));
        //imageDirSelectButton
        imageDirSelectButton = new JButton("点击选择");
        imageDirSelectButton.setFont(FontClass.font16);
        imageDirSelectButton.setForeground(Color.WHITE);//设置字体颜色
//        imageDirSelectButton.setBounds(BaseConstant.CONST660, BaseConstant.CONST80, BaseConstant.CONST100, BaseConstant.CONST40);
        imageDirSelectButton.setBounds(ScreenSize.widthRatio(0.3437), ScreenSize.heightRatio(0.0670), ScreenSize.widthRatio(0.0520), ScreenSize.heightRatio(0.0370));
        imageDirSelectButton.setBorderPainted(false);//边框
        imageDirSelectButton.setFocusPainted(false);//去掉按钮文字周围的焦点框
        imageDirSelectButton.setBackground(ColorClass.color_18a5d6);
        //按钮点击事件
        imageDirSelectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("选择文件夹");
                // 设置文件选择器的初始目录
                // fileChooser.setCurrentDirectory();
                // 设置风格  网址： https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  // 只选择文件夹
                int option = fileChooser.showOpenDialog(null);  // 获取用户操作
                if (option == JFileChooser.APPROVE_OPTION) {  // Return value if approve (yes, ok) is chosen.
                    File file = fileChooser.getSelectedFile();
                    String selectDir = file.getAbsolutePath();
                    imageDirText.setText(selectDir);
                } else {
                    // System.out.println("打开命令取消");
                }
            }
        });
        //按钮悬停事件绑定MouseAdapter
        imageDirSelectButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                imageDirSelectButton.setFont(FontClass.boldFont16);
            }
            public void mouseExited(MouseEvent e) {
                imageDirSelectButton.setFont(FontClass.font16);
            }
        });
    }

    public static ImageDir instance;//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
    public static ImageDir createInstance(){
        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
            instance = new ImageDir();
        }
        return instance;
    }
}
