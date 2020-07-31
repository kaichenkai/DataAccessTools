package com.seemmo.service;

import com.alibaba.fastjson.JSONObject;
import com.seemmo.gui.commons.*;
import com.seemmo.gui.panel.logPanel.LogTextPane;
import com.seemmo.startup.BootStrap;
import com.seemmo.utils.HttpClientUtil;

import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: kaichenkai
 * @create: 7/10/2020 10:38
 */
public abstract class BaseService implements IBaseService{
    public final LogTextPane logging = LogTextPane.instance;
    //图片文件夹路径
    public File imageDirObj;

    //图片文件对象列表
    public List<File> imageObjList;

    //图片模式
    public ImageDataMode imageDataMode = ImageDataMode.instance;

    //序列图模式对象
    public SequencePicRule sequencePicRule = SequencePicRule.instance;

    //分隔符
    public String separator;

    //线程池
    public ExecutorService executorService = null;

    //url地址
    public String accessUrl;

    //统计数据
    public Integer accessTotal;//总量
    public Integer accessCompleteNum;//已完成数量
    public Integer accessSuccessNum;//接入成功数量
    public String accessProgress;//接入进度

    //Lock 锁 (避免多线程抢占资源)
    public Lock threadLock = new ReentrantLock();

    //接口业务抽象方法, 由业务实现类覆写
    public abstract boolean run() throws InterruptedException;

    /**
     * 接入前置方法
     */
    public boolean initializationCheck(){
        //清理日志区内容
        logging.setText("");
        //面板统计置零
        AccessTotal.instance.setAccessTotal(0);
        AccessStats.instance.setAccessCompleteNum(0);
        AccessStats.instance.setAccessSuccessNum(0);
        AccessProgress.instance.setAccessProgress("0%");
        //接入计数置零
        this.accessTotal = 0;
        this.accessCompleteNum = 0;
        this.accessSuccessNum = 0;
        this.accessProgress = "0%";
        //初始化属性
        this.imageDirObj = new File(ImageDir.instance.imageDirText.getText().trim());
        this.imageObjList = new ArrayList<>();
        this.separator = getSeparator();
        this.accessUrl = AccessURL.instance.accessUrlText.getText().trim();
        //输出业务模式
        logging.warning("业务模式:");
        logging.debug(BootStrap.business + "\n");

        //图片模式
        logging.warning("图片模式:");
        logging.debug(ImageDataMode.instance.getImageDataMode() + "\n");
        //如果是序列图, 检查序列图规则
        if (imageDataMode.sequenceModeText.isSelected()) {
            logging.warning("序列图规则检查:");
            boolean isCheckSerialImgRule = this.checkSerialImgRule();
            if (!isCheckSerialImgRule) {
                return false;
            }
        }

        //输出基本配置信息
        logging.warning("基本配置信息:");
        logging.debug(String.format("图片文件夹\t\t%s", this.imageDirObj.getPath()));
        logging.debug(String.format("分隔符\t\t%s", this.separator));
        logging.debug(String.format("推送地址\t\t%s\n", this.accessUrl));

        //3 判断文件夹是否存在
        if (!this.imageDirObj.exists() || !this.imageDirObj.isDirectory()) {
            logging.error(String.format("文件夹不存在：[ %s ]", this.imageDirObj.getPath()));
            return false;
        }

        //4 统计接入数据总量
        logging.warning("统计接入总量, 请等待 ...");
        this.getAccessTotal(imageDirObj);
        if (this.accessTotal <= 0) {
            if (imageDataMode.sequenceModeText.isSelected()) {//可能是序列图标识未配置正确
                logging.error("没有检测到文件夹中的图片数据, 请检查序列图规则配置");
            } else {
                logging.error("文件夹中没有图片数据");
            }
            return false;
        }
        logging.debug(String.format("接入数据总量\t%d\n", this.accessTotal));

        //5 测试平台接入url是否正常
        logging.warning("接口访问验证, 请等待 ...");
        boolean isNormal = this.testAccessApi(this.accessUrl);
        if (!isNormal) {
            return false;
        }
        logging.debug("接口访问正常\n");

        //6 创建线程池
        int processNum = Integer.parseInt(String.valueOf(ProcessNum.instance.processNumText.getSelectedItem()));
        if (executorService == null) {
            executorService = Executors.newFixedThreadPool(processNum);
        }
        return true;
    }

