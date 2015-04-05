package jtechlog.activiti.concordion;

import jtechlog.activiti.IntegrationTest;
import org.concordion.Concordion;
import org.concordion.api.ResultSummary;
import org.concordion.internal.ConcordionBuilder;
import org.junit.Test;

import java.io.IOException;

public abstract class ConcordionTest extends IntegrationTest {

    @Test
    public void runSpecification() throws IOException {
        Concordion concordion = new ConcordionBuilder().build();

        ResultSummary resultSummary = concordion.process(this);
        System.out.print("Successes: " + resultSummary.getSuccessCount());
        System.out.print(", Failures: " + resultSummary.getFailureCount());
        if (resultSummary.hasExceptions()) {
            System.out.print(", Exceptions: " + resultSummary.getExceptionCount());
        }
        System.out.print("\n");

        resultSummary.assertIsSatisfied(concordion);
    }
}
