package jtechlog.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/applicationContext-activiti.xml", "classpath:/applicationContext.xml"})
public class WorkflowIntegrationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private Workflow workflow;

    @Before
    public void init() {
        deleteDeployments();
        deploy();
    }

    private void deleteDeployments() {
        for (Deployment deployment: repositoryService.createDeploymentQuery().list()) {
            repositoryService.deleteDeployment(deployment.getId(), true);
        }
    }

    private void deploy() {
        repositoryService.createDeployment()
                .name("timeoffrequest")
                .addInputStream("timeoff.bpmn", WorkflowIntegrationTest.class.getResourceAsStream("/timeoff.bpmn"))
                .deploy();
    }

    @Test
    public void afterRequestTimeOffShouldListIt() {
        TimeOffRequest request = new TimeOffRequest("foo@example.org", new Date(), new Date());
        workflow.requestTimeOff(request);

        assertThat(workflow.listTimeOffRequests(), not(empty()));
        assertThat(workflow.listTimeOffRequests().iterator().next().getMail(), is(equalTo("foo@example.org")));
    }

    @Test
    public void afterApproveListIsEmpty() {
        TimeOffRequest request = new TimeOffRequest("foo@example.org", new Date(), new Date());
        workflow.requestTimeOff(request);
        workflow.approve(request);

        assertThat(workflow.listTimeOffRequests(), is(empty()));
    }
}