    /**
     * 接入结束调用方法
     */
//    public boolean filishCall(){
//        return true;
//    }

    /**
     * 测试平台接口是否正常
     */
    public boolean testAccessApi(String url) {
        try {
            HttpClientUtil httpUtil = new HttpClientUtil();
            httpUtil.addHeader("Content-Type", "application/json");
            httpUtil.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put("test", "test");
            String jsonArgs = JSONObject.toJSONString(argsMap);
            String responseString = httpUtil.postJson(url, jsonArgs);
            // 解析响应
            // 请求完成则返回 true
            return true;
        } catch (Exception ex) {
            logging.error(String.format("接口访问异常: [%s]", ex));
            return false;
        }
    }

    /**
     * 统计接入记录总量
     * @param imageDirObj: 文件夹对象
     */

    public void getAccessTotal(File imageDirObj) {
        //遍历图片文件夹中的图片
        for (File imgFileObj : Objects.requireNonNull(imageDirObj.listFiles())) {
            boolean isImage = this.isImageFile(imgFileObj);
            if (!isImage) {
                continue;
            }
            //统计违法记录的数量
            //合成图模式
            if (imageDataMode.compositeModeText.isSelected()) {
                this.imageObjList.add(imgFileObj);
                this.accessTotal += 1;
            }
            //序列图模式
            if (imageDataMode.sequenceModeText.isSelected()) {
                //序列图索引值, 序号标识, 修改标记
                int serialIndex = sequencePicRule.getSerialIndex();
                if (serialIndex > 0) {//有填写索引值
                    String imageSerialMart1 = sequencePicRule.imageSerialText1.getText().toString();
                    //String imageSerialMart2 = sequenceImageMode.imageSerialText2.getText().toString();
                    //String imageSerialMart3 = sequenceImageMode.imageSerialText3.getText().toString();
                    //boolean fixSerialMartStatus1 = sequenceImageMode.fixSerialText1;
                    //boolean fixSerialMartStatus2 = sequenceImageMode.fixSerialText2;
                    //boolean fixSerialMartStatus3 = sequenceImageMode.fixSerialText3;
                    String imgName = this.getFileNameNoEx(imgFileObj);//获取不带扩展名的图片名
                    String[] elements = imgName.split(this.separator, -1);
                    String serialMark;
                    try {
                        serialMark = elements[serialIndex];
                    } catch (Exception ex) {
                        logging.error(String.format("图片序列号索引越界: %s", imgName));
                        return;
                    }
                    if (imageSerialMart1.equals(serialMark)) {//序列图序号 01
                        this.imageObjList.add(imgFileObj);
                        this.accessTotal += 1;
                    }
                } else {//没有填写索引值, 相当于合成图模式
                    this.imageObjList.add(imgFileObj);
                    this.accessTotal += 1;
                }
            }
        }
        //设置面板上的接入总量
        AccessTotal.instance.setAccessTotal(this.accessTotal);
    }

    /**
     * 判断是否是图片文件
     */
    public boolean isImageFile(File imageFile) {
        if (imageFile.isDirectory()) {
            //是目录
            return false;
        }
        //图片解码太耗时
//                        try {
//                            //通过ImageReader来解码这个file并返回一个BufferedImage对象
//                            //如果找不到合适的ImageReader则会返回null，我们可以认为这不是图片文件
//                            ImageIO.read(imgFileObj);
//                        } catch(IOException ex) {
//                            //解码失败, 不是图片
//                            continue;
//                        }
        String imageName = imageFile.getName();
        return imageName.endsWith(".JPG") || imageName.endsWith(".PNG") || imageName.endsWith(".jpg") || imageName.endsWith(".png");
    }

