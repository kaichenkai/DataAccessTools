package com.seemmo.gui.frame;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.commons.*;
import com.seemmo.gui.commons.accessField.*;
import com.seemmo.gui.listener.AIQualityListener;
import com.seemmo.gui.listener.AITrafficListener;
import com.seemmo.gui.panel.AIQualityConfigPanel;
import com.seemmo.gui.panel.AITrafficConfigPanel;
import com.seemmo.gui.utils.ColorClass;
import com.seemmo.gui.utils.FontClass;
import com.seemmo.startup.BootStrap;
import org.omg.CORBA.OBJ_ADAPTER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
        this.addWindowListener(new WindowExitListener());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭操作
    }

    //退出时执行
    class WindowExitListener extends WindowAdapter{
        @Override
        public void windowClosing(WindowEvent e) {
            super.windowClosing(e);
            //准备存储的内容Map
            Map<String, Object> contentMap = new HashMap<>();
            //1业务模式
            if (AITrafficConfigPanel.instance.isVisible()) {
                contentMap.put("businessMode", BusinessConstant.AI_TRAFFIC_USINESS_MODE);
            } else {
                contentMap.put("businessMode", BusinessConstant.AI_QUALITY_USINESS_MODE);
            }
            //2推送地址
            contentMap.put("accessUrl", AccessURL.instance.accessUrlText.getText().toString());
            //3数据文件夹
            contentMap.put("imageDir", ImageDir.instance.imageDirText.getText().toString());

            //4字段匹配数据
            contentMap.put("manufacturerCodeIndex", Manufacturer.instance.index.getText().toString());
            contentMap.put("manufacturerCodeDefaultValue", Manufacturer.instance.defaultValue.getText().toString());
            //
            contentMap.put("recordIdIndex", RecordId.instance.index.getText().toString());
            contentMap.put("recordIdCheckBoxValue", RecordId.instance.checkBoxValue.isSelected());
            //
            contentMap.put("deviceCodeIndex", DeviceCode.instance.index.getText().toString());
            contentMap.put("deviceCodeDefaultValue", DeviceCode.instance.defaultValue.getText().toString());
            //
            contentMap.put("carPlateNumberIndex", CarPlateNumber.instance.index.getText().toString());
            //
            contentMap.put("illegalCodeIndex", IllegalCode.instance.index.getText().toString());
            contentMap.put("illegalCodeDefaultValue", IllegalCode.instance.defaultValue.getText().toString());
            contentMap.put("illegalCodeComboBoxValue", IllegalCode.instance.comboBoxValue.getSelectedItem().toString());
            //
            contentMap.put("illegalTimeIndex", IllegalTime.instance.index.getText().toString());
            contentMap.put("illegalTimeCheckBoxValue", IllegalTime.instance.checkBoxValue.isSelected());
            //
            contentMap.put("carDirectIndex", CarDirect.instance.index.getText().toString());
            contentMap.put("carDirectDefaultValue", CarDirect.instance.defaultValue.getText().toString());
            //
            contentMap.put("carWayCodeIndex", CarWayCode.instance.index.getText().toString());
            contentMap.put("carWayCodeDefaultValue", CarWayCode.instance.defaultValue.getText().toString());
            //
            contentMap.put("carPlateTypeIndex", CarPlateType.instance.index.getText().toString());
            contentMap.put("carPlateTypeDefaultValue", CarPlateType.instance.defaultValue.getText().toString());
            //
            contentMap.put("plateColorCodeIndex", PlateColorCode.instance.index.getText().toString());
            contentMap.put("plateColorCodeDefaultValue", PlateColorCode.instance.defaultValue.getText().toString());
            //
            contentMap.put("carColorCodeIndex", CarColorCode.instance.index.getText().toString());
            contentMap.put("carColorCodeDefaultValue", CarColorCode.instance.defaultValue.getText().toString());

            //5辅助配置
            contentMap.put("compositeModeText", ImageDataMode.instance.compositeModeText.isSelected());
            contentMap.put("sequenceModeText", ImageDataMode.instance.sequenceModeText.isSelected());
            //
            contentMap.put("combinedPicTypeText", CombinedPicRule.instance.combinedPicTypeText.getSelectedItem().toString());
            contentMap.put("carNumPicIndex", CombinedPicRule.instance.carNumPicIndex.getSelectedItem().toString());
            contentMap.put("recogPicIndex", CombinedPicRule.instance.recogPicIndex.getText().toString());
            //
            contentMap.put("imageSerialIndex", SequencePicRule.instance.imageSerialIndex.getText().toString());
            contentMap.put("fixIndexFlag", SequencePicRule.instance.fixIndexFlag);
            contentMap.put("imageSerialText1", SequencePicRule.instance.imageSerialText1.getText().toString());
            contentMap.put("fixSerialText1", SequencePicRule.instance.fixSerialText1);
            contentMap.put("imageSerialText2", SequencePicRule.instance.imageSerialText2.getText().toString());
            contentMap.put("fixSerialText2", SequencePicRule.instance.fixSerialText2);
            contentMap.put("imageSerialText3", SequencePicRule.instance.imageSerialText3.getText().toString());
            contentMap.put("fixSerialText3", SequencePicRule.instance.fixSerialText3);
            //
            contentMap.put("separatorText", Separator.instance.separatorText.getSelectedItem().toString());
            contentMap.put("separatorCustomText", Separator.instance.separatorCustomText.getText().toString());
            contentMap.put("separatorFixFlag", Separator.instance.fixFlag);
            //
            contentMap.put("timeFormatText", TimeFormat.instance.timeFormatText.getSelectedItem().toString());
            contentMap.put("timeFormatCustomText", TimeFormat.instance.timeFormatCustomText.getText().toString());
            contentMap.put("timeFormatFixFlag", TimeFormat.instance.fixFlag);
            //
            contentMap.put("processNumText", ProcessNum.instance.processNumText.getSelectedItem().toString());

            //存储配置信息
            File configFile = new File(BusinessConstant.CONFIG_FILE_NAME);//当前使用目录
            try(Writer writer = new FileWriter(configFile)){
                String content = JSON.toJSONString(contentMap);
                writer.write(content);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     *
     */
    //开启时从文件中加载配置信息
    public void loadConfigInfo() {
        File file = new File(BusinessConstant.CONFIG_FILE_NAME);
        //文件不存在
        if (!file.exists()) {
            return;
        }
        try(Reader reader = new FileReader(file)) {
            char[] chars = new char[(int) file.length()];//file.length 获取的是字符数量
            reader.read(chars);
            String contentStr = String.valueOf(chars);
            //json转 map
            Map<String, Object> contentMap = JSONObject.toJavaObject(JSONObject.parseObject(contentStr), Map.class);
            //业务模式
            String businessMode = String.valueOf(contentMap.get("businessMode"));
            if (BusinessConstant.AI_TRAFFIC_USINESS_MODE.equals(businessMode)) {
                AITrafficConfigPanel.instance.setVisible(true);
                AIQualityConfigPanel.instance.setVisible(false);
                AITrafficConfigPanel.instance.addComponents();//添加组件
                //切换颜色/字体
                AITrafficListener.instance.setBackground(ColorClass.color_18a5d6);
                AIQualityListener.instance.setBackground(ColorClass.color_bbbbbb);
                AITrafficListener.instance.setFont(FontClass.boldFont20);
                AIQualityListener.instance.setFont(FontClass.font20);
                BootStrap.business= BusinessConstant.AI_TRAFFIC_USINESS_MODE;//切换业务模式
                //合成图模式隐藏
                CombinedPicRule.instance.combinedPicTypeText.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.carNumPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.carNumPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.recogPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                CombinedPicRule.instance.recogPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
                //替换url
                String url = AccessURL.instance.getUrl();
                url = url.replace(BusinessConstant.AIQUALITY_ACCESS_URL, BusinessConstant.AITRAFFIC_ACCESS_URL);//替换URL中间部分
                AccessURL.instance.accessUrlText.setText(url);
            }
            if (BusinessConstant.AI_QUALITY_USINESS_MODE.equals(businessMode)) {
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
            //
            String accessUrl = String.valueOf(contentMap.get("accessUrl"));
            AccessURL.instance.accessUrlText.setText(accessUrl);
            //
            String imageDir = String.valueOf(contentMap.get("imageDir"));
            ImageDir.instance.imageDirText.setText(imageDir);
            //4字段匹配数据
            Manufacturer.createInstance().index.setText(String.valueOf(contentMap.get("manufacturerCodeIndex")));
            Manufacturer.createInstance().defaultValue.setText(String.valueOf(contentMap.get("manufacturerCodeDefaultValue")));
            //
            RecordId.createInstance().index.setText(String.valueOf(contentMap.get("recordIdIndex")));
            RecordId.createInstance().checkBoxValue.setSelected((Boolean) contentMap.get("recordIdCheckBoxValue"));
            //
            DeviceCode.createInstance().index.setText(String.valueOf(contentMap.get("deviceCodeIndex")));
            DeviceCode.createInstance().defaultValue.setText(String.valueOf(contentMap.get("deviceCodeDefaultValue")));
            //
            CarPlateNumber.createInstance().index.setText(String.valueOf(contentMap.get("carPlateNumberIndex")));
            //
            IllegalCode.createInstance().index.setText(String.valueOf(contentMap.get("illegalCodeIndex")));
            IllegalCode.createInstance().defaultValue.setText(String.valueOf(contentMap.get("illegalCodeDefaultValue")));
            IllegalCode.createInstance().comboBoxValue.setSelectedItem(String.valueOf(contentMap.get("illegalCodeComboBoxValue")));
            //
            IllegalTime.createInstance().index.setText(String.valueOf(contentMap.get("illegalTimeIndex")));
            IllegalTime.createInstance().checkBoxValue.setSelected((Boolean) contentMap.get("illegalTimeCheckBoxValue"));
            //
            CarDirect.createInstance().index.setText(String.valueOf(contentMap.get("carDirectIndex")));
            CarDirect.createInstance().defaultValue.setText(String.valueOf(contentMap.get("carDirectDefaultValue")));
            //
            CarWayCode.createInstance().index.setText(String.valueOf(contentMap.get("carWayCodeIndex")));
            CarWayCode.createInstance().defaultValue.setText(String.valueOf(contentMap.get("carWayCodeDefaultValue")));
            //
            CarPlateType.createInstance().index.setText(String.valueOf(contentMap.get("carPlateTypeIndex")));
            CarPlateType.createInstance().defaultValue.setText(String.valueOf(contentMap.get("carPlateTypeDefaultValue")));
            //
            PlateColorCode.createInstance().index.setText(String.valueOf(contentMap.get("plateColorCodeIndex")));
            PlateColorCode.createInstance().defaultValue.setText(String.valueOf(contentMap.get("plateColorCodeDefaultValue")));
            //
            CarColorCode.createInstance().index.setText(String.valueOf(contentMap.get("carColorCodeIndex")));
            CarColorCode.createInstance().defaultValue.setText(String.valueOf(contentMap.get("carColorCodeDefaultValue")));

            //5辅助配置
            ImageDataMode.instance.compositeModeText.setSelected((Boolean) contentMap.get("compositeModeText"));
            ImageDataMode.instance.sequenceModeText.setSelected((Boolean) contentMap.get("sequenceModeText"));
            //
            CombinedPicRule.instance.combinedPicTypeText.setSelectedItem(String.valueOf(contentMap.get("combinedPicTypeText")));
            CombinedPicRule.instance.carNumPicIndex.setSelectedItem(String.valueOf(contentMap.get("carNumPicIndex")));
            CombinedPicRule.instance.recogPicIndex.setText(String.valueOf(contentMap.get("recogPicIndex")));

            //
            boolean fixIndexFlag = (boolean) contentMap.get("fixIndexFlag");
            String imageSerialIndex = String.valueOf(contentMap.get("imageSerialIndex"));
            if (fixIndexFlag && !"".equals(imageSerialIndex)) {
                SequencePicRule.instance.fixIndexFlag = fixIndexFlag;
                SequencePicRule.instance.imageSerialIndex.setText(imageSerialIndex);
                SequencePicRule.instance.imageSerialIndex.setForeground(Color.BLACK);//设置字体颜色
            }
            boolean fixSerialText1 = (boolean) contentMap.get("fixSerialText1");
            String imageSerialText1 = String.valueOf(contentMap.get("imageSerialText1"));
            if (fixSerialText1 && !"".equals(imageSerialText1)) {
                SequencePicRule.instance.fixSerialText1 = fixIndexFlag;
                SequencePicRule.instance.imageSerialText1.setText(imageSerialText1);
                SequencePicRule.instance.imageSerialText1.setForeground(Color.BLACK);//设置字体颜色
            }
            boolean fixSerialText2 = (boolean) contentMap.get("fixSerialText2");
            String imageSerialText2 = String.valueOf(contentMap.get("imageSerialText2"));
            if (fixSerialText2 && !"".equals(imageSerialText2)) {
                SequencePicRule.instance.fixSerialText2 = fixIndexFlag;
                SequencePicRule.instance.imageSerialText2.setText(imageSerialText2);
                SequencePicRule.instance.imageSerialText2.setForeground(Color.BLACK);//设置字体颜色
            }
            boolean fixSerialText3 = (boolean) contentMap.get("fixSerialText3");
            String imageSerialText3 = String.valueOf(contentMap.get("imageSerialText3"));
            if (fixSerialText3 && !"".equals(imageSerialText3)) {
                SequencePicRule.instance.fixSerialText3 = fixIndexFlag;
                SequencePicRule.instance.imageSerialText3.setText(imageSerialText3);
                SequencePicRule.instance.imageSerialText3.setForeground(Color.BLACK);//设置字体颜色
            }

            //
            boolean separatorFixFlag = (boolean) contentMap.get("separatorFixFlag");
            String separatorCustomText = String.valueOf(contentMap.get("separatorCustomText"));
            if (separatorFixFlag && !"".equals(separatorCustomText)) {
                Separator.createInstance().fixFlag = separatorFixFlag;
                Separator.createInstance().separatorCustomText.setText(separatorCustomText);
                Separator.createInstance().separatorCustomText.setForeground(Color.BLACK);//设置字体颜色
            }
            Separator.createInstance().separatorText.setSelectedItem(String.valueOf(contentMap.get("separatorText")));

            //
            boolean timeFormatFixFlag = (boolean) contentMap.get("timeFormatFixFlag");
            String timeFormatCustomText = String.valueOf(contentMap.get("timeFormatCustomText"));
            if (timeFormatFixFlag && !"".equals(timeFormatCustomText)) {
                TimeFormat.createInstance().fixFlag = timeFormatFixFlag;
                TimeFormat.createInstance().timeFormatCustomText.setText(timeFormatCustomText);
                TimeFormat.createInstance().timeFormatCustomText.setForeground(Color.BLACK);//设置字体颜色
            }
            TimeFormat.createInstance().timeFormatText.setSelectedItem(String.valueOf(contentMap.get("timeFormatText")));

            //
            ProcessNum.createInstance().processNumText.setSelectedItem(String.valueOf(contentMap.get("processNumText")));

            //序列图模式改变
            SequencePicRule.instance.imageSerialIndex.setVisible(ImageDataMode.instance.sequenceModeText.isSelected());//选择序列图之后, 显示序号索引值输入框
            SequencePicRule.instance.imageSerialLabel.setVisible(ImageDataMode.instance.sequenceModeText.isSelected());
            SequencePicRule.instance.imageSerialText1.setVisible(ImageDataMode.instance.sequenceModeText.isSelected());//选择序列图之后显示序号标识输入框
            SequencePicRule.instance.imageSerialText2.setVisible(ImageDataMode.instance.sequenceModeText.isSelected());
            SequencePicRule.instance.imageSerialText3.setVisible(ImageDataMode.instance.sequenceModeText.isSelected());
            //合成图模式改变
            CombinedPicRule.instance.combinedPicTypeText.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
            CombinedPicRule.instance.carNumPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
            CombinedPicRule.instance.carNumPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
            CombinedPicRule.instance.recogPicIndexLabel.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
            CombinedPicRule.instance.recogPicIndex.setVisible(AIQualityConfigPanel.instance.isVisible() && ImageDataMode.instance.compositeModeText.isSelected());
            //AI智检模式 && 序列图模式 下, 改变设备编码 1x1
            if (AIQualityConfigPanel.instance.isVisible()){
                if (ImageDataMode.instance.compositeModeText.isSelected()) {
                    //设置设备编码 (设备编码 = 合成图类型 + 车牌索引值)
                    String combinedPicTypeStr = CombinedPicRule.instance.combinedPicTypeText.getSelectedItem().toString();
                    String carNumPicIndexStr = CombinedPicRule.instance.carNumPicIndex.getSelectedItem().toString();
                    String deviceCodeText = combinedPicTypeStr + "_" + carNumPicIndexStr;
                    DeviceCode.instance.defaultValue.setText(deviceCodeText);
                } else {
                    DeviceCode.instance.defaultValue.setText("1x1");
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
