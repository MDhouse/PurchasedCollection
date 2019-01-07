package purchases.application.purchasescollection.common.notification;

public class ExtraParameter {

    private String parameterName;
    private String parameterValue;

    public ExtraParameter(String parameterName, String parameterValue) {
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }
}
