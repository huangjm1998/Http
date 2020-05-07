//package com.zz.common.socket.util;
//
//import org.apache.commons.io.IOUtils;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.NameValuePair;
//import org.apache.http.ParseException;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.*;
//import org.apache.http.client.utils.URLEncodedUtils;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.apache.http.util.TextUtils;
//import org.easypay.core.common.util.MyLog;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.io.*;
//import java.net.HttpURLConnection;
//import java.net.URI;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Created by dell on 2015/1/13.
// */
//public class HttpClientUtil {
//    private static final MyLog _log = MyLog.getLog(HttpClientUtil.class);
//    //发送JSON字符串 如果成功则返回成功标识。
//    public static String doJsonPost(String urlPath, String Json) {
//        // HttpClient 6.0被抛弃了
//        String result = "";
//        BufferedReader reader = null;
//        try {
//            URL url = new URL(urlPath);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("POST");
//            conn.setDoOutput(true);
//            conn.setDoInput(true);
//            conn.setUseCaches(false);
//            conn.setRequestProperty("Connection", "Keep-Alive");
//            conn.setRequestProperty("Charset", "UTF-8");
//            // 设置文件类型:
//            conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
//            // 设置接收类型否则返回415错误
//            //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
//            conn.setRequestProperty("accept","application/json");
//            // 往服务器里面发送数据
//            if (Json != null && !TextUtils.isEmpty(Json)) {
//                byte[] writebytes = Json.getBytes();
//                // 设置文件长度
//                conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
//                OutputStream outwritestream = conn.getOutputStream();
//                outwritestream.write(Json.getBytes());
//                outwritestream.flush();
//                outwritestream.close();
//                System.out.println("doJsonPost: conn  "+conn.getResponseCode());
//                //Log.d("hlhupload", "doJsonPost: conn"+conn.getResponseCode());
//            }
//            if (conn.getResponseCode() == 200) {
//                reader = new BufferedReader(
//                        new InputStreamReader(conn.getInputStream()));
//                result = reader.readLine();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return result;
//    }
//
//
//    /**
//     * 封装HTTP POST方法
//     * @param
//     * @param
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String post(String url, Map paramMap) throws ClientProtocolException, IOException {
//        HttpClient httpClient = new DefaultHttpClient();
//        /** 忽略SSL证书校验*/
//        try {
//            //Secure Protocol implementation.
//            SSLContext ctx = SSLContext.getInstance("SSL");
//            //Implementation of a trust manager for X509 certificates
//            X509TrustManager tm = new X509TrustManager() {
//                public void checkClientTrusted(X509Certificate[] xcs,
//                        String string) throws CertificateException {
//                }
//                public void checkServerTrusted(X509Certificate[] xcs,
//                        String string) throws CertificateException {
//                }
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//            };
//            ctx.init(null, new TrustManager[] { tm }, null);
//            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
//            ClientConnectionManager ccm = httpClient.getConnectionManager();
//            //register https protocol in httpclient's scheme registry
//            SchemeRegistry sr = ccm.getSchemeRegistry();
//            sr.register(new Scheme("https", 443, ssf));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        HttpPost httpPost = new HttpPost(url);
//        List<NameValuePair> formparams = setHttpParams(paramMap);
//        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, HTTP.UTF_8);
//        httpPost.setEntity(param);
//        HttpResponse response = httpClient.execute(httpPost);
//        String httpEntityContent = getHttpEntityContent(response);
//        httpPost.abort();
//        return httpEntityContent;
//    }
//
//    /**
//     * 封装HTTP POST方法
//     * @param
//     * @param （如JSON串）
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String post(String url, String data) throws ClientProtocolException, IOException {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPost httpPost = new HttpPost(url);
//        httpPost.setHeader("Content-Type","text/json; charset=utf-8");
//        httpPost.setEntity(new StringEntity(URLEncoder.encode(data, "UTF-8")));
//        HttpResponse response = httpClient.execute(httpPost);
//        String httpEntityContent = getHttpEntityContent(response);
//        httpPost.abort();
//        return httpEntityContent;
//    }
//
//    /**
//     * 封装HTTP GET方法
//     * @param
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String get(String url) throws ClientProtocolException, IOException {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet();
//        httpGet.setURI(URI.create(url));
//        HttpResponse response = httpClient.execute(httpGet);
//        String httpEntityContent = getHttpEntityContent(response);
//        httpGet.abort();
//        return httpEntityContent;
//    }
//
//    /**
//     * 封装HTTP GET方法
//     * @param
//     * @param
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String get(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet();
//        List<NameValuePair> formparams = setHttpParams(paramMap);
//        String param = URLEncodedUtils.format(formparams, "UTF-8");
//        System.out.println(URI.create(url + "?" + param));
//        httpGet.setURI(URI.create(url + "?" + param));
//        HttpResponse response = httpClient.execute(httpGet);
//        String httpEntityContent = getHttpEntityContent(response);
//        httpGet.abort();
//        return httpEntityContent;
//    }
//
//    /**
//     * 封装HTTP PUT方法
//     * @param
//     * @param
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String put(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpPut httpPut = new HttpPut(url);
//        List<NameValuePair> formparams = setHttpParams(paramMap);
//        UrlEncodedFormEntity param = new UrlEncodedFormEntity(formparams, "UTF-8");
//        httpPut.setEntity(param);
//        HttpResponse response = httpClient.execute(httpPut);
//        String httpEntityContent = getHttpEntityContent(response);
//        httpPut.abort();
//        return httpEntityContent;
//    }
//
//    /**
//     * 封装HTTP DELETE方法
//     * @param
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String delete(String url) throws ClientProtocolException, IOException {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpDelete httpDelete= new HttpDelete();
//        httpDelete.setURI(URI.create(url));
//        HttpResponse response = httpClient.execute(httpDelete);
//        String httpEntityContent = getHttpEntityContent(response);
//        httpDelete.abort();
//        return httpEntityContent;
//    }
//
//    /**
//     * 封装HTTP DELETE方法
//     * @param
//     * @param
//     * @return
//     * @throws ClientProtocolException
//     * @throws IOException
//     */
//    public static String delete(String url, Map<String, String> paramMap) throws ClientProtocolException, IOException {
//        HttpClient httpClient = new DefaultHttpClient();
//        HttpDelete httpDelete = new HttpDelete();
//        List<NameValuePair> formparams = setHttpParams(paramMap);
//        String param = URLEncodedUtils.format(formparams, "UTF-8");
//        httpDelete.setURI(URI.create(url + "?" + param));
//        HttpResponse response = httpClient.execute(httpDelete);
//        String httpEntityContent = getHttpEntityContent(response);
//        httpDelete.abort();
//        return httpEntityContent;
//    }
//
//
//    /**
//     * 设置请求参数
//     * @param
//     * @return
//     */
//    private static List<NameValuePair> setHttpParams(Map<String, String> paramMap) {
//        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//        Set<Map.Entry<String, String>> set = paramMap.entrySet();
//        for (Map.Entry<String, String> entry : set) {
//            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//        }
//        return formparams;
//    }
//
//    /**
//     * 获得响应HTTP实体内容
//     * @param response
//     * @return
//     * @throws IOException
//     * @throws UnsupportedEncodingException
//     */
//    private static String getHttpEntityContent(HttpResponse response) throws IOException, UnsupportedEncodingException {
//        HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            InputStream is = entity.getContent();
//            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
//            String line = br.readLine();
//            StringBuilder sb = new StringBuilder();
//            while (line != null) {
//                sb.append(line + "\n");
//                line = br.readLine();
//            }
//            return sb.toString();
//        }
//        return "";
//    }
//
//    /**
//     * 模拟请求
//     *
//     * @param url        资源地址
//     * @param map    参数列表
//     * @param encoding    编码
//     * @return
//     * @throws ParseException
//     * @throws IOException
//     */
//    public static String sendHttpPost(String url, Map<String,String> map,String encoding) throws ParseException, IOException{
//        String body = "";
//
//        //创建httpclient对象
//        CloseableHttpClient client = HttpClients.createDefault();
//        //创建post方式请求对象
//        HttpPost httpPost = new HttpPost(url);
//
//        //装填参数
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        if(map!=null){
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
//            }
//        }
//        //设置参数到请求对象中
//        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));
//
//        System.out.println("请求地址："+url);
//        System.out.println("请求参数："+nvps.toString());
//
//        //设置header信息
//        //指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
//        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
//
//        //执行请求操作，并拿到结果（同步阻塞）
//        CloseableHttpResponse response = client.execute(httpPost);
//        //获取结果实体
//        HttpEntity entity = response.getEntity();
//        if (entity != null) {
//            //按指定编码转换结果实体为String类型
//            body = EntityUtils.toString(entity, encoding);
//        }
//        EntityUtils.consume(entity);
//        //释放链接
//        response.close();
//        return body;
//    }
//
//    /*
//	 * map转换为url
//	 * */
//	public static String sendPostRequest(String url, Map params) throws Exception {
//		URL u = null;
//		HttpURLConnection con = null;
//
//		// 构建请求参数
//		String sendString = MapUtils.mapToUrlParam(params);
//		// 尝试发送请求
//		try {
//			u = new URL(url);
//			con = (HttpURLConnection) u.openConnection();
//			con.setRequestMethod("POST");
//			con.setDoOutput(true);
//			con.setDoInput(true);
//			con.setUseCaches(false);
//			con.setRequestProperty("Content-Type",
//					"application/x-www-form-urlencoded");
//			OutputStreamWriter osw = new OutputStreamWriter(con
//					.getOutputStream(), "UTF-8");
//			osw.write(sendString);
//			osw.flush();
//			osw.close();
//		} catch (Exception e) {
//            _log.error(e,"与服务器连接发生错误");
//			throw new Exception("与服务器连接发生错误");
//		} finally {
//			if (con != null) {
//				con.disconnect();
//			}
//		}
//		// 读取返回内容
//		StringBuffer buffer = new StringBuffer();
//		try {
//			BufferedReader br = new BufferedReader(new InputStreamReader(con
//			.getInputStream(), "UTF-8"));
//			String temp;
//			while ((temp = br.readLine()) != null) {
//				buffer.append(temp);
//			}
//		} catch (Exception e) {
//            _log.error(e,"从服务器获取数据失败");
//			throw new Exception("从服务器获取数据失败");
//		}
//		return buffer.toString();
//	}
//
//    public static byte[] postBinResource(String urlStr, String request, String encode,int timeOutInSeconds) throws Exception {
//        HttpURLConnection http = null;
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//
//        try {
//            URL url = new URL(urlStr);
//            http = (HttpURLConnection)url.openConnection();
//            http.setDoInput(true);
//            http.setDoOutput(true);
//            http.setUseCaches(false);
//            http.setConnectTimeout(timeOutInSeconds*1000);//设置连接超时
//            //如果在建立连接之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
//            http.setReadTimeout(timeOutInSeconds*1000);//设置读取超时
//            //如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。超时时间为零表示无穷大超时。
//            http.setRequestMethod("POST");
//            http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset="+encode);
//            http.connect();
//
//            outputStream = http.getOutputStream();
//            OutputStreamWriter osw = new OutputStreamWriter(outputStream, encode);
//            osw.write(request);
//            osw.flush();
//            osw.close();
//
//            if (http.getResponseCode() == 200) {
//                inputStream = http.getInputStream();
//                byte[] returnValue1 = IOUtils.toByteArray(inputStream);
//                return returnValue1;
//            }else{
//                throw new RuntimeException("http read ["+http.getResponseCode()+"]");
//            }
//        } catch (Exception e) {
//            throw e;
//        } finally {
//            if (http != null) http.disconnect();
//            IOUtils.closeQuietly(inputStream);
//            IOUtils.closeQuietly(outputStream);
//        }
//    }
//
//}
