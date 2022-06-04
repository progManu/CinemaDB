import java.util.ArrayList;

public class StoreProcedureDetails {
    private String callSP;
    private String[] spParameterNames;
    public StoreProcedureDetails(String callSP, String[] spParameterNames){
        this.callSP = callSP;
        this.spParameterNames = spParameterNames;
    }

    public String getCallSP() {
        return callSP;
    }

    public String[] getSpParameterNames() {
        return spParameterNames;
    }
}
