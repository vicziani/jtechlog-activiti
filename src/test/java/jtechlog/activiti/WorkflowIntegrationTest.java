package jtechlog.activiti;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class WorkflowIntegrationTest extends IntegrationTest {

    @Test
    public void afterRequestTimeOffShouldListIt() {
        when(workflowSupport.shouldApprove()).thenReturn(true);
        TimeOffRequest request = new TimeOffRequest("foo@example.org", new Date(), new Date());
        workflow.requestTimeOff(request);

        assertThat(workflow.listTimeOffRequests(), not(empty()));
        assertThat(workflow.listTimeOffRequests().iterator().next().getMail(), is(equalTo("foo@example.org")));
    }

    @Test
    public void whenApproveIsNotNecessaryListIsEmpty() {
        when(workflowSupport.shouldApprove()).thenReturn(false);
        TimeOffRequest request = new TimeOffRequest("foo@example.org", new Date(), new Date());
        workflow.requestTimeOff(request);
        assertThat(workflow.listTimeOffRequests(), is(empty()));
    }

    @Test
    public void afterApproveListIsEmpty() {
        when(workflowSupport.shouldApprove()).thenReturn(true);
        TimeOffRequest request = new TimeOffRequest("foo@example.org", new Date(), new Date());
        workflow.requestTimeOff(request);
        workflow.approve(request);

        assertThat(workflow.listTimeOffRequests(), is(empty()));
    }
}
