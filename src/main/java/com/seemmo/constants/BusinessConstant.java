package com.seemmo.constants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kaichenkai
 * @create: 7/6/2020 14:09
 */
public interface BusinessConstant {

    String AI_TRAFFIC_USINESS_MODE = "AI预审";
    String AI_QUALITY_USINESS_MODE = "AI智检";

    /**
     * URL 前缀部分
     */
    String URL_PREFIX = "https://IP:PORT";

    /**
     * URL 后置提示部分
     */
    String URL_SUFFIX = " 【请更改IP和PORT】";

    /**
     * AI预审数据推送地址
     */
    String AITRAFFIC_ACCESS_URL = "/client/instesv/illegallogic";

    /**
     * AI智检数据推送地址
     */
    String AIQUALITY_ACCESS_URL = "/client/instesv/recog/testdata";


    /**
     * 没有对应的业务模式
     */
    String BUSINESS_MODE_ERROR = "没有对应的业务模式";

    /**
     * 分隔符文本内容
     */
    String SEPARATOR = "分隔符";
    /**
     * 自定义分隔符文本内容
     */
    String CUSTOM_SEPARATOR = "自定义分隔符";
    /**
     * 自定义时间格式文本内容
     */
    String CUSTOM_TIME_FORMAT = "自定义时间格式";
    /**
     * 下划线文本
     */
    String UNDERLINE = "下划线 \"_\"";
    /**
     * 美元符号文本
     */
    String DOLLAR_SIGN = "美元符号 \"$\"";
    /**
     * 英文逗号
     */
    String COMMA = "英文逗号 \",\"";
    /**
     * 线程数量
     */
    String THREAD_NUM = "线程数量";

    String SERIAL_INDEX = "序号索引值";

    /**
     * 序列图标识
     */
    String IMAGE_SERIAL_MARK_1 = "01";
    String IMAGE_SERIAL_MARK_2 = "02";
    String IMAGE_SERIAL_MARK_3 = "03";

    /**
     * 未知的图片数据模式
     */
    String UNKNOWN_IMAGE_MODEL = "未知的图片数据模式, 请联系管理员!";

    /**
     * 厂商代码, 行车方向 默认值
     */
    String DEFAULT_MANUFACTURER_CODE = "SEEMMO";
    String DEFAULT_CAR_DIRECT = "9";

    /**
     * 请选择违法
     */
    String PLEASE_SELECT = "请选择";

    /**
     * 配置文件名称
     */
    String CONFIG_FILE_NAME = "configInfo.txt";

