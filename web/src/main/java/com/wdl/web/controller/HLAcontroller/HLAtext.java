package com.wdl.web.controller.HLAcontroller;

import com.wdl.web.service.MailService;
import com.wdl.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class HLAtext {
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
    @PostMapping("/submitFormHLAtext")
    public String HLAtext(@RequestParam(value = "checkbox", required = false) boolean checkbox,
                          @RequestParam("Email_address") String Email_address,
                          @RequestParam("inputA") String inputA,
                          @RequestParam("inputB") String inputB){

        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS")) {
            fileUploadRootDir = fileUploadRootDirMac;
        } else if (osName.startsWith("Windows")) {
            fileUploadRootDir = HLAfileUploadRootDirWindows;
        } else {
            fileUploadRootDir = fileUploadRootDirLinux;
        }
        FileUtil.createDirectories(fileUploadRootDir);



        String csvContent;

        // 将输入转换成CSV格式
        csvContent = ",Antigen,HLA_sequence\n0,"
                + inputA + "," + inputB;



        // 将 CSV 内容写入到文件
        File csvFile = new File(filename);
        try {
            csvFile.createNewFile();
            FileWriter writer = new FileWriter(csvFile);
            writer.write(csvContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            // 可以在这里记录日志或者执行其他异常处理逻辑
        }


        // 检查 inputA 和 inputB 是否为空，如果不为空才调用 uploadFileHLA() 函数
        if (!inputA.isEmpty() && !inputB.isEmpty()) {
            // 调用文件上传方法将 CSV 文件上传到指定位置
            uploadFileHLA(csvFile);
        }

//        // 调用文件上传方法将 CSV 文件上传到指定位置
//        uploadFileHLA(csvFile);


        boolean result=false;
        if (checkbox) {
            result = mailService.ManysendWithWithEnclosure(Email_address, HLAfilePath,HLAhotPath,"HLA");
        } else {
            result = mailService.sendWithWithEnclosure(Email_address, HLAfilePath,"HLA");
        }



//        boolean result = mailService.sendWithWithEnclosure(Email_address, filePath);
        if (result) {
            return "邮件发送成功！";
        } else {
            return "带附件的邮件发送失败，请检查日志获取详细信息。";
        }


    }


    //    上传手动输入的序列
    private void uploadFileHLA(File file) {
        try {


            String osName = System.getProperty("os.name");
            if (osName.startsWith("Mac OS")) {
                fileUploadRootDir = fileUploadRootDirMac;
            } else if (osName.startsWith("Windows")) {
                fileUploadRootDir = HLAfileUploadRootDirWindows;
            } else {
                fileUploadRootDir = fileUploadRootDirLinux;
            }
            FileUtil.createDirectories(fileUploadRootDir);



            // 使用MultipartFile类创建一个临时文件
            MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/csv", Files.readAllBytes(file.toPath()));

            // 通过Spring提供的MultipartFile类，实现文件上传逻辑
            // 假设上传到fileUploadRootDir目录中
            Path uploadPath = Paths.get(fileUploadRootDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Files.copy(multipartFile.getInputStream(), uploadPath.resolve(multipartFile.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            // 记录日志或者执行其他成功上传后的逻辑
            System.out.println("File uploaded successfully: " + multipartFile.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            // 可以在这里记录日志或者执行其他异常处理逻辑
        }
    }


}
