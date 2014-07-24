package jtechlog.activiti;

import java.util.List;

public interface Workflow {

    public void requestTimeOff(TimeOffRequest timeOffRequest);

    public List<TimeOffRequest> listTimeOffRequests();

    public void approve(TimeOffRequest timeOffRequest);

}
