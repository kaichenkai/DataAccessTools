package com.seemmo.gui.separator;

import javax.swing.*;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 20:05
 */
public class CommonSeparator extends JSeparator {
    public CommonSeparator(int width, int height){
        this.setMaximumSize(new Dimension(width, height));//设置大小
        this.setOrientation(SwingConstants.HORIZONTAL);
    }
}
