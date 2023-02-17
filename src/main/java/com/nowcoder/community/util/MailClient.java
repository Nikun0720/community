package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender mailSender;    // 虽然编译会报错，但是运行没问题

    @Value("${spring.mail.username}")
    private String sender;

    public void sendMail(String receiver, String subject, String content) {

        try {

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setFrom(sender);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(content, true);  // 这个true，加了就支持html文本，不加的话仅支持普通文本

            mailSender.send(helper.getMimeMessage());

        } catch (MessagingException e) {
            logger.error("发送邮件失败：" + e.getMessage());
        }

    }

}
