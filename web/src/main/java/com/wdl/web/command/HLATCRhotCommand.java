package com.wdl.web.command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HLATCRhotCommand {
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

        // 第一个脚本路径
        String scriptPath1 = "H:/桌面/hla_tcr/性能测试_hla_tcr.py";
        String command1 = pythonPath + " " + scriptPath1;

        // 第二个脚本路径
        String scriptPath2 = "H:/桌面/hla_tcr/热图_hla_tcr.py";
        String command2 = pythonPath + " " + scriptPath2;

        // 执行第一个命令
        Process process1 = Runtime.getRuntime().exec(command1);
        process1.waitFor();

        // 执行第二个命令
        Process process2 = Runtime.getRuntime().exec(command2);
        process2.waitFor();

    }




    public void executeLinuxCommands() throws IOException, InterruptedException {
        // 执行 Linux 下的命令
        // 例如：source activate ycp
        Process process1 = Runtime.getRuntime().exec("source activate ycp && python /data/ycp/fx/性能测试.py");
        process1.waitFor();
    }
}
