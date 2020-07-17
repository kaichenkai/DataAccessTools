package com.seemmo.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

//import com.seemmo.aitraffic.constant.BaseConstant;
//import org.apache.commons.codec.CharEncoding;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * HttpClientUtil
 * 增加方法post 文本
 */
public class HttpClientUtil {

//    private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

//    public static final Charset UTF_8 = Charset.forName(CharEncoding.UTF_8);
    public static final Charset UTF_8 = Charset.forName("UTF-8");
    /**
     * 连接超时时间
     * 10s
     */
    private static final int CONNECT_TIMEOUT = 10000;

    /**
     * 请求超时时间
     * 10s
     */
    private static final int CONNECT_REQUEST_TIMEOUT = 10000;

    /**
     * socket超时时间
     * 10s
     */
//    private static final int SOCKET_TIMEOUT = 10000;
    private static final int SOCKET_TIMEOUT = 5000;

    /**
     * 连接超时时间
     * 10s
     */
    private int connectTimeout = CONNECT_TIMEOUT;

    /**
     * 请求超时时间
     * 10s
     */
    private int connectionRequestTimeout = CONNECT_REQUEST_TIMEOUT;

    /**
     * socket超时时间
     * 10s
     */
    private int socketTimeout = SOCKET_TIMEOUT;

    private List<Header> headers;

    private static com.seemmo.utils.HttpClientUtil instance = new com.seemmo.utils.HttpClientUtil();

    public static com.seemmo.utils.HttpClientUtil getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new com.seemmo.utils.HttpClientUtil();
                }
            }
        }
        return instance;
    }


    /**
     * 获取HttpClient
     * 并配置初始化参数
     *
     * @return
     */
    private CloseableHttpClient getHttpClient() {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    /**
     * 获取HttpClient
     * 并配置初始化参数
     *
     * @return
     */
    private CloseableHttpClient getHttpClient(int timeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(timeout)
                .setConnectTimeout(timeout * 2)
                .setConnectionRequestTimeout(timeout * 4).build();
        CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig).build();
        return httpClient;
    }

    /**
     * 获取SSLHttpClient
     *
     * @return
     */
    private CloseableHttpClient getSSLHttpClient() {
        try {
            //设置超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout)
                    .setConnectionRequestTimeout(connectionRequestTimeout)
                    .setSocketTimeout(socketTimeout).build();
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                //信任所有
                public boolean isTrusted(X509Certificate[] chain,
                                         String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslContext,
                    NoopHostnameVerifier.INSTANCE);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setSSLSocketFactory(sslsf)
                    .build();
            return httpClient;
        } catch (NoSuchAlgorithmException e) {
//            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (KeyManagementException e) {
//            logger.error(e.getMessage(), e);
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
//            logger.error(e.getMessage(), e);
        }
        return HttpClients.createDefault();
    }

    /**
     * 执行post或者get请求
     *
     * @param url
     * @param encoding
     * @param httpUriRequest
     * @param httpClient
     * @return
     * @throws Exception
     */
    private String execute(String url, String encoding,
                           HttpUriRequest httpUriRequest, CloseableHttpClient httpClient)
            throws Exception {
        CloseableHttpResponse response = null;
        try {
            if (headers != null) {
                httpUriRequest.setHeaders(headers.toArray(new Header[]{}));
            }
            response = httpClient.execute(httpUriRequest);
            if (response != null) {
                // 请求发送成功，并得到响应
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, encoding);
                return result;
            }
        } finally {
            if (response != null) {
                response.close();
            }
            if (httpClient != null) {
                httpClient.close();
            }
        }
