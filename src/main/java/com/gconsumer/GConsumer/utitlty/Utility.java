package com.gconsumer.GConsumer.utitlty;

import com.gconsumer.GConsumer.config.PasswordGenerator;
import com.gconsumer.GConsumer.model.EmailDetails;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class Utility {


    public static Map<String, Object> setResponseData( Object data) {
        Map<String, Object> res = new HashMap<>();
        res.put("data", data);
        return res;
    }

    public static String getOTP(int len) {
        String otp = "";
        int ranNo;
        for (int i = 0; i < len; i++) {
            ranNo = new Random().nextInt(9);
            otp = otp.concat(Integer.toString(ranNo));
        }
        return otp;
    }

    public static String addMinutesToCurrentDateTime(int minutes, String format) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.now().plus(Duration.of(minutes, ChronoUnit.MINUTES)).format(dateTimeFormatter);
    }

    public static String sendSimpleMail(EmailDetails emailDetails, JavaMailSender javaMailSender) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("welpieproj@gmail.com");
            mailMessage.setTo(emailDetails.getRecipient());
            mailMessage.setText(emailDetails.getMsgBody());
            mailMessage.setSubject(emailDetails.getSubject());
            javaMailSender.send(mailMessage);
            return "Sent";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String encodeToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String decodeToString(String src) {
        return new String(Base64.getDecoder().decode(src));
    }

    public static String generatePasswordWithCustomLength(int length) {
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        return passwordGenerator.generate(length);
    }

    public static void sendSMS(String password) {
    }
}
