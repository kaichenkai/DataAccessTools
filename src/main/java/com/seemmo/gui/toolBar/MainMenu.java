package com.seemmo.gui.toolBar;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 16:18
 */
public class MainMenu extends JToolBar {
    public MainMenu(int width, int height) {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(width, height));
        this.setBackground(Color.WHITE);//背景色
        this.setBorderPainted(false);//去掉外边框
    }
}