//        return BaseConstant.EMPTYSTR;
        return "";
    }

    /**
     * http get或者https get
     * 通用方法
     *
     * @param url
     * @param encoding
     * @param httpClient
     * @return
     * @throws Exception
     */
    private String get(String url, String encoding,
                       CloseableHttpClient httpClient) throws Exception {
        HttpGet httpget = new HttpGet(url);
        return execute(url, encoding, httpget, httpClient);
    }

    /**
     * post
     *
     * @param url
     * @param nvps
     * @param encoding
     * @param httpClient
     * @return
     * @throws Exception
     */
    private String post(String url, List<NameValuePair> nvps,
                        String encoding, CloseableHttpClient httpClient) throws Exception {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
        return execute(url, encoding, httpPost, httpClient);
    }


    /**
     * http get请求
     * 默认编码为utf-8
     *
     * @param url
     * @param encoding 编码
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public String get(String url, String encoding) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        return get(url, encoding, httpClient);
    }


    /**
     * http get请求
     * 默认utf-8编码
     *
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String get(String url) throws Exception {
        return get(url, UTF_8.name());
    }

    /**
     * ssl get
     *
     * @param url
     * @param encoding
     * @return
     * @throws Exception
     */
    public String getSSL(String url, String encoding) throws Exception {
        CloseableHttpClient httpClient = getSSLHttpClient();
        return get(url, encoding, httpClient);
    }

    /**
     * ssl get
     * 默认utf-8编码
     *
     * @param url
     * @return
     * @throws Exception
     */
    public String getSSL(String url) throws Exception {
        return getSSL(url, UTF_8.name());
    }


    /**
     * post 请求
     * List <NameValuePair> nvps = new ArrayList <NameValuePair>();
     * nvps.add(new BasicNameValuePair("user.userName", "哈哈"));
     * HttpClientUtil.doPost("http://localhost:8888/login/login!login.ac",nvps,"utf-8");
     *
     * @param url
     * @param nvps
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String post(String url, List<NameValuePair> nvps,
                       String encoding) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        return post(url, nvps, encoding, httpClient);
    }

    /**
     * post 请求
     * <pre>
     * List <NameValuePair> nvps = new ArrayList <NameValuePair>();
     * nvps.add(new BasicNameValuePair("user.userName", "哈哈"));
     * HttpClientUtil.doPost("http://localhost:8888/login/login!login.ac",nvps);
     * </pre>
     *
     * @param url
     * @param nvp
     * @return
     * @throws Exception
     */
    public String post(String url, List<NameValuePair> nvp)
            throws Exception {
        return post(url, nvp, UTF_8.name());
    }

    /**
     * post文本
     * 默认编码UTF_8
     *
     * @param url
     * @param content
     * @return
     * @throws Exception
     * @date 2015年7月17日
     */
    public String post(String url, String content)
            throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);
        StringEntity se = new StringEntity(content, UTF_8);
        se.setContentEncoding(UTF_8.name());
        httpPost.setEntity(se);
        return execute(url, UTF_8.name(), httpPost, httpClient);
    }

    /**
     * post请求传输json参数
     *
     * @param url     url地址
     * @param content json格式字符串
     * @return 请求获取的结果
     */
//    public String postJson(String url, String content, int timeout) throws Exception {
    public String postJson(String url, String content) throws Exception {
        // post请求返回结果
//        CloseableHttpClient httpClient = getHttpClient(timeout);
        CloseableHttpClient httpClient = getSSLHttpClient();
        HttpPost httpPost = new HttpPost(url);
        StringEntity entity = new StringEntity(content, UTF_8);
        entity.setContentEncoding(UTF_8.name());
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        return execute(url, UTF_8.name(), httpPost, httpClient);
    }

    /**
     * ssl post 请求
     * <pre>
     * List <NameValuePair> nvps = new ArrayList <NameValuePair>();
     * nvps.add(new BasicNameValuePair("user.userName", "哈哈"));
     * HttpClientUtil.doPost("http://localhost:8888/login/login!login.ac",nvps,"utf-8");
     * </pre>
     *
     * @param url
     * @param nvps
     * @param encoding
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String postSSL(String url, List<NameValuePair> nvps,
                          String encoding) throws Exception {
        CloseableHttpClient httpClient = getSSLHttpClient();
        return post(url, nvps, encoding, httpClient);
    }

    /**
     * ssl post 请求
     * <pre>
     * List <NameValuePair> nvps = new ArrayList <NameValuePair>();
     * nvps.add(new BasicNameValuePair("user.userName", "哈哈"));
     * HttpClientUtil.doPost("http://localhost:8888/login/login!login.ac",nvps,"utf-8");
     * </pre>
     *
     * @param url
     * @param nvps
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String postSSL(String url, List<NameValuePair> nvps) throws Exception {
        return postSSL(url, nvps, UTF_8.name());
    }

    /**
     * get 下载文件
     *
     * @param url
     * @return
     * @throws Exception
     */
    public byte[] getFile(String url) throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(url);
            if (headers != null) {
                httpget.setHeaders(headers.toArray(new Header[]{}));
            }
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toByteArray(entity);
        } finally {
            response.close();
            httpClient.close();
        }
    }

    /**
     * get 下载文件
     *
     * @param url
     * @return
     * @throws Exception
     */
    public byte[] getFileSSL(String url) throws Exception {
        CloseableHttpClient httpClient = getSSLHttpClient();
        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(url);

            if (headers != null) {
                httpget.setHeaders(headers.toArray(new Header[]{}));
            }
            response = httpClient.execute(httpget);
            HttpEntity entity = response.getEntity();
            return EntityUtils.toByteArray(entity);
        } finally {
            response.close();
            httpClient.close();
        }
    }

    /**
     * post上传文件
     *
//     * @param url
//     * @param fileName
//     * @param file
     * @return
     * @throws Exception
     */
//    public String postFile(String url, String fileName, File file) throws Exception {
//        CloseableHttpClient httpClient = getHttpClient();
//        HttpPost post = new HttpPost(url);
//        FileBody bin = new FileBody(file);
//        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//        multipartEntityBuilder.addPart(fileName, bin);
//        HttpEntity multiEntity = multipartEntityBuilder.build();
//        post.setEntity(multiEntity);
//        return execute(url, UTF_8.name(), post, httpClient);
//    }


    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public void setConnectionRequestTimeout(int connectionRequestTimeout) {
        this.connectionRequestTimeout = connectionRequestTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public void setHeaders(List<Header> headers) {
        this.headers = headers;
    }

    public void addHeader(String name, String value) {
        Header header = new BasicHeader(name, value);
        if (headers == null) {
            headers = new ArrayList<Header>();
        }
        headers.add(header);
    }
}
