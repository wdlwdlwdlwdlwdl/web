package com.wdl.web.controller;

import com.wdl.web.command.Command;
import com.wdl.web.model.FileInfo;
import com.wdl.web.service.ProgressUpdateService;
import com.wdl.web.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileControllerTest {
    private static String fileUploadRootDir = null;

    //    HLA上传文件的地址
    @Value("${HLAfile.upload.root.dir.windows}")
    String HLAfileUploadRootDirWindows;

    //    TCR上传文件的地址
    @Value("${TCRfile.upload.root.dir.windows}")
    String TCRfileUploadRootDirWindows;

    //    HLA-TCR上传文件的地址
    @Value("${HLA-TCRfile.upload.root.dir.windows}")
    String HLATCRfileUploadRootDirWindows;




    @Value("${file.upload.root.dir.mac}")
    String fileUploadRootDirMac;

    @Value("${file.upload.root.dir.linux}")
    String fileUploadRootDirLinux;

    private static Map<String, FileInfo> fileRepository = new HashMap<>();

//    @PostConstruct
//    public void initFileRepository() {
//        String osName = System.getProperty("os.name");
//        if (osName.startsWith("Mac OS")) {
//            fileUploadRootDir = fileUploadRootDirMac;
//        } else if (osName.startsWith("Windows")) {
//            fileUploadRootDir = fileUploadRootDirWindows;
//        } else {
//            fileUploadRootDir = fileUploadRootDirLinux;
//        }
//        FileUtil.createDirectories(fileUploadRootDir);
//    }

//    @RequestMapping("/")
//    public String files(Model model) {
//        Collection<FileInfo> files = fileRepository.values();
//        model.addAttribute("data", files);
//        return "files.html";
//    }

    @RequestMapping("/")
    public RedirectView redirectToIndex() {
        return new RedirectView("/index.html");
    }









    // 将上传的文件改名为 independent.csv
    @Value("${fileName}")
    String filename;


    @PostMapping(value = "/uploadHLA", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String fileUploadHLA(@RequestParam("file") MultipartFile file) throws IOException {
        if (!isCSVFile(file)) {
            return "请上传csv文件";
        }



        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS")) {
            fileUploadRootDir = fileUploadRootDirMac;
        } else if (osName.startsWith("Windows")) {
            fileUploadRootDir = HLAfileUploadRootDirWindows;
        } else {
            fileUploadRootDir = fileUploadRootDirLinux;
        }
        FileUtil.createDirectories(fileUploadRootDir);



//        String uploadDir = getUploadDir();
        String uploadDir = fileUploadRootDir;

        if (!uploadDir.endsWith(File.separator)) {
            uploadDir += File.separator;
        }
        File convertFile = new File(uploadDir + filename);
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());

        fileRepository.put(fileInfo.getName(), fileInfo);

        return "File is uploaded successfully";
    }



    @PostMapping(value = "/uploadTCR", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String fileUploadTCR(@RequestParam("file") MultipartFile file) throws IOException {
        if (!isCSVFile(file)) {
            return "请上传csv文件";
        }



        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS")) {
            fileUploadRootDir = fileUploadRootDirMac;
        } else if (osName.startsWith("Windows")) {
            fileUploadRootDir = TCRfileUploadRootDirWindows;
        } else {
            fileUploadRootDir = fileUploadRootDirLinux;
        }
        FileUtil.createDirectories(fileUploadRootDir);



//        String uploadDir = getUploadDir();
        String uploadDir = fileUploadRootDir;

        if (!uploadDir.endsWith(File.separator)) {
            uploadDir += File.separator;
        }
        File convertFile = new File(uploadDir + filename);
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());

        fileRepository.put(fileInfo.getName(), fileInfo);

        return "File is uploaded successfully";
    }



    @PostMapping(value = "/uploadHLATCR", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String fileUploadHLATCR(@RequestParam("file") MultipartFile file) throws IOException {
        if (!isCSVFile(file)) {
            return "请上传csv文件";
        }



        String osName = System.getProperty("os.name");
        if (osName.startsWith("Mac OS")) {
            fileUploadRootDir = fileUploadRootDirMac;
        } else if (osName.startsWith("Windows")) {
            fileUploadRootDir = HLATCRfileUploadRootDirWindows;
        } else {
            fileUploadRootDir = fileUploadRootDirLinux;
        }
        FileUtil.createDirectories(fileUploadRootDir);



//        String uploadDir = getUploadDir();
        String uploadDir = fileUploadRootDir;

        if (!uploadDir.endsWith(File.separator)) {
            uploadDir += File.separator;
        }
        File convertFile = new File(uploadDir + filename);
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(file.getBytes());
        fileOutputStream.close();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());

        fileRepository.put(fileInfo.getName(), fileInfo);

        return "File is uploaded successfully";
    }





    // 新增一个辅助方法用于检查文件类型
    private boolean isCSVFile(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("text/csv");
    }