    /**
     * 字段名
     */
    //AI智检
    String BATCH_FLAG = "batchFlag";//批量数据标识
    String INSTESV_STATUS = "instesvStatus";//接入状态
    String COMBINED_PIC_TYPE = "combinedPicType";//合成图类型
    String CAR_NUM_IMAGE_INDEX = "featureImageIdx";//车牌图索引
    String RECOG_IMAGE_INDEX = "recogImageIdx";//识别图索引
    //公共字段
    String MANUFACTURER_CODE = "manufacturerCode";//厂商代码
    String RECORD_ID         = "recordId";//源违法记录Id
    String DEVICE_CODE       = "deviceCode";//设备代码
    String CAR_PLATE_NUMBER  = "carPlateNumber";//车牌号码
    String CAR_PLATE_TYPE    = "carPlateType";//车牌类型
    String PLATE_COLOR_CODE  = "plateColorCode";//车牌颜色
    String CAR_COLOR_CODE    = "carColorCode";//车辆颜色
    String CAR_DIRECT        = "carDirect";//行车方向
    String CAR_WAY_CODE      = "carWayCode";//车道号
    String ILLEGAL_CODE      = "illegalCode";//违法类型
    String ILLEGAL_TIME      = "snapshotTime";//违法时间
    String COMBINED_PIC_DATA = "combinedPicData";//合成图片base64字符串
    String CAR_IMG_ONE_DATA  = "carImg1Data";//单张图片1base64字符串
    String CAR_IMG_TWO_DATA  = "carImg2Data";//单张图片2base64字符串
    String CAR_IMG_THREE_DATA= "carImg3Data";//单张图片3base64字符串
    String CAR_IMG_FOUR_DATA = "carImg4Data";//单张图片4base64字符串
    String CAR_IMG_FIVE_DATA = "carImg5Data";//单张图片5base64字符串


//    /**
//     * 标注数据下载存放位置
//     */
//    String MARK_IMAGE = "markimage";
//
//    /**
//     * 标注数据下载存放位置
//     */
//    String MARK_DATA = "markdata";
//
//    /**
//     * 深度学习图片下载存放位置
//     */
//    String ML_IMAGE = "mlimage";
//    /**
//     * 标注数据上传存放位置
//     */
//    String MARK_TEMP_UPLOAD = "marktempupload";
//
//    /**
//     * 预审测试数据下载存放位置
//     */
//    String TRAFFIC_TEST = "traffictest";
//
//    /**
//     * 临时目录
//     */
//    String TMEP_DIR = "data_tmp";
//
//    /**
//     * 标注图 存放目录
//     */
//    String IMAGE_DEVICEMARK = "markimage";
//    /**
//     * 业务图片 存放目录
//     */
//    String IMAGE_AITRAFFIC = "aitraffic";
//
//    /**
//     * 预审测试图片 存放目录
//     */
//    String IMAGE_TRAFFIC_TEST = "traffictest";
//
//    /**
//     * 切图 存放目录
//     */
//    String IMAGE_SPLIT_IMAGE = "splitImage";
//
//    /**
//     * 导出的Excel的默认文件名
//     */
//    String EXPORT_EXCEL_FILENAME = "record.xlsx";
//
//    /**
//     * 文件结尾字符
//     */
//    String LOWER_XLS = "xls";
//    String LOWER_XLSX = "xlsx";
//    String LOWER_SPOTXLS = ".xls";
//    String LOWER_SPOTXLSX = ".xlsx";
//    String LOWER_SPOTZIP = ".zip";
//    String LOWER_SPOTJPG = ".jpg";
//    String LOWER_SPOTPNG = ".png";
//    String LOWER_SPOTJSON = ".json";
//
//    String SEEMMO_MARK = "IUAjJCVeJiooKXNlZW1tbw==";
//
//    /**
//     * 批量插入sql语句时，每次最大处理条数
//     */
//    int SIZE = BaseConstant.CONST200;
//
//    /**
//     * 未知路口
//     */
//    String DEFAULT_ROAD_NAME = "未知路口";
//    /**
//     * 批量导入最大记录条数
//     */
//    int TEST_IMP_MAXCOUNT = BaseConstant.CONST3000;
//
//    /**
//     * 用户密码最小长度
//     */
//    int PASSWORD_MIN_LENGTH = 6;
//    /**
//     * 用户密码最大长度
//     */
//    int PASSWORD_MAX_LENGTH = 15;
//
//    String WEB_ICON = "iconUrl";
//
//    String WEB_ICON_PRE = "data:image/jpeg;base64,";
//
//    String WEB_ICON_PRE_KEY = ";base64,";
//
//    String WEB_ICON_SPLIT = "##";
//
//    String FIELD_KEY_PROJECT_ID = "projectId";
//    String FIELD_KEY_MANUFACTURERCODE = "manufacturerCode";
//
//    String EXPORT_DEVICE_MARK_DEVICES = "devices";
//    String EXPORT_DEVICE_MARK_MARKS = "device_marks";
//    String EXPORT_DEVICE_MARK_ILLEAGALS = "device_illegals";
//    String EXPORT_DEVICE_MARK_MARKCONFIGS = "device_markconfig";
//    String EXPORT_DEVICE_MARK_MARKRELAS = "device_markrelas";
//
//    /**
//     * 中横杠常量
//     */
//    String EXPORT_SPLIT = "_";
//    String EXPORT_PREFIXX = "export_";
//    String EXPORT_AITEST_NAME = "aitest";
//    String EXPORT_SAMPLE_NAME = "sample";
//    String EXPORT_AITEST_LABEL = "aitest_label";
//
//
//    String INTERCEPTOR_WEBLOG = "WebLogInterceptor";
//    String INTERCEPTOR_HANDLER = "HandlerInterceptorAdapter";
//
//    int MAX_THREAD_COUNT = BaseConstant.CONST10;
//    /**
//     * 数据目录basedir
//     */
//    String DATABASEDIR = "/data/";
//
//    /**
//     * 道路线
//     * 白实线 WSL
//     * 黄实线 YSL
//     * WLVRSL 白线-左虚右实线
//     * YLVRSL 黄线-左虚右实线
//     * WLSRVL 白线-左实右虚线
//     * YLSRVL 黄线-左实右虚线
//     */
//    String[] DLX_VALUE = {"WSL", "YSL", "WLVRSL", "YLVRSL", "WLSRVL", "YLSRVL"};
//
//    List<String> DLX_VALUE_LIST = Arrays.asList(DLX_VALUE);
//
//
//    String KEY_REQUEST_COREPARAMS = "to_be_updated";
//    String KEY_RESPONSE_COREPARAMS = "updated_param";
}
