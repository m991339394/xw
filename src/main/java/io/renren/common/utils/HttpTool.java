package io.renren.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * @ClassName HttpTool
 * @Description TODO
 * @Author jgl
 * @Date 2019/12/6 15:03
 * @Version 1.0
 */
public class HttpTool {

    public static String sendPostJSON(String url, String jsonStr) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setUseCaches(false);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("user-agent",
                    "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2376.0 Safari/537.36");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            OutputStreamWriter outs = new OutputStreamWriter(
                    conn.getOutputStream(), "utf-8");
            out = new PrintWriter(outs);
            // 发送请求参数
            out.print(jsonStr);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


    public static JSONObject doGetStr(String url) throws ClientProtocolException, IOException {
        //DefaultHttpClient client = new DefaultHttpClient();
        //DefaultHttpClient is deprecated 【Api弃用]】
        //DefaultHttpClient —> CloseableHttpClient
        //HttpResponse —> CloseableHttpResponse
        CloseableHttpClient client = HttpClientBuilder.create().build();//获取DefaultHttpClient请求
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        //HttpResponse httpResponse = client.execute(httpGet);
        CloseableHttpResponse httpResponse = client.execute(httpGet);
        try {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } finally {
            httpResponse.close();
        }

        return jsonObject;
    }



    /**
     * 封装HTTP POST方法
     * @param
     * @param
     * @return
     * @throws ClientProtocolException
     * @throws java.io.IOException
     */
    public static JSONObject postStringJson(String url, String json) throws ClientProtocolException, IOException  {
        CloseableHttpClient  httpClient = HttpClientBuilder.create().build();//获取DefaultHttpClient请求
        //HttpClient httpClient = new DefaultHttpClient();

        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json");

        //通过setEntity()设置参数给post
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON));

        JSONObject jsonObject = null;
        //利用httpClient的execute()方法发送请求并且获取返回参数
        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        try {
            HttpEntity entity = httpResponse.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } finally {
            httpResponse.close();
        }
        return jsonObject;
    }

}