//    之前的手动输入上传逻辑，现在调整到 newController 里面执行手动输入序列上传逻辑
    @PostMapping(value = "/uploadCSV", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public String uploadCSV(@RequestParam("csvFile") MultipartFile csvFile) throws IOException {
        String uploadDir = getUploadDir();
        if (!uploadDir.endsWith(File.separator)) {
            uploadDir += File.separator;
        }
        File convertFile = new File(uploadDir + csvFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(convertFile);
        fileOutputStream.write(csvFile.getBytes());
        fileOutputStream.close();

        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(csvFile.getOriginalFilename());

        fileRepository.put(fileInfo.getName(), fileInfo);

        return "File is uploaded successfully";
    }

    public String getUploadDir() {
        return fileUploadRootDir;
    }








    //原先页面的处理逻辑
    @Autowired
    ProgressUpdateService progressUpdateService;

    @Value("${file.download.path}")
    private String fileUploadPath;

    @GetMapping("/download/file")
    @ResponseBody
    public ResponseEntity<Object> downloadFile() {

        // 执行文件下载逻辑前发送进度更新消息
        progressUpdateService.sendProgressUpdate( "Downloading: 0%");

        // 文件上传成功后执行Shell命令
        Command commandExecutor = new Command();
        commandExecutor.executeShellCommands();


        String filePath = fileUploadPath;
        File file = new File(filePath);

        if (file.exists()) {
            try {
                String actualFileName = file.getName();

                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment;filename=" + encodeFileNameToUTF8(actualFileName));
                headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");

                // 执行文件下载逻辑后发送进度更新消息
                progressUpdateService.sendProgressUpdate( "Downloading: 100%");


                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (FileNotFoundException e) {
                return ResponseEntity.status(500).body("Internal Server Error");
            }
        } else {
            return ResponseEntity.status(404).body("File not found");
        }
    }

    private String encodeFileNameToUTF8(String fileName) {
        try {
            return URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return fileName;
        }
    }





    //  最新的文件下载处理逻辑
    @Value("${ExampleFilePath}")
    private String ExampleFilePath;

    @GetMapping("/static/examples")
    @ResponseBody
    public ResponseEntity<Object> downloadFile2() {

        String filePath = ExampleFilePath;
        File file = new File(filePath);

        if (file.exists()) {
            try {
                String actualFileName = file.getName();

                InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Disposition", "attachment;filename=" + encodeFileNameToUTF8(actualFileName));
                headers.add("Cache-Control", "no-cache,no-store,must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");



                return ResponseEntity.ok()
                        .headers(headers)
                        .contentLength(file.length())
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(resource);
            } catch (FileNotFoundException e) {
                return ResponseEntity.status(500).body("Internal Server Error");
            }
        } else {
            return ResponseEntity.status(404).body("File not found");
        }
    }


}