    /*
    * 获取不带扩展名的文件名
    */
        public String getFileNameNoEx(File imgFile) {
            String filename = imgFile.getName();
            if (filename.length() > 0) {
                int dot = filename.lastIndexOf('.');
                if ((dot >-1) && (dot < (filename.length()))) {
                    return filename.substring(0, dot);
                }
            }
        return filename;
    }

    /**
     * 获取文件的后缀名
     */
    public String getFileSuffix(File imgFile){
        String filename = imgFile.getName();
        if (filename.length() > 0) {
            int dot = filename.lastIndexOf('.');
            if ((dot >-1) && (dot < (filename.length()))) {
                return filename.substring(dot);
            }
        }
        return "";
    }

    /**
     * 获取分隔符
     * @return
     */
    public String getSeparator() {
        //自定义分隔符
        String customSeparator = Separator.instance.separatorCustomText.getText().trim();
        boolean fixFlag = Separator.instance.fixFlag;
        if (fixFlag && !"".equals(customSeparator) ) {
            //有自定义分隔符不为"", 且不等于默认值
            return customSeparator;
        } else {
            String separator = (String) Separator.instance.separatorText.getSelectedItem();
            if (separator.contains("_")) {
                return "_";
            } else if (separator.contains("$")) {
                return "$";
            } else {
                return ",";
            }
        }
    }

    /**
     * 获取时间格式字符串
     *
     * @return
     */
    public String getTimeFormat() {
        String customTimeFormat = TimeFormat.instance.timeFormatCustomText.getText().toString();
        boolean fixFlat = TimeFormat.instance.fixFlag;
        if (fixFlat && !"".equals(customTimeFormat)) {
            //有自定义时间格式且不为""
            return customTimeFormat;
        } else {
            //返回默认选择项
            int timeFormatIndex = TimeFormat.instance.timeFormatText.getSelectedIndex();
            String timeFormatItem = TimeFormat.instance.timeFormatText.getSelectedItem().toString();
            if (timeFormatIndex == 2) {//unix 毫秒时间戳， 无须额外处理
                return null;
            } else {
                return timeFormatItem;
            }
        }
    }

    /**
     * 检查序列图规则
     */
    public boolean checkSerialImgRule(){
        int serialIndex = SequencePicRule.instance.getSerialIndex();
        if (serialIndex < 0) {
            // 单张序列图可以不填写索引值
//            logging.error("请设置序列图索引值\n");
//            return false;
        }
        //检查序号标识(至少填写前面两个序号标识)
        String imageSerialMart1 = sequencePicRule.imageSerialText1.getText().toString();
        boolean fixSerialMartStatus1 = sequencePicRule.fixSerialText1;
        String imageSerialMart2 = sequencePicRule.imageSerialText2.getText().toString();
        boolean fixSerialMartStatus2 = sequencePicRule.fixSerialText2;
        String imageSerialMart3 = sequencePicRule.imageSerialText3.getText().toString();
        boolean fixSerialMartStatus3 = sequencePicRule.fixSerialText3;
        if (!fixSerialMartStatus1 || "".equals(imageSerialMart1)) {
//            logging.error("请设置序列图 1 的序号标识");
//            return false;
            //序列图1标识  可选
        } else {
            logging.debug(String.format("序列图 1 标识: %s", imageSerialMart1));
        }

        //
        if (!fixSerialMartStatus2 || "".equals(imageSerialMart2)) {
//            logging.error("请设置序列图 2 的序号标识");
//            return false;
            //序列图2  可选
        } else {
            logging.debug(String.format("序列图 2 标识: %s", imageSerialMart2));
        }

        //
        if (!fixSerialMartStatus3 || "".equals(imageSerialMart3)) {
            //序列图3  可选
        } else {
            logging.debug(String.format("序列图 3 标识: %s", imageSerialMart3));
        }
        //
        logging.debug("\n"); //空行
        return true;
    }
}
