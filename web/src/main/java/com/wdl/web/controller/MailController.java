//package com.wdl.web.controller;
//
//import com.wdl.web.service.MailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//@Controller
//public class MailController {
//
//    @Autowired
//    private MailService mailService;
//
//    @Value("${subject}")
//    String subject;
//
//    // 通过@Value注解从properties文件中读取邮件内容
//    @Value("${content}")
//    String content;
//
//
//    //老页面的邮件映射(发送内容的)
//    @RequestMapping ("/sendEmail")
//    @ResponseBody
//    public String sendSimpleTextMailMailHLA(@RequestParam String to) {
//
////        if (to == null) {
////            // 处理缺少 'to' 参数的情况
////            return "缺少 'to' 参数";
////        }
//
//        boolean result = mailService.sendSimpleText(to, subject, content);
//        if (result) {
//            return "邮件发送成功！";
//        } else {
//            return "邮件发送失败，请检查日志获取详细信息。";
//        }
//    }
//
//
//
//
//
//
//
//    // 通过@Value注解从properties文件中读取文件路径
//    @Value("${FilePath}")
//    String filePath;
//
//    @Value("${HotPath}")
//    String hotPath;
//
////    老的，现在不用了
//    @PostMapping("/sendWithEnclosure")
//    @ResponseBody
//    public String sendWithEnclosureMail(@RequestParam String to) {
//        boolean result = mailService.sendWithWithEnclosure(to, filePath,hotPath);
//        if (result) {
//            return "邮件发送成功！";
//        } else {
//            return "带附件的邮件发送失败，请检查日志获取详细信息。";
//        }
//    }
//
//
////    //新页面的邮件映射（发送附件的）
////    @PostMapping("/submitForm")
////    @ResponseBody
////    public String sendWithEnclosureMailA(@RequestParam String Email_address) {
////        boolean result = mailService.sendWithWithEnclosure(Email_address, filePath);
////        if (result) {
////            return "邮件发送成功！";
////        } else {
////            return "带附件的邮件发送失败，请检查日志获取详细信息。";
////        }
////    }
//
//
//}