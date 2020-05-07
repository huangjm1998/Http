package com.zz.common.socket;


import com.zz.sys.pojo.BaoWen;
import com.zz.common.socket.util.httpUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * @author hjm
 */
@Component
@Configuration
@Service
public class ScoketImpl extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().localAddress().toString() + " 通道已注册！");

    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println(ctx.channel().localAddress().toString() + " 通道已断开！！！");


    }


    public static String getMessage(ByteBuf buf) {

        byte[] con = new byte[buf.readableBytes()];

        ByteBuf readBytes = buf.readBytes(con);
        String s1 = ByteBufUtil.hexDump(con);
        byte[] bytes = s1.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            System.out.print(bytes[i] + "...");
        }
        System.out.println();
        return s1;

    }


    @Resource
    private MongoTemplate mongoTemplate;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        String s2 = format.format(date);

        // 接收字符串时的处理
        ByteBuf buf = (ByteBuf) msg;
        String rev = getMessage(buf);
        String s = URLEncoder.encode(rev, "gbk");
        String s1 = s.toLowerCase();
        BaoWen baoWen = new BaoWen();
        baoWen.setStr(s1);
        // mongoTemplate.save(s1);
        httpUtil.doGet("http://hjm:12334/BaoWen/jiexi", baoWen);

        //  System.out.println(s1);
        DateFormat df3 = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
        DateFormat df7 = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.CHINA);
        String date3 = df3.format(new Date());
        String time3 = df7.format(new Date());

        System.out.println("客户端收到服务器数据:" + s1 + "---时间：" + date3 + " " + time3);

        String substring = s1.substring(20, 22);
        System.out.println(substring);
        switch (substring) {
            case "2f":
                //解析报文
                System.out.println("这是心跳报");
                baoWen.setType("心跳报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "30":
                System.out.println("这是测试报");
                baoWen.setType("测试报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "34":
                System.out.println("这是小时时报！");
                baoWen.setType("小时报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "33":
                System.out.println(s1.length() + "加报");
                System.out.println("这是加报！");
                baoWen.setType("加报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "32":
                System.out.println("这是定时报");
                baoWen.setType("定时报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "e0":
                System.out.println("这是开机报");
                baoWen.setType("开机报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "e3":
                System.out.println("这是图像加报");
                baoWen.setType("图像加报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "e6":
                System.out.println("这是图像定时报");
                baoWen.setType("图像定时报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "f1":
                System.out.println("远程视频状态");
                baoWen.setType("远程视频状态报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "f2":
                System.out.println("自检每日报");
                baoWen.setType("自检每日报");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "37":
                System.out.println("中心站查询遥测站实时数据");
                baoWen.setType("中心站查询遥测站实时数据");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "40":
                System.out.println("中心站修改遥测站基本配置表");
                baoWen.setType("中心站修改遥测站基本配置表");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "41":
                System.out.println("中心站读取遥测站基本配置表");
                baoWen.setType("中心站读取遥测站基本配置表");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "43":
                System.out.println("中心站读取遥测站运行参数配置表");
                baoWen.setType("中心站读取遥测站运行参数配置表");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "42":
                System.out.println("中心站修改遥测站运行参数配置表");
                baoWen.setType("中心站修改遥测站运行参数配置表");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "47":
                System.out.println("初始化固态存储数据");
                baoWen.setType("初始化固态存储数据");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "4a":
                System.out.println("设置遥测站时钟");
                baoWen.setType("设置遥测站时钟");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "38":
                System.out.println("中心站查询遥测站时段数据");
                baoWen.setType("中心站查询遥测站时段数据");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "46":
                System.out.println("中心站查询遥测站状态和报警信息");
                baoWen.setType("中心站查询遥测站状态和报警信息");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "48":
                System.out.println("恢复遥测站出厂设置");
                baoWen.setType("恢复遥测站出厂设置");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "49":
                System.out.println("修改密码");
                baoWen.setType("修改密码");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
            case "f3":
                System.out.println("启动遥测站摄像头");
                baoWen.setType("启动遥测站摄像头");
                baoWen.setDate(s2);
                httpUtil.doGet("http://hjm:8080/saveDemoTest", baoWen);
                break;
        }

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("服务端接收数据完毕..");
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
        System.out.println("异常信息：\r\n" + cause.getMessage());
    }
}