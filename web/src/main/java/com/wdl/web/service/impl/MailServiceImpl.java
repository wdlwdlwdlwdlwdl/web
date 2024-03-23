package com.wdl.web.service.impl;

//import com.wdl.email.service.MailService;
import com.wdl.web.command.*;
import com.wdl.web.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class MailServiceImpl implements MailService {

    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

//    @Component
//    public class MailPropertiesWrapper extends MailProperties {
//        // 这里可以为空，或者保留原有属性和方法
//    }

    @Autowired
    private MailProperties mailProperties;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    ProgressUpdateServiceImpl progressUpdateService;


    @Override
    public boolean sendSimpleText(String to, String subject, String content) {
        logger.info("## Ready to send mail ...");

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 邮件发送来源
        simpleMailMessage.setFrom(mailProperties.getUsername());
        // 邮件发送目标
        simpleMailMessage.setTo(to);
        // 设置标题
        simpleMailMessage.setSubject(subject);
        // 设置内容
        simpleMailMessage.setText(content);

        try {
            // 发送
            javaMailSender.send(simpleMailMessage);
            logger.info("## Send the mail success ...");
        } catch (Exception e) {
            logger.error("Send mail error: ", e);
            return false;
        }

        return true;
    }



    @Override
    public boolean sendWithWithEnclosure(String to, String filePath,String datatype) {

        // 执行邮件发送逻辑前发送进度更新消息
        progressUpdateService.sendProgressUpdate("Sending Email: 0%");



        if(datatype.equals("HLA")){
            HLACommand commandExecutor=new HLACommand();
            commandExecutor.executeShellCommands();
        }
        else if(datatype.equals("TCR")){
            TCRCommand commandExecutor=new TCRCommand();
            commandExecutor.executeShellCommands();
        }
        else{
            HLATCRCommand commandExecutor=new HLATCRCommand();
            commandExecutor.executeShellCommands();
        }

//        // 文件上传成功后执行Shell命令
//        Command commandExecutor = new Command();
//        commandExecutor.executeShellCommands();

        logger.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("标题：运行结果");
            mimeMessageHelper.setText("运行结果");

            FileSystemResource file = new FileSystemResource(filePath);
            //String attachementFileName = "附件_" + file.getFilename();
            String attachementFileName = file.getFilename();
            mimeMessageHelper.addAttachment(attachementFileName, file);

            javaMailSender.send(mimeMessage);
            logger.info("## Send the mail with enclosure success ...");

            // 邮件发送逻辑后发送进度更新消息
            progressUpdateService.sendProgressUpdate("Sending Email: 100%");

        } catch (Exception e) {
            logger.error("Send html mail error: ", e);
            return false;
        }
        return true;
    }



    //此函数只能发送两个附件（已舍弃）
    @Override
    public boolean sendWithWithEnclosure(String to, String filePathA, String filePathB,String datatype) {

        // 执行邮件发送逻辑前发送进度更新消息
        progressUpdateService.sendProgressUpdate("Sending Email: 0%");



        if(datatype.equals("HLA")){
            HLACommand commandExecutor=new HLACommand();
            commandExecutor.executeShellCommands();
        }
        else if(datatype.equals("TCR")){
            TCRCommand commandExecutor=new TCRCommand();
            commandExecutor.executeShellCommands();
        }
        else if(datatype.equals("HLATCR")){
            HLATCRCommand commandExecutor=new HLATCRCommand();
            commandExecutor.executeShellCommands();
        }



//        // 文件上传成功后执行Shell命令
//        Command commandExecutor = new Command();
//        commandExecutor.executeShellCommands();

        logger.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("标题：运行结果");
            mimeMessageHelper.setText("运行结果");

            // 添加第一个附件
            FileSystemResource fileA = new FileSystemResource(filePathA);
            String attachmentFileNameA = fileA.getFilename();
            mimeMessageHelper.addAttachment(attachmentFileNameA, fileA);

            // 添加第二个附件
            FileSystemResource fileB = new FileSystemResource(filePathB);
            String attachmentFileNameB = fileB.getFilename();
            mimeMessageHelper.addAttachment(attachmentFileNameB, fileB);

            javaMailSender.send(mimeMessage);
            logger.info("## Send the mail with enclosure success ...");

            // 邮件发送逻辑后发送进度更新消息
            progressUpdateService.sendProgressUpdate("Sending Email: 100%");

        } catch (Exception e) {
            logger.error("Send html mail error: ", e);
            return false;
        }
        return true;
    }




    //此函数能发送多个附件（filepath 路径下的附件， folderPath 文件夹下的所有附件）
    @Override
    public boolean ManysendWithWithEnclosure(String to, String filepath, String folderPath, String datatype) {

        // 执行邮件发送逻辑前发送进度更新消息
        progressUpdateService.sendProgressUpdate("Sending Email: 0%");

        // 清空文件夹内的附件
        clearFolderAttachments(folderPath);




        if(datatype.equals("HLA")){
//            HLACommand commandExecutor=new HLACommand();
//            commandExecutor.executeShellCommands();
            HLAhotCommand commandExecutor=new HLAhotCommand();
            commandExecutor.executeShellCommands();
        }
        else if(datatype.equals("TCR")){
//            TCRCommand commandExecutor=new TCRCommand();
//            commandExecutor.executeShellCommands();
            TCRhotCommand commandExecutor=new TCRhotCommand();
            commandExecutor.executeShellCommands();
        }
        else if(datatype.equals("HLATCR")){
//            HLATCRCommand commandExecutor=new HLATCRCommand();
//            commandExecutor.executeShellCommands();
            HLATCRhotCommand commandExecutor=new HLATCRhotCommand();
            commandExecutor.executeShellCommands();
        }

        logger.info("## Ready to send mail ...");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(mailProperties.getUsername());
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("标题：运行结果");
            mimeMessageHelper.setText("运行结果");

            // 添加指定路径下的文件作为附件
            if (filepath != null && !filepath.isEmpty()) {
                FileSystemResource file = new FileSystemResource(filepath);
                mimeMessageHelper.addAttachment(file.getFilename(), file);
            }

            // 添加文件夹下的所有文件作为附件
            if (folderPath != null && !folderPath.isEmpty()) {
                File folder = new File(folderPath);
                if (folder.exists() && folder.isDirectory()) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.isFile()) {
                            FileSystemResource resource = new FileSystemResource(file);
                            mimeMessageHelper.addAttachment(file.getName(), resource);
                        }
                    }
                }
            }

            javaMailSender.send(mimeMessage);
            logger.info("## Send the mail with enclosure success ...");

            // 邮件发送逻辑后发送进度更新消息
            progressUpdateService.sendProgressUpdate("Sending Email: 100%");

        } catch (Exception e) {
            logger.error("Send html mail error: ", e);
            return false;
        }
        return true;
    }

    //清空 folderPath 路径下所有的附件
    private void clearFolderAttachments(String folderPath) {
        if (folderPath != null && !folderPath.isEmpty()) {
            File folder = new File(folderPath);
            if (folder.exists() && folder.isDirectory()) {
                File[] files = folder.listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        // 删除文件
                        file.delete();
                    }
                }
            }
        }
    }

}
