package com.taotao.common.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.bean.HttpResult;

@Service
public class ApiService {

    @Autowired(required = false)
    private CloseableHttpClient httpclient;

    @Autowired(required = false)
    private RequestConfig requestConfig;

    /**
     * 没有参数据的Get请求
     * 
     * @param url 请求网址
     * @return 如果返回状是200，返回网页内容，反之，返回null
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url) throws ClientProtocolException, IOException {
        // 创建http GET请求
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }

        return null;
    }

    /**
     * 带有参数的doget请求
     * 
     * @param url 请求的网址
     * @param params 请求网址的参数
     * @return 如果返回状是200，返回网页内容，反之，返回null
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public String doGet(String url, Map<String, Object> params) throws URISyntaxException,
            ClientProtocolException, IOException {
        // 定义请求的参数
        URIBuilder uriBuilder = new URIBuilder(url);

        if (!params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
            }
        }
        return doGet(uriBuilder.build().toString());
    }

    /**
     * 带有参数的doPost请求
     * 
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResult doPost(String url, Map<String, Object> params) throws ClientProtocolException,
            IOException {

        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求参数
        httpPost.setConfig(requestConfig);
        // 设置表单值
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        if (!params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

        }
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                        response.getEntity(), "UTF-8"));
            }

        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new HttpResult(response.getStatusLine().getStatusCode(), null);
    }

    /**
     * 没有带参数doPost请
     * 
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResult doPost(String url) throws ClientProtocolException, IOException {
        return doPost(url, null);
    }

    /**
     * 带有参数的doPost请求
     * 
     * @param url
     * @param params
     * @return
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResult dojsonPost(String url, String json) throws ClientProtocolException, IOException {

        // 创建http POST请求
        HttpPost httpPost = new HttpPost(url);
        // 设置请求参数
        httpPost.setConfig(requestConfig);

        // 构造一个String表单式的实体
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(stringEntity);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                        response.getEntity(), "UTF-8"));
            }

        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new HttpResult(response.getStatusLine().getStatusCode(), null);
    }

    /**
     * 没有带参数doput
     */
    public HttpResult doPut(String url) throws ClientProtocolException,
            IOException {

        // 创建http PUT请求
        HttpPut httpPut = new HttpPut(url);
        // 设置请求参数
        httpPut.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPut);
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                        response.getEntity(), "UTF-8"));
            }

        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new HttpResult(response.getStatusLine().getStatusCode(), null);
    }
    
    /**
     * 没有带参数doput
     */
    public HttpResult doDelete(String url) throws ClientProtocolException,
            IOException {

        // 创建http PUT请求
        HttpDelete httpDelete = new HttpDelete(url);
        // 设置请求参数
        httpDelete.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpDelete);
            if (response.getEntity() != null) {
                return new HttpResult(response.getStatusLine().getStatusCode(), EntityUtils.toString(
                        response.getEntity(), "UTF-8"));
            }

        } finally {
            if (response != null) {
                response.close();
            }
        }
        return new HttpResult(response.getStatusLine().getStatusCode(), null);
    }
}
