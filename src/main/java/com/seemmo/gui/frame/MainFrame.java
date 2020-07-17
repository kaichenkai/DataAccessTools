package com.seemmo.gui.frame;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 15:56
 * 主面板 JFrame 类
 */
public class MainFrame extends JFrame {
    public MainFrame(String title, int width,  int height) {
        this.setLayout(null);
        this.setSize(width, height);//尺寸大小, 单位像素
        this.setTitle(title);//标题
        //修改logo
        Image icon = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("favicon.ico"));
        this.setIconImage(icon);
        //String imagePath = "static/favicon.ico";
        //ImageIcon icon = new ImageIcon(imagePath);
        //this.setIconImage(icon.getImage());
        this.getContentPane().setBackground(Color.WHITE);//背景色
        this.setLocationRelativeTo(null);//取消相对定位
        this.setResizable(true);//尺寸是否可变
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭操作
    }
}
