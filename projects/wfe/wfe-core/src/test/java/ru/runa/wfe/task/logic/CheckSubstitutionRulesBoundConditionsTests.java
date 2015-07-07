package ru.runa.wfe.task.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = { "classpath:ru/runa/wfe/task/logic/test.context.xml" })
public class CheckSubstitutionRulesBoundConditionsTests extends AbstractTestNGSpringContextTests {

    @Autowired
    ITaskListBuilderTestProvider taskListBuilder;

    @Test()
    void test1() {

    }

}
