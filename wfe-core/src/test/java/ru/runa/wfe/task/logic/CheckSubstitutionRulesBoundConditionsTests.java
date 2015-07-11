package ru.runa.wfe.task.logic;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
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
                Actor actor = mock(Actor.class);
                when(actor.isActive()).thenReturn(true);
                when(executorDAO.getActor(new Long(1))).thenReturn(actor);
                when(executorDAO.getActor(new Long(2))).thenThrow(mock(org.springframework.dao.DataAccessException.class));
                when(executorDAO.getActor(new Long(3))).thenReturn(actor);
                when(executorDAO.getActor(new Long(4))).thenReturn(actor);
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
                Actor actor = mock(Actor.class);
                when(actor.isActive()).thenReturn(true);
                when(executorDAO.getActor(new Long(1))).thenReturn(actor);
                when(executorDAO.getActor(new Long(2))).thenThrow(mock(ExecutorDoesNotExistException.class));
                when(executorDAO.getActor(new Long(3))).thenReturn(actor);
                when(executorDAO.getActor(new Long(4))).thenReturn(actor);
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

        TaskLogicMockFactory.getFactory().setContextRules(testCase);

        int rules = taskListBuilder.checkSubstitutionRules(testCase.getCriteria(), testCase.getIds(), testCase.getExeContext(), testCase.getTask(),
                testCase.getAssignedActor(), testCase.getSubstitutorActor());

        Assert.assertEquals(rules, TaskListBuilder.SUBSTITUTION_APPLIES);

        TaskLogicMockFactory.getFactory().setContextRules(null);
    }

    @DataProvider
    public Object[][] getSuccessTestcases() {
        return new Object[][] { { "applies testcase", TaskListBuilder.SUBSTITUTION_APPLIES, new TestCaseDataSet() {

            Actor actor;

            @Override
            public void mockRules(IExecutorDAO executorDAO) {
                actor = mock(Actor.class);
                when(actor.isActive()).thenReturn(true);
                when(executorDAO.getActor(new Long(1))).thenReturn(actor);
            }

            @Override
            public SubstitutionCriteria getCriteria() {
                SubstitutionCriteria criteria = mock(SubstitutionCriteria.class);
                when(criteria.isSatisfied(any(ExecutionContext.class), any(Task.class), any(Actor.class), eq(actor))).thenReturn(true);
                return criteria;
            }

            @Override
            public Set<Long> getIds() {
                return Sets.newHashSet(new Long(1));
            }
        } }, { "can substitute testcase", TaskListBuilder.CAN_I_SUBSTITUTE, new TestCaseDataSet() {

            Actor actor;

            @Override
            public void mockRules(IExecutorDAO executorDAO) {
                actor = mock(Actor.class);
                when(executorDAO.getActor(new Long(1))).thenReturn(actor);
            }

            @Override
            public SubstitutionCriteria getCriteria() {
                SubstitutionCriteria criteria = mock(SubstitutionCriteria.class);
                when(criteria.isSatisfied(any(ExecutionContext.class), any(Task.class), any(Actor.class), eq(actor))).thenReturn(true);
                return criteria;
            }

            @Override
            public Actor getSubstitutorActor() {
                return actor;
            }

            @Override
            public Set<Long> getIds() {
                return Sets.newHashSet(new Long(1));
            }

        } }, { "applies and can substitute testcase", TaskListBuilder.SUBSTITUTION_APPLIES | TaskListBuilder.CAN_I_SUBSTITUTE, new TestCaseDataSet() {

            Actor actor;

            @Override
            public void mockRules(IExecutorDAO executorDAO) {
                actor = mock(Actor.class);
                when(actor.isActive()).thenReturn(true);
                when(executorDAO.getActor(new Long(1))).thenReturn(actor);
            }

            @Override
            public SubstitutionCriteria getCriteria() {
                SubstitutionCriteria criteria = mock(SubstitutionCriteria.class);
                when(criteria.isSatisfied(any(ExecutionContext.class), any(Task.class), any(Actor.class), eq(actor))).thenReturn(true);
                return criteria;
            }

            @Override
            public Actor getSubstitutorActor() {
                return actor;
            }

            @Override
            public Set<Long> getIds() {
                return Sets.newHashSet(new Long(1));
            }
        } } };
    }

    @Test(dataProvider = "getSuccessTestcases")
    void successTests(String testName, int expected, TestCaseDataSet testCase) {

        log.info(String.format("start test: %s", testName));

        TaskLogicMockFactory.getFactory().setContextRules(testCase);

        int rules = taskListBuilder.checkSubstitutionRules(testCase.getCriteria(), testCase.getIds(), testCase.getExeContext(), testCase.getTask(),
                testCase.getAssignedActor(), testCase.getSubstitutorActor());

        Assert.assertEquals(rules, expected);

        TaskLogicMockFactory.getFactory().setContextRules(null);
    }
}
