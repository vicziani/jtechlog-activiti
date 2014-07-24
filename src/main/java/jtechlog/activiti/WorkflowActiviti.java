package jtechlog.activiti;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WorkflowActiviti implements Workflow {

    public static final String PROCESS_VARIABLE_TIME_OFF_REQUEST = "timeoffrequest";

    public static final String DEPLOYMENT_NAME = "timeoffrequest";

    private RuntimeService runtimeService;

    private TaskService taskService;

    @Autowired
    public WorkflowActiviti(RuntimeService runtimeService, TaskService taskService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
    }

    @Override
    public void requestTimeOff(TimeOffRequest timeOffRequest) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(PROCESS_VARIABLE_TIME_OFF_REQUEST, timeOffRequest);
        runtimeService.startProcessInstanceByKey(DEPLOYMENT_NAME,
                timeOffRequest.getId(), parameters);
    }

    @Override
    public List<TimeOffRequest> listTimeOffRequests() {
        List<TimeOffRequest> requests = new ArrayList<>();
        List<Task> tasks = taskService.createTaskQuery().orderByTaskCreateTime().includeProcessVariables()
                .desc().list();
        for (Task task: tasks) {
            requests.add((TimeOffRequest) task.getProcessVariables().get(PROCESS_VARIABLE_TIME_OFF_REQUEST));
        }
        return requests;
    }

    @Override
    public void approve(TimeOffRequest timeOffRequest) {
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceBusinessKey(timeOffRequest.getId()).list();
        taskService.complete(tasks.iterator().next().getId());
    }
}
