package com.seemmo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.commons.*;
import com.seemmo.gui.commons.accessField.*;
import com.seemmo.utils.HttpClientUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author: kaichenkai
 * @create: 7/7/2020 19:19
 *
 */
public class AIQualityService extends BaseService {
    //单例
    private AIQualityService() {
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static AIQualityService instance = new AIQualityService();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

    @Override
    public boolean run() {
        //接入初始化
        boolean checkStatus = this.initializationCheck();
        if (!checkStatus){
            return false;
        }

        logging.warning("开始接入...");
        //每测试一批数据给定一个唯一的标识编码
        String batchFlag = UUID.randomUUID().toString();

        //线程控制 步骤1
        CountDownLatch countDownLatch = new CountDownLatch(1);

        //推送第一条数据
        logging.warning("推送第一条数据:");
        int instesvStatus = 0;
        File imgFirstFile = this.imageObjList.get(0);
        try {
            Map<String, Object> argsMap = dataMapping(imgFirstFile, batchFlag, instesvStatus);
            if (argsMap == null) {//规则设置有误, 停止执行
                return false;
            }
            //数据推送到平台
            this.pushData(argsMap, imgFirstFile, countDownLatch);
        } catch (Exception ex) {
            logging.error(String.format("数据映射错误, 图片名: %s, 错误: %s", imgFirstFile.getName(), ex));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException exception) {
            logging.error("线程等待错误: " + exception.toString());
            return false;
        }

        //线程控制 步骤2
        countDownLatch = new CountDownLatch(this.imageObjList.size() - 2);//排除首尾的数据
        //遍历图片文件夹中的图片
        logging.warning("推送中间数据:");
        instesvStatus = 1;
        for (final File imgFileObj : this.imageObjList.subList(1, this.imageObjList.size() - 1)) {
            final Map<String, Object> argsMap;
            try {
                argsMap = dataMapping(imgFileObj, batchFlag, instesvStatus);
            } catch (Exception ex) {
                logging.error(String.format("数据映射错误: %s, 错误: %s", imgFileObj.getName(), ex));
                return false;//映射失败, 停止执行
            }
            if (argsMap == null) {//规则设置有误, 停止执行
                return false;
            }
            //数据推送到平台
            this.pushData(argsMap, imgFileObj, countDownLatch);
        }

        //开启子线程等待推送最后一条数据(处理阻塞问题)
        CountDownLatch finalCountDownLatch = countDownLatch;
        Thread daemonThread = new Thread(() -> {
            //推送最后一条数据
            try {
                finalCountDownLatch.await();//等待前面的线程任务执行完成(这里会阻塞)
            } catch (InterruptedException exception) {
                logging.error("线程等待错误: " + exception.toString());
                return;
            }
            logging.warning("推送最后一条数据:");
            int instesvStatus1 = 2;
            File imgLastFile = this.imageObjList.get(this.imageObjList.size() - 1);
            try {
                Map<String, Object> argsMap = dataMapping(imgLastFile, batchFlag, instesvStatus1);
                if (argsMap == null) {//规则设置有误, 停止执行
                    return;
                }
                //数据推送到平台
                pushData(argsMap, imgLastFile, finalCountDownLatch);
            } catch (Exception ex) {
                logging.error(String.format("数据映射错误, 图片名: %s, 错误: %s", imgLastFile.getName(), ex));
            }
        });
        daemonThread.start();
        //
        return true;
    }

    /**
     * 数据处理: dataMapping
     */
    public Map<String, Object> dataMapping(File imgFileObj, String batchFlag, int instesvStatus) throws Exception {
        Map<String, Object> argsMap = new HashMap<>();
        argsMap.put(BusinessConstant.BATCH_FLAG, batchFlag);
        argsMap.put(BusinessConstant.INSTESV_STATUS, instesvStatus);
        //获取对应规则
        String[] elements = this.getFileNameNoEx(imgFileObj).split(this.separator);
        String suffix = this.getFileSuffix(imgFileObj);
        //厂商编码
        String manufacturerCode = Manufacturer.instance.getUseValue(elements);
        if (manufacturerCode == null) {
            logging.error("请设置字段匹配规则：厂商编码");
            return null;
        }
        if (manufacturerCode.length() > BaseConstant.CONST10) {
            logging.error("厂商编码内容超出限制(10)");
            return null;
        }
        argsMap.put(BusinessConstant.MANUFACTURER_CODE, manufacturerCode);

        //违法记录ID
        boolean checkBoxValue = RecordId.instance.getCheckBoxValue();
        int recordIndex;
        if (checkBoxValue) {
            argsMap.put(BusinessConstant.RECORD_ID, UUID.randomUUID().toString());
        } else if ((recordIndex = RecordId.instance.getIndex()) >= 0) {
            String recordId = elements[recordIndex];
            if (recordId.length() > BaseConstant.CONST100) {
                logging.error("违法记录ID内容超出限制(100)");
                return null;
            }
            argsMap.put(BusinessConstant.RECORD_ID, recordId);
        } else {
            logging.error("请设置字段匹配规则：违法记录ID");
            return null;
        }

        //设备编号
        String deviceCode = DeviceCode.instance.getUseValue(elements);
        if (deviceCode == null) {
            logging.error("请设置字段匹配规则：设备编号");
            return null;
        }
        if (deviceCode.length() > BaseConstant.CONST50) {
            logging.error("设备编号内容超出限制(50)");
            return null;
        }
        argsMap.put(BusinessConstant.DEVICE_CODE, deviceCode);

        //车牌号码
        int carPlateNumberIndex = CarPlateNumber.instance.getIndex();
        if (carPlateNumberIndex < 0) {
            logging.error("请设置字段匹配规则：车牌号码");
            return null;
        }
        String carPlateNumber = elements[carPlateNumberIndex];
        if (carPlateNumber.length() > BaseConstant.CONST10) {
            logging.error("车牌号码内容超出限制(10)");
            return null;
        }
        argsMap.put(BusinessConstant.CAR_PLATE_NUMBER, carPlateNumber);

        //车牌类型代码
        String carPlateType = CarPlateType.instance.getUseValue(elements);
        if (carPlateType == null) {
            logging.error("请设置字段匹配规则：车牌类型代码");
            return null;
        }
        if (carPlateType.length() > BaseConstant.CONST10) {
            logging.error("车牌类型代码内容超出限制(10)");
            return null;
        }
        argsMap.put(BusinessConstant.CAR_PLATE_TYPE, carPlateType);

        //车牌颜色代码
        String plateColorCode = PlateColorCode.instance.getUseValue(elements);
        if (plateColorCode == null) {
            logging.error("请设置字段匹配规则：车牌颜色代码");
            return null;
        }
        if (plateColorCode.length() > BaseConstant.CONST10) {
            logging.error("车牌颜色代码内容超出限制(10)");
            return null;
        }
        argsMap.put(BusinessConstant.PLATE_COLOR_CODE, plateColorCode);

        //车辆颜色代码
        String carColorCode = CarColorCode.instance.getUseValue(elements);
        if (carColorCode == null) {
            logging.error("请设置字段匹配规则：车辆颜色代码");
            return null;
        }
        if (carColorCode.length() > BaseConstant.CONST10) {
            logging.error("车辆颜色代码内容超出限制(10)");
            return null;
        }
        argsMap.put(BusinessConstant.CAR_COLOR_CODE, carColorCode);

        //行车方向代码
        String carDirect = CarDirect.instance.getUseValue(elements);
        if (carDirect == null) {
            logging.error("请设置字段匹配规则：行车方向代码");
            return null;
        }
        if (carDirect.length() > BaseConstant.CONST10) {
            logging.error("行车方向代码内容超出限制(10)");
            return null;
        }
        argsMap.put(BusinessConstant.CAR_DIRECT, carDirect);

        //车道号
        String carWayCodeStr = CarWayCode.instance.getUseValue(elements);
        if (carWayCodeStr == null) {
            logging.error("请设置字段匹配规则：车道号");
            return null;
        }
        if (carWayCodeStr.length() > BaseConstant.CONST9) {
            logging.error("车道号内容超出限制(9)");
            return null;
        }
        int carWayCode = Integer.parseInt(carWayCodeStr);
        argsMap.put(BusinessConstant.CAR_WAY_CODE, carWayCode);

        //违法类型代码
        String illegalCode = IllegalCode.instance.getUseValue(elements);
        if (illegalCode == null) {
            logging.error("请设置字段匹配规则：违法类型代码");
            return null;
        }
        if (illegalCode.length() > BaseConstant.CONST10) {
            logging.error("违法类型代码内容超出限制(10)");
            return null;
        }
        argsMap.put(BusinessConstant.ILLEGAL_CODE, illegalCode);

        //违法时间
        boolean illegalTimeCheckBoxValue = IllegalTime.instance.getCheckBoxValue();
        int illegalTimeIndex;
        String illegalTime;
        if (illegalTimeCheckBoxValue) {
            //使用当前时间
            illegalTime = String.valueOf(new Date().getTime());
        } else if ((illegalTimeIndex = IllegalTime.instance.getIndex()) >= 0) {
            //优先使用自定义时间格式
            String timeFormat = this.getTimeFormat();
            if (timeFormat == null) {
                illegalTime = elements[illegalTimeIndex];
            } else {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
                Date dateTime = simpleDateFormat.parse(elements[illegalTimeIndex]);
                illegalTime = String.valueOf(dateTime.getTime());
            }
        } else {
            logging.error("请设置字段匹配规则：违法时间");
            return null;
        }
        argsMap.put(BusinessConstant.ILLEGAL_TIME, illegalTime);

        //图片数据
        //1.合成图
        if (imageDataMode.compositeModeText.isSelected()) {
            //1.1 合成图类型
            String combinedPicType = CombinedPicRule.instance.combinedPicTypeText.getSelectedItem().toString();
            if (!"".equals(combinedPicType)) {
                argsMap.put(BusinessConstant.COMBINED_PIC_TYPE, combinedPicType);
            }
            //1.2 车牌图索引值
            String carNumPicIndex = CombinedPicRule.instance.carNumPicIndex.getText().toString();
            if (!"".equals(carNumPicIndex)) {
                argsMap.put(BusinessConstant.CAR_NUM_IMAGE_INDEX, Integer.valueOf(carNumPicIndex));
            }
            //1.3 识别图索引值
            String recogImageIndex = CombinedPicRule.instance.recogPicIndex.getText().toString();
            if (!"".equals(recogImageIndex)) {
                argsMap.put(BusinessConstant.RECOG_IMAGE_INDEX, recogImageIndex);
            }
            //1.4 合成图数据
            byte[] fileByte;
            fileByte = Files.readAllBytes(imgFileObj.toPath());
            // base64 加密
            String combinedPicData = Base64.getEncoder().encodeToString(fileByte);
            argsMap.put(BusinessConstant.COMBINED_PIC_DATA, combinedPicData);
        }

        //2.序列图
        if (imageDataMode.sequenceModeText.isSelected()) {
            //序列图索引值, 序号标识, 修改标记
            int serialIndex = sequencePicRule.getSerialIndex();
            //String imageSerialMart1 = sequenceImageMode.imageSerialText1.getText().toString();
            String imageSerialMart2 = sequencePicRule.imageSerialText2.getText().toString();
            String imageSerialMart3 = sequencePicRule.imageSerialText3.getText().toString();
            //boolean fixSerialMartStatus1 = sequenceImageMode.fixSerialText1;
            //boolean fixSerialMartStatus2 = sequenceImageMode.fixSerialText2;
            boolean fixSerialMartStatus3 = sequencePicRule.fixSerialText3;
            //序列图1
            byte[] fileByte;
            fileByte = Files.readAllBytes(imgFileObj.toPath());
            // base64 加密
            String carImg1Data = Base64.getEncoder().encodeToString(fileByte);
            argsMap.put(BusinessConstant.CAR_IMG_ONE_DATA, carImg1Data);

            //序列图2
            elements[serialIndex] = imageSerialMart2;
            //拼接图片名
            String imgName2 = String.join(separator, elements) + suffix;
            File imgFileObj2 = new File(this.imageDirObj.getAbsolutePath() + File.separator + imgName2);
            fileByte = Files.readAllBytes(imgFileObj2.toPath());
            // base64 加密
            String carImg2Data = Base64.getEncoder().encodeToString(fileByte);
            argsMap.put(BusinessConstant.CAR_IMG_TWO_DATA, carImg2Data);

            //序列图3(可选)
            if (fixSerialMartStatus3 && !"".equals(imageSerialMart3)) {
                elements[serialIndex] = imageSerialMart3;
                //拼接图片名
                String imgName3 = String.join(separator, elements)  + suffix;
                File imgFileObj3 = new File(this.imageDirObj.getAbsolutePath() + File.separator + imgName3);
                fileByte = Files.readAllBytes(imgFileObj3.toPath());
                // base64 加密
                String carImg3Data = Base64.getEncoder().encodeToString(fileByte);
                argsMap.put(BusinessConstant.CAR_IMG_THREE_DATA, carImg3Data);
            }
        }
        //
        return argsMap;
    }

    /**
     * 数据推送, 放入线程池中执行
     */
    public void pushData(Map<String, Object> argsMap, File imgFileObj, CountDownLatch  countDownLatch){
        //放入线程池执行
        executorService.execute(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                try {
                    HttpClientUtil httpUtil = new HttpClientUtil();
                    httpUtil.addHeader("Content-Type", "application/json");
                    httpUtil.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.116 Safari/537.36");
                    String jsonArgs = JSONObject.toJSONString(argsMap);
                    String responseString = httpUtil.postJson(AIQualityService.instance.accessUrl, jsonArgs);
                    // 解析响应
                    JSONObject jsonRet = JSON.parseObject(responseString);
                    String errorCode = String.valueOf(jsonRet.get("errorCode"));
                    String message = String.valueOf(jsonRet.get("message"));

                    // 推送结果输出到日志区
                    if ("0".equals(errorCode)) {
                        logging.info(String.format("success: %s", imgFileObj.getName()));
                        accessSuccessNum += 1;
                        AccessStats.instance.setAccessSuccessNum(accessSuccessNum);
                    } else if ("308".equals(errorCode)) {
                        // 重复接入
                        logging.error(String.format("repeated: %s", imgFileObj.getName()));
                    } else {
                        // 接入错误
                        logging.error(String.format("failed: %s code:[%s] message:[%s]", imgFileObj.getName(), errorCode, message));
                    }
                } catch (Exception ex) {
                    logging.error(String.format("请求接口异常: [%s]", ex));
                } finally {
                    //更新接入完成数量
                    accessCompleteNum += 1;
                    //更新接入进度设置到显示面板上
                    accessProgress = String.format("%.2f%%", (float) accessCompleteNum / (float) accessTotal * 100);
                    AccessProgress.instance.setAccessProgress(accessProgress);
                    if ("100.00%".equals(accessProgress)) {
                        logging.warning("数据接入完成!");
                        StartButton.instance.enabled(true);
                    }
                    //线程池的任务减1
                    if(countDownLatch.getCount()>0){
                        //System.out.println("执行前任务数" + countDownLatch.getCount());
                        countDownLatch.countDown();
                        //System.out.println("后任务数" + countDownLatch.getCount());
                    }
                }
            }
        });
    }
}
