package jtechlog.activiti.concordion;

import jtechlog.activiti.TimeOffRequest;

import java.util.Date;

import static org.mockito.Mockito.when;

public class ApproveTest extends ConcordionTest {

    TimeOffRequest request;

    public void requestTimeOff(String mail) {
        when(workflowSupport.shouldApprove()).thenReturn(true);
        request = new TimeOffRequest(mail, new Date(), new Date());
        workflow.requestTimeOff(request);
    }
    
    public void approve() {
        workflow.approve(request);
    }

    public int numberOfTimeOffRequests() {
        return workflow.listTimeOffRequests().size();
    }
}
