package ru.runa.wfe.task.logic;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import ru.runa.wfe.execution.ExecutionContext;
import ru.runa.wfe.ss.SubstitutionCriteria;
import ru.runa.wfe.task.Task;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.ExecutorDoesNotExistException;
import ru.runa.wfe.user.dao.IExecutorDAO;

import com.google.common.collect.Sets;

@Test
@ContextConfiguration(locations = { "classpath:ru/runa/wfe/task/logic/test.context.xml" })
public class CheckSubstitutionRulesBoundConditionsTests extends AbstractTestNGSpringContextTests {

    private static final Log log = LogFactory.getLog(CheckSubstitutionRulesBoundConditionsTests.class);

    @Autowired
    ITaskListBuilderTestProvider taskListBuilder;

    @DataProvider
    public Object[][] getExceptionsTestcases() {
        return new Object[][] { { "applies with one of DataAccessException testcase", new TestCaseDataSet() {
            @Override
            public void mockRules(IExecutorDAO executorDAO) {
                when(executorDAO.getActor(new Long(1))).thenReturn(mock(Actor.class));
                when(executorDAO.getActor(new Long(2))).thenThrow(mock(org.springframework.dao.DataAccessException.class));
                when(executorDAO.getActor(new Long(3))).thenReturn(mock(Actor.class));
                when(executorDAO.getActor(new Long(4))).thenReturn(mock(Actor.class));
            }

            @Override
            public SubstitutionCriteria getCriteria() {
                SubstitutionCriteria criteria = mock(SubstitutionCriteria.class);
                when(criteria.isSatisfied(any(ExecutionContext.class), any(Task.class), any(Actor.class), any(Actor.class))).thenReturn(true);
                return criteria;
            }

            @Override
            public Set<Long> getIds() {
                return Sets.newHashSet(new Long(1), new Long(2), new Long(3), new Long(4));
            }

        } }, { "applies with one of ExecutorDoesNotExistException testcase", new TestCaseDataSet() {
            @Override
            public void mockRules(IExecutorDAO executorDAO) {
                when(executorDAO.getActor(new Long(1))).thenReturn(mock(Actor.class));
                when(executorDAO.getActor(new Long(2))).thenThrow(mock(ExecutorDoesNotExistException.class));
                when(executorDAO.getActor(new Long(3))).thenReturn(mock(Actor.class));
                when(executorDAO.getActor(new Long(4))).thenReturn(mock(Actor.class));
            }

            @Override
            public SubstitutionCriteria getCriteria() {
                SubstitutionCriteria criteria = mock(SubstitutionCriteria.class);
                when(criteria.isSatisfied(any(ExecutionContext.class), any(Task.class), any(Actor.class), any(Actor.class))).thenReturn(true);
                return criteria;
            }

            @Override
            public Set<Long> getIds() {
                return Sets.newHashSet(new Long(1), new Long(2), new Long(3), new Long(4));
            }

        } } };
    }

    @Test(dataProvider = "getExceptionsTestcases")
    void exceptionsTests(String testName, TestCaseDataSet testCase) {

        log.info(String.format("start test: %s", testName));

        int rules = taskListBuilder.checkSubstitutionRules(testCase.getCriteria(), testCase.getIds(), testCase.getExeContext(), testCase.getTask(),
                testCase.getAssignedActor(), testCase.getSubstitutorActor());

        Assert.assertEquals(rules, TaskListBuilder.SUBSTITUTION_APPLIES);
    }
}
