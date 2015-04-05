package jtechlog.activiti.concordion;

import jtechlog.activiti.TimeOffRequest;

import java.util.Date;

import static org.mockito.Mockito.when;

public class ShouldNotApproveTest extends ConcordionTest {

    public void requestTimeOff(String mail) {
        when(workflowSupport.shouldApprove()).thenReturn(false);
        TimeOffRequest request = new TimeOffRequest(mail, new Date(), new Date());
        workflow.requestTimeOff(request);
    }

    public int numberOfTimeOffRequests() {
        return workflow.listTimeOffRequests().size();
    }
}
