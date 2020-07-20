package com.seemmo.service;

import com.seemmo.constants.BaseConstant;
import com.seemmo.constants.BusinessConstant;
import com.seemmo.gui.commons.accessField.*;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author: kaichenkai
 * @create: 7/7/2020 19:19
 */
public class AITrafficTestService extends BaseService{
    //单例
    private AITrafficTestService() {
    }//私有化构造方法使得该类无法在外部通过new 进行实例化
    public static AITrafficTestService instance = new AITrafficTestService();//准备一个类属性，指向一个实例化对象。 因为是类属性，所以只有一个

    @Override
    public boolean run() {
        //接入初始化
        boolean checkStatus = this.initializationCheck();
        if (!checkStatus){
            return false;
        }

        //遍历图片文件夹中的图片
        for (final File imgFileObj : this.imageObjList) {
            logging.warning(String.format("字段匹配验证: %s", imgFileObj.getName()));
            final Map<String, Object> argsMap;
            try {
                argsMap = dataMapping(imgFileObj);
            }
            catch (Exception ex) {
                //logging.error(String.format("数据映射错误: %s, 错误: %s", imgFileObj.getName(), ex));
                logging.error(String.format("error: %s", ex));
                //映射错误停止执行
                return false;
            }
            if (argsMap == null) {//规则设置有误, 停止执行
                return false;
            }
            //换行输出
            logging.debug("\n");
        }
        return true;
    }

    /**
     * 数据处理: dataMapping
     */
    public Map<String, Object> dataMapping(File imgFileObj) throws Exception {
        Map<String, Object> argsMap = new HashMap<>();
        //
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
        logging.debug("厂商编码" + "\t\t" + manufacturerCode);

        //违法记录ID
        boolean checkBoxValue = RecordId.instance.getCheckBoxValue();
        int recordIndex;
        String recordId;
        if (checkBoxValue) {
            recordId = UUID.randomUUID().toString();
        } else if ((recordIndex = RecordId.instance.getIndex()) >= 0) {
            recordId = elements[recordIndex];
            if (recordId.length() > BaseConstant.CONST100) {
                logging.error("违法记录ID内容超出限制(100)");
                return null;
            }
        } else {
            logging.error("请设置字段匹配规则：违法记录ID");
            return null;
        }
        argsMap.put(BusinessConstant.RECORD_ID, recordId);
        logging.debug("违法记录ID" + "\t\t" + recordId);

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
        logging.debug("设备编号" + "\t\t" + deviceCode);

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
        logging.debug("车牌号码" + "\t\t" + carPlateNumber);

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
        logging.debug("车牌类型代码" + "\t" + carPlateType);

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
        logging.debug("车牌颜色代码" + "\t" + plateColorCode);

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
        logging.debug("车辆颜色代码" + "\t" + carColorCode);

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
        logging.debug("行车方向代码" + "\t" + carDirect);

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
        logging.debug("车道号" + "\t\t" + carWayCode);

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
        logging.debug("违法类型代码" + "\t" + illegalCode);

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
        logging.debug("违法时间" + "\t\t" + illegalTime);

        //图片数据
        //1.合成图
        if (imageDataMode.compositeModeText.isSelected()) {
            byte[] fileByte;
            fileByte = Files.readAllBytes(imgFileObj.toPath());
            // base64 加密
            String combinedPicData = Base64.getEncoder().encodeToString(fileByte);
            argsMap.put(BusinessConstant.COMBINED_PIC_DATA, combinedPicData);
            logging.debug("合成图数据" + "\t\t" + "base64数据");
        }

        //2.序列图
        if (imageDataMode.sequenceModeText.isSelected()) {
            //序列图索引值, 序号标识, 修改标记
            int serialIndex = sequencePicRule.getSerialIndex();
//            String imageSerialMart1 = sequenceImageMode.imageSerialText1.getText().toString();
            String imageSerialMart2 = sequencePicRule.imageSerialText2.getText().toString();
            String imageSerialMart3 = sequencePicRule.imageSerialText3.getText().toString();
//            boolean fixSerialMartStatus1 = sequenceImageMode.fixSerialText1;
//            boolean fixSerialMartStatus2 = sequenceImageMode.fixSerialText2;
            boolean fixSerialMartStatus3 = sequencePicRule.fixSerialText3;
            //序列图1
            byte[] fileByte;
            fileByte = Files.readAllBytes(imgFileObj.toPath());
            // base64 加密
            String carImg1Data = Base64.getEncoder().encodeToString(fileByte);
            argsMap.put(BusinessConstant.CAR_IMG_ONE_DATA, carImg1Data);
            logging.debug("序列图数据1" + "\t" + "base64数据");

            //序列图2
            elements[serialIndex] = imageSerialMart2;
            //拼接图片名
            String imgName2 = String.join(separator, elements) + suffix;
            File imgFileObj2 = new File(this.imageDirObj.getAbsolutePath() + File.separator + imgName2);
            fileByte = Files.readAllBytes(imgFileObj2.toPath());
            // base64 加密
            String carImg2Data = Base64.getEncoder().encodeToString(fileByte);
            argsMap.put(BusinessConstant.CAR_IMG_TWO_DATA, carImg2Data);
            logging.debug("序列图数据2" + "\t" + "base64数据");

            //序列图3(可选)
            if (fixSerialMartStatus3 && !"".equals(imageSerialMart3)) {
                elements[serialIndex] = imageSerialMart3;
                //拼接图片名
                String imgName3 = String.join(separator, elements) + suffix;
                File imgFileObj3 = new File(this.imageDirObj.getAbsolutePath() + File.separator + imgName3);
                fileByte = Files.readAllBytes(imgFileObj3.toPath());
                // base64 加密
                String carImg3Data = Base64.getEncoder().encodeToString(fileByte);
                argsMap.put(BusinessConstant.CAR_IMG_THREE_DATA, carImg3Data);
                logging.debug("序列图数据3" + "\t" + "base64数据");
            }
        }
        //
        return argsMap;
    }
}
