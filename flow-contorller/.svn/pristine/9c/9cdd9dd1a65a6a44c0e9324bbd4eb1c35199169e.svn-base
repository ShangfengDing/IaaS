package appcloud.flowcontroller.util;

public class CommandResult {
    public static final int EXIT_VALUE_TIMEOUT=-1;
    public static final int PARAMS_WRONG=-2;
    
    private String output;

    public void setOutput(String output) {
        this.output=output;
    }

    public String getOutput(){
        return output;
    }

    int exitValue;

    public void setExitValue(int value) {
        this.exitValue=value;
    }

    public int getExitValue(){
        return exitValue;
    }

    private String error;

    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }
    
    @Override
    public String toString() {
    	StringBuilder result = new StringBuilder();
    	result.append("output:"+output+" |");
    	result.append("error:"+error+" |");
    	result.append("exitValue:"+exitValue);
    	return result.toString();
    }
}