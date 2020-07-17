package com.seemmo.gui.panel.logPanel;

import com.seemmo.gui.utils.FontClass;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import java.awt.*;

/**
 * @author: kaichenkai
 * @create: 7/6/2020 17:59
 */
public class LogTextPane extends JTextPane {
    //单例面板类
    private LogTextPane(){
        this.setLayout(null);
        this.setBackground(Color.BLACK);
        this.setFont(FontClass.font14);
        //this.setBounds(BaseConstant.CONST0, BaseConstant.CONST0, ScreenSize.getWidth() / 2, ScreenSize.getHeight());
        this.setEditable(false);//不可编辑
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static LogTextPane instance = new LogTextPane();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个
    //public static 方法，提供给调用者,创建一次
//    public static LogTextPane createInstance(){
//        if(null==instance){//第一次访问的时候，发现instance没有指向任何对象，这时实例化一个对象
//            instance = new LogTextPane();
//        }
//        return instance;
//    }

    public void debug(String logInfo) {
        Style style = this.getStyledDocument().addStyle(null, null);// 获取组件空样式，addStyle(null, null)会返回一个空样式
        StyleConstants.setForeground(style, Color.GREEN);// 将style的设置颜色
        this.append(logInfo, style);//追加日志
    }

    public void info(String logInfo) {
        Style style = this.getStyledDocument().addStyle(null, null);// 获取组件空样式，addStyle(null, null)会返回一个空样式
        StyleConstants.setForeground(style, Color.WHITE);// 将style的设置颜色
        this.append(logInfo, style);//追加日志
    }

    public void warning(String logInfo) {
        Style style = this.getStyledDocument().addStyle(null, null);// 获取组件空样式，addStyle(null, null)会返回一个空样式
        StyleConstants.setForeground(style, Color.ORANGE);// 将style的设置颜色
        this.append(logInfo, style);//追加日志
    }

    public void error(String logInfo) {
        Style style = this.getStyledDocument().addStyle(null, null);// 获取组件空样式，addStyle(null, null)会返回一个空样式
        StyleConstants.setForeground(style, Color.RED);// 将style的设置颜色
        this.append(logInfo, style);//追加日志
    }

    //暂时没用
    public void addLog(Color color, String logInfo) {
        Style style = this.getStyledDocument().addStyle(null, null);// 获取组件空样式，addStyle(null, null)会返回一个空样式
        StyleConstants.setForeground(style, color);// 将style的设置颜色
        this.append(logInfo, style);//追加日志
    }

    public void append(String logInfo, Style style){
        if (logInfo != null) {
            int contentLenth = this.getStyledDocument().getLength();// 这一句是获取当前面板内容的总长度，
            try {
                // 作为要插入内容的偏移量 this._new.getText()+"\n"这一句是获取输入面板内容 style这一句是使用的样式
                this.getStyledDocument().insertString(contentLenth, logInfo + "\n", style);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            //实现垂直滚动条自动下滑到最低端
            this.setCaretPosition(this.getStyledDocument().getLength());
        }
    }
}
