package com.example.biubiu.util;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class SendMailUtil {

    /**
     * 发送邮件(参数自己根据自己的需求来修改，发送短信验证码可以直接套用这个模板)
     * @param recevices		接收人的邮箱
     * @param code		    验证码
     * @return
     */
    public static boolean sendQQEmail(String recevices,int code){
        String from_email ="2674314843@qq.com";
        String pwd = "wahqdfonefgqeaed";
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");		//使用smpt的邮件传输协议
        props.setProperty("mail.smtp.host", "smtp.qq.com");		//主机地址
        props.setProperty("mail.smtp.auth", "true");		//授权通过

        Session session = Session.getInstance(props);		//通过我们的这些配置，得到一个会话程序

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from_email));		//设置发件人
            message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(recevices,"用户","utf-8"));		//设置收件人
            message.setSubject("biubiu邮件验证码","utf-8");		//设置主题
            message.setSentDate(new Date());

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String str = "<!DOCTYPE html><html><head><meta charset='UTF-8'></head><body><p style='font-size: 20px;font-weight:bold;'>您好！</p>"
                    + "<p style='text-indent:2em; font-size: 20px;'>欢迎注册biubiu游戏，您本次的注册码是 "
                    + "<span style='font-size:30px;font-weight:bold;color:red'>" + code + "</span>，3分钟之内有效，请尽快使用！</p>"
                    + "<p style='text-align:right; padding-right: 20px;'"
                    + "<a href='http://www.hyycinfo.com' style='font-size: 18px'>biubiu游戏有限公司</a></p>"
                    + "<span style='font-size: 18px; float:right; margin-right: 60px;'>" + sdf.format(new Date()) + "</span></body></html>";

            Multipart mul=new MimeMultipart();  //新建一个MimeMultipart对象来存放多个BodyPart对象
            BodyPart mdp=new MimeBodyPart();  //新建一个存放信件内容的BodyPart对象
            mdp.setContent(str, "text/html;charset=utf-8");
            mul.addBodyPart(mdp);  //将含有信件内容的BodyPart加入到MimeMultipart对象中
            message.setContent(mul); //把mul作为消息内容


            message.saveChanges();

            //创建一个传输对象
            Transport transport=session.getTransport("smtp");

            transport.connect("smtp.qq.com", 587, from_email, pwd);

            //发送邮件
            transport.sendMessage(message,message.getAllRecipients());

            //关闭邮件传输
            transport.close();

        } catch (AddressException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return true;
    }
}

