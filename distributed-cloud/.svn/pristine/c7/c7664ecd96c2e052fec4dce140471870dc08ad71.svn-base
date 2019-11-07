package appcloud.distributed.util;

import appcloud.distributed.common.Constants;

import java.io.*;

/**
 * Created by lizhenhao on 2017/12/19.
 */
public class Command {

    private String[] command;

    private String error;

    private String output;

    public Command (String[] command) {
        this.command = command;
    }

    public void execSync() {
        try {
            Process process = Runtime.getRuntime().exec(command);
            wait(process);
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void wait(Process process) {
        BufferedReader outReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
        try {
            if (outReader.ready()) {
                String line;
                while ((line = outReader.readLine()) != null) {
                    output = output.concat(line);
                }
            }

            if (errorReader.ready()) {
                String line;
                while ((line = errorReader.readLine()) != null) {
                    error = error.concat(line);
                }
            }
            process.waitFor();

        } catch (IOException e) {
            e.printStackTrace();
            this.error = "IOException" + e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
            this.error = "InterruptedException" + e.getMessage();
        } finally {
            try {
                if (outReader != null) {
                    outReader.close();
                }
                if (errorReader != null) {
                    errorReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                this.error = "IOException" + e.getMessage();
            }
        }
    }

    public void handlerError() {}



    /**
     * 生成各种command语句
     *
     *
     */
    public static String[] createDeleteCommand(String location) {
        String[] commands = new String[]{"/bin/sh","-c","rm -rf "+location};
        return commands;
    }

    public static String createDnsZone(String dnsContent) {
        String command = "echo "+"'"+dnsContent+"'" +">>"+ Constants.DNS_ZONES;
        return command;
    }

    public static String createZoneFile(String dnsContent,String fileName) {
        String command = "echo "+"'"+dnsContent+"'" +">>"+ Constants.DNS_ZONES_PATH + fileName;
        return command;
    }






    public String[] getCommand() {
        return command;
    }

    public void setCommand(String[] command) {
        this.command = command;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
