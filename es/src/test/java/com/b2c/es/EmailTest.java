package com.b2c.es;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Date;

/**
 * 邮件集成测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailTest {

    //发送邮件人，可以从配置文件中获取
    @Value("${spring.mail.username}")
    public String myEmail ;//"272038088@qq.com";

    //主送
    private String[] toEmail    = {"272038088@qq.com","154262341@qq.com"};
    //抄送
    private String[] ccEmail    = {"272038088@qq.com"};
    //暗送
    private String[] bccEmail = {"154262341@qq.com"};

    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 测试获取配置文件的数据
     */
    @Test
    public void getMailName(){
        System.out.println(myEmail);
    }

    /**
     * 发送邮件测试，简单内容
     */
    @Test
    public void toSendMail(){
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setSubject("关于今晚家庭聚餐的通知");
        simpleMessage.setText("关于今晚家庭聚餐的通知：各位亲朋好友，我今晚请客，你们掏钱。");
        simpleMessage.setSentDate(new Date());
        //
        simpleMessage.setTo(toEmail);

        //
        simpleMessage.setFrom(myEmail);
        mailSender.send(simpleMessage);
    }

    /**
     * 发送邮件测试，复杂的邮件
     */
    @Test
    public void toSendMimeMessage() throws MessagingException {
        //创建发送消息的对象
        MimeMessage mimeMailMessage = this.mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage,true);
        //设置发送内容部分
        //标题
        String subject = "好照片就是要分享";
        //内容
        String text = "<div style='font-size:18px;'>好作品：</div>"
                    +"<HR>"
                    +"<span style='font-size:25px;'>这是去杭州的照片，分享光荣。测试【java集成邮件发送。。。】</span>";
        //设置信息
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(text,true);
        mimeMessageHelper.setSentDate(new Date());

        //附件
        mimeMessageHelper.addAttachment("图1", new File("/Users/ming/Documents/我的资料/我的照片/浙江-游/IMG_3640.JPG"));
        mimeMessageHelper.addAttachment("图2", new File("/Users/ming/Documents/我的资料/我的照片/浙江-游/IMG_3567.JPG"));
        mimeMessageHelper.addAttachment("图3", new File("/Users/ming/Documents/我的资料/我的照片/浙江-游/IMG_3599.JPG"));

        //执行发送动作
        //主送to
        mimeMessageHelper.setTo(toEmail);
        //抄送cc
        mimeMessageHelper.setCc(ccEmail);

        //暗送bcc
        mimeMessageHelper.setBcc(bccEmail);
        //发件人
        mimeMessageHelper.setFrom(myEmail);

        mailSender.send(mimeMailMessage);
    }

}
