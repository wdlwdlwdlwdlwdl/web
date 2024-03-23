package com.wdl.web.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HLACommand {
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
        // 完整的 Python 解释器路径
        String pythonPath = "E:/anaconda/envs/pytorch/python.exe";

        //  E:/anaconda/envs/pytorch/python.exe D:/web/web/性能测试.py

        // 脚本路径
        String scriptPath = "H:/桌面/hla/性能测试_hla.py";
//        String scriptPath = "D:/web/web/性能测试.py";
        //H:/桌面/hla/性能测试_hla.py

        // 构建命令
        String command = pythonPath + " " + scriptPath;

        //下面两行代码是最后打包时要写的，如果报错，将下面注释的代码解除注释，并将这两行代码注释掉
        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();

//        try {
//            Process process = Runtime.getRuntime().exec(command);
//            process.waitFor();
//
//            // 输出命令执行结果
//            logCommandOutput(process.getInputStream(), "Command Output");
//
//            // 检查是否有错误输出
//            InputStream errorStream = process.getErrorStream();
//            if (errorStream.available() > 0) {
//                logCommandOutput(errorStream, "Command Error");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

//    public void logCommandOutput(InputStream inputStream, String logPrefix) throws IOException {
//        // 输出命令执行结果
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
//        String line;
//        System.out.println(logPrefix + ":");
//        while ((line = reader.readLine()) != null) {
//            System.out.println(line);
//        }
//    }


    public void executeLinuxCommands() throws IOException, InterruptedException {
        // 执行 Linux 下的命令
        // 例如：source activate ycp
        Process process1 = Runtime.getRuntime().exec("source activate ycp && python /data/ycp/fx/性能测试.py");
        process1.waitFor();
    }
}
