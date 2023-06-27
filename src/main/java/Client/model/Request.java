package Client.model;

import java.util.ArrayList;

public class Request {
    private String methodName;
    private final ArrayList<Object> parameters = new ArrayList<>();

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public ArrayList<Object> getParameters() {
        return parameters;
    }

    public void addParameter(Object object) {
        parameters.add(object);
    }
}
