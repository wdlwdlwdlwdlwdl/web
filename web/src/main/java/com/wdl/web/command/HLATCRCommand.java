package com.wdl.web.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HLATCRCommand {
    public void executeShellCommands() {
        String osName = System.getProperty("os.name");

        try {
            if (osName.startsWith("Windows")) {
                executeWindowsCommands();
            } else if (osName.startsWith("Linux")) {
                executeLinuxCommands();
            } else {
                System.err.println("Unsupported operating system: " + osName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void executeWindowsCommands() throws IOException, InterruptedException {


        //本地测试
        String scriptPath = "E:/anaconda/envs/pytorch/python.exe E:/FTP/web/data/性能测试_hla_tcr.py";

        //服务器
//        String scriptPath = "python E:/FTP/web/data/性能测试_hla_tcr.py";




        // 构建命令
        String command = scriptPath;






        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();



    }




    public void executeLinuxCommands() throws IOException, InterruptedException {
        // 执行 Linux 下的命令
        // 例如：source activate ycp
        Process process1 = Runtime.getRuntime().exec("source activate ycp && python /data/ycp/fx/性能测试.py");
        process1.waitFor();
    }
}
