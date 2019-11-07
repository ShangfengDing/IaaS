package appcloud.flowcontroller.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

public class CommandHelper {
	private static Logger logger = Logger.getLogger(CommandHelper.class);
    //default time out, in millseconds
    public int DEFAULT_TIMEOUT = 600000;
    public int DEFAULT_INTERVAL = 1000;


    public CommandResult exec(String command) throws IOException {
    	logger.info("excute command:" + command);
    	Process process = Runtime.getRuntime().exec(new String[]{"sh","-c",command});
        CommandResult commandResult = wait(process);
        if (process != null) {
            process.destroy();
        }
        return commandResult;
    }

    private boolean isOverTime(long start) {
        return System.currentTimeMillis() - start >= DEFAULT_TIMEOUT;
    }

    private CommandResult wait(Process process) throws IOException {
        BufferedReader errorStreamReader = null;
        BufferedReader inputStreamReader = null;
        long start;
        try {
            errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            //timeout control
            start = System.currentTimeMillis();
            boolean isFinished = false;

            for (;;) {
                if (isOverTime(start)) {
                    CommandResult result = new CommandResult();
                    result.setExitValue(CommandResult.EXIT_VALUE_TIMEOUT);
                    result.setOutput("Command process timeout");
                    return result;
                }

                if (isFinished) {
                    CommandResult result = new CommandResult();
                    try {
						result.setExitValue(process.waitFor());
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
						throw new IOException("Actually, It's an InterruptedException.");
					}
                    
                    //parse error info
                    if (errorStreamReader.ready()) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = errorStreamReader.readLine()) != null) {
                            buffer.append(line);
                        }
                        result.setError(buffer.toString());
                    }

                    //parse info
                    if (inputStreamReader.ready()) {
                        StringBuilder buffer = new StringBuilder();
                        String line;
                        while ((line = inputStreamReader.readLine()) != null) {
                            buffer.append(line);
                            buffer.append("\n");
                        }
                        result.setOutput(buffer.toString());
                    }
                    return result;
                }

                try {
                    isFinished = true;
                    process.exitValue();
                } catch (IllegalThreadStateException e) {
                    // process hasn't finished yet
                    isFinished = false;
                    try {
						Thread.sleep(DEFAULT_INTERVAL);
					} catch (InterruptedException e1) {
						logger.error(e.getMessage());
						throw new IOException("Actually, It's an InterruptedException.");
					}
                }
            }

        } finally {
            if (errorStreamReader != null) {
                try {
                    errorStreamReader.close();
                } catch (IOException e) {
                }
            }

            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
