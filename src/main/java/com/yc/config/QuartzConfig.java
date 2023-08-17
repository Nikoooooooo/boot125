package com.yc.config;

import com.yc.biz.AccountBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import java.util.Date;

@Configuration
public class QuartzConfig {
    @Autowired
    private AccountBiz accountBiz;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sendMailer;

    @Scheduled(cron = "0 0/2 * * * ?")    //cron表达式
    public void getTotal(){
        Double total=accountBiz.findTotalBalance();
        System.out.println("银行的总余额为:"+total);
    }

    @Scheduled(cron = "0 0/8 * * * ?")    //cron表达式
    public void sendEmail() {
        String title="银行余额 王斌斌";
        String text="银行余额为:"+accountBiz.findTotalBalance();
        String receive="2609406065@qq.com";
        try {
            //true 代表支持复杂的类型
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(),true);
            //邮件发信人
            mimeMessageHelper.setFrom(sendMailer);
            //邮件收信人
            mimeMessageHelper.setTo(receive);
            //邮件主题
            mimeMessageHelper.setSubject(title);
            //邮件内容
            mimeMessageHelper.setText(text);
            //邮件发送时间
            mimeMessageHelper.setSentDate(new Date());
            //发送邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
            System.out.println("发送邮件成功：" +sendMailer+"===>"+receive);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("发送邮件失败："+e.getMessage());
        }
    }


}
