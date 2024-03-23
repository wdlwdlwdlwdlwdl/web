package com.wdl.web.controller.HLATCRcontroller;

import com.wdl.web.service.MailService;
import com.wdl.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HLATCRfile {
    private static String fileUploadRootDir = null;

    @Value("${HLAfile.upload.root.dir.windows}")
    String HLAfileUploadRootDirWindows;

    @Value("${TCRfile.upload.root.dir.windows}")
    String TCRfileUploadRootDirWindows;

    @Value("${HLA-TCRfile.upload.root.dir.windows}")
    String HLATCRfileUploadRootDirWindows;

    @Value("${file.upload.root.dir.windows}")
    String fileUploadRootDirWindows;



    @Value("${file.upload.root.dir.mac}")
    String fileUploadRootDirMac;

    @Value("${file.upload.root.dir.linux}")
    String fileUploadRootDirLinux;

    @Autowired
    private MailService mailService;

    // 通过@Value注解从properties文件中读取文件路径(运行结果)
    @Value("${HLAFilePath}")
    String HLAfilePath;

    // 通过@Value注解从properties文件中读取文件路径(运行结果)
    @Value("${TCRFilePath}")
    String TCRfilePath;

    // 通过@Value注解从properties文件中读取文件路径(运行结果)
    @Value("${HLATCRFilePath}")
    String HLATCRfilePath;


//    @Value("${HotPathHLA}")
//    String hotPath;

    @Value("${HotPathHLA}")
    String HLAhotPath;

    @Value("${HotPathTCR}")
    String TCRhotPath;

    @Value("${HotPathHLATCR}")
    String HLATCRhotPath;

    @Value("${fileName}")
    String filename;


    @ResponseBody
    @PostMapping("/submitFormHLATCRfile")
    public String HLAfile(@RequestParam(value = "checkbox", required = false) boolean checkbox,
                          @RequestParam("Email_address") String Email_address){

        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS")) {
            fileUploadRootDir = fileUploadRootDirMac;
        } else if (osName.startsWith("Windows")) {
            fileUploadRootDir = HLAfileUploadRootDirWindows;
        } else {
            fileUploadRootDir = fileUploadRootDirLinux;
        }
        FileUtil.createDirectories(fileUploadRootDir);



        boolean result=false;
        if (checkbox) {
            result = mailService.ManysendWithWithEnclosure(Email_address, HLATCRfilePath,HLATCRhotPath,"HLATCR");
        } else {
            result = mailService.sendWithWithEnclosure(Email_address, HLATCRfilePath,"HLATCR");
        }


        //        boolean result = mailService.sendWithWithEnclosure(Email_address, filePath);
        if (result) {
            return "邮件发送成功！";
        } else {
            return "带附件的邮件发送失败，请检查日志获取详细信息。";
        }


    }
}

