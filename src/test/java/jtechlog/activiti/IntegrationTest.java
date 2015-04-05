package jtechlog.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:/applicationContext-activiti.xml", "classpath:/applicationContext.xml",
        "classpath:/applicationContext-test.xml"})
public abstract class IntegrationTest {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    protected Workflow workflow;

    @Autowired
    protected WorkflowSupport workflowSupport;

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
}
