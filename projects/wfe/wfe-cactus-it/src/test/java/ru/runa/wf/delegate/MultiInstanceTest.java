package ru.runa.wf.delegate;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cactus.ServletTestCase;

import ru.runa.junit.ArrayAssert;
import ru.runa.wf.service.WfServiceTestHelper;
import ru.runa.wfe.presentation.BatchPresentationFactory;
import ru.runa.wfe.relation.Relation;
import ru.runa.wfe.service.ExecutionService;
import ru.runa.wfe.task.dto.WfTask;
import ru.runa.wfe.user.Actor;
import ru.runa.wfe.user.Group;
import ru.runa.wfe.user.User;

import com.google.common.collect.Lists;

public class MultiInstanceTest extends ServletTestCase {
    private WfServiceTestHelper th;

    private Group group1 = null;
    private Relation relation1 = null;
    private Actor actor1 = null;
    private Actor actor2 = null;
    private Actor actor3 = null;
    private Actor actor4 = null;
    private ExecutionService executionService;

    @Override
    protected void setUp() throws Exception {
        th = new WfServiceTestHelper(MultiInstanceTest.class.getName());
        executionService = th.getExecutionService();
        group1 = th.createGroupIfNotExist("group1", MultiInstanceTest.class.getName());
        relation1 = th.createRelation("relation1", MultiInstanceTest.class.getName());
        actor1 = th.createActorIfNotExist("actor1", MultiInstanceTest.class.getName());
        th.addExecutorToGroup(actor1, group1);
        actor2 = th.createActorIfNotExist("actor2", MultiInstanceTest.class.getName());
        th.addExecutorToGroup(actor2, group1);
        actor3 = th.createActorIfNotExist("actor3", MultiInstanceTest.class.getName());
        th.addExecutorToGroup(actor3, group1);
        actor4 = th.createActorIfNotExist("relationparam1", MultiInstanceTest.class.getName());
        th.addRelationPair(relation1.getId(), actor4, actor1);
        th.addRelationPair(relation1.getId(), actor4, actor2);
        th.addRelationPair(relation1.getId(), actor4, actor3);
        th.deployValidProcessDefinition("multiinstance superprocess.par");
        th.deployValidProcessDefinition("multiinstance subprocess.par");
        th.deployValidProcessDefinition("MultiInstance - MainProcess.par");
        th.deployValidProcessDefinition("MultiInstance - SubProcess.par");
        th.deployValidProcessDefinition("MultiInstance - TypeMainProcess.par");
    }

    @Override
    protected void tearDown() throws Exception {
        if (relation1 != null) {
            th.removeRelation(relation1.getId());
        }
        th.undeployValidProcessDefinition("MultiInstance - MainProcess");
        th.undeployValidProcessDefinition("MultiInstance - TypeMainProcess");
        th.undeployValidProcessDefinition("MultiInstance - SubProcess");
        th.undeployValidProcessDefinition("multiinstance superprocess");
        th.undeployValidProcessDefinition("multiinstance subprocess");
        th.releaseResources();
        super.tearDown();
    }

    public void testSimple() throws Exception {
        User user = th.getAdminUser();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("discriminator", new String[] { "d1", "d2", "d3" });
        variables.put("discriminator_r", new String[] { "d1_r", "d2_r", "d3_r" });
        variables.put("discriminator_rw", new String[] { "d1_rw", "d2_rw", "d3_rw" });
        long processId = executionService.startProcess(user, "multiinstance superprocess", variables);
        List<WfTask> tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        for (WfTask task : tasks) {
            String descriminatorValue = (String) executionService.getVariable(user, task.getProcessId(), "d").getValue();
            assertEquals(descriminatorValue + "_r", (String) executionService.getVariable(user, task.getProcessId(), "d_r").getValue());
            assertEquals(descriminatorValue + "_rw", (String) executionService.getVariable(user, task.getProcessId(), "d_rw").getValue());
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
        }
        ArrayAssert.assertEqualArrays("discriminator", new String[] { "d1", "d2", "d3" },
                (Object[]) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays("discriminator_r", new String[] { "d1_r", "d2_r", "d3_r" }, (Object[]) executionService
                .getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_r").values().iterator().next().getValue());
        ArrayAssert.assertEqualArrays("discriminator_w", Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_w").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays("discriminator_rw", Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_rw").values().iterator()
                        .next().getValue());
    }

    public void testSimpleWithLists() throws Exception {
        User user = th.getAdminUser();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("discriminator", Lists.newArrayList("d1", "d2", "d3"));
        variables.put("discriminator_r", Lists.newArrayList("d1_r", "d2_r", "d3_r"));
        variables.put("discriminator_rw", Lists.newArrayList("d1_rw", "d2_rw", "d3_rw"));
        long processId = executionService.startProcess(user, "multiinstance superprocess", variables);
        List<WfTask> tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        for (WfTask task : tasks) {
            String descriminatorValue = (String) executionService.getVariable(user, task.getProcessId(), "d").getValue();
            assertEquals(descriminatorValue + "_r", (String) executionService.getVariable(user, task.getProcessId(), "d_r").getValue());
            assertEquals(descriminatorValue + "_rw", (String) executionService.getVariable(user, task.getProcessId(), "d_rw").getValue());
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
        }
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator").values().iterator().next()
                        .getValue());
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1_r", "d2_r", "d3_r"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_r").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_w").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_rw").values().iterator()
                        .next().getValue());
    }

    public void testNullDiscriminator() throws Exception {
        User user = th.getAdminUser();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("discriminator", new String[] { "d1", "d2", "d3" });
        variables.put("discriminator_r", new String[] { "d1_r", "d2_r", "d3_r" });
        long processId = executionService.startProcess(user, "multiinstance superprocess", variables);
        List<WfTask> tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        for (WfTask task : tasks) {
            String descriminatorValue = (String) executionService.getVariable(user, task.getProcessId(), "d").getValue();
            assertEquals(descriminatorValue + "_r", (String) executionService.getVariable(user, task.getProcessId(), "d_r").getValue());
            assertEquals(null, (String) executionService.getVariable(user, task.getProcessId(), "d_rw").getValue());
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
        }
        ArrayAssert.assertEqualArrays("", new String[] { "d1", "d2", "d3" },
                (Object[]) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays("", new String[] { "d1_r", "d2_r", "d3_r" },
                (Object[]) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_r").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_w").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_rw").values().iterator()
                        .next().getValue());
    }

    public void testEmptyDiscriminator() throws Exception {
        User user = th.getAdminUser();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("discriminator", new String[] {});
        variables.put("discriminator_r", new String[] {});
        Long processId = executionService.startProcess(user, "multiinstance superprocess", variables);
        System.out.println("multiinstancesubprocesses=" + executionService.getSubprocesses(user, processId, true));
        assertTrue(executionService.getProcess(user, processId).isEnded());
    }

    // public void testNullDiscriminator2() throws Exception {
    // User user = th.getAdminUser();
    // Map<String, Object> variables = new HashMap<String, Object>();
    // variables.put("discriminator", new String[] {});
    // variables.put("discriminator_r", new String[] {});
    // if (SystemProperties.isV3CompatibilityMode()) {
    // Long processId = executionService.startProcess(user,
    // "multiinstance superprocess", variables);
    // System.out.println("multiinstancesubprocesses=" +
    // executionService.getSubprocessesRecursive(user, processId));
    // assertTrue(executionService.getProcess(user, processId).isEnded());
    // } else {
    // try {
    // executionService.startProcess(user, "multiinstance superprocess",
    // variables);
    // fail("No exception on null discriminator");
    // } catch (RuntimeException e) {
    // Assert.assertEquals("discriminatorValue == null", e.getMessage());
    // }
    // }
    // }

    public void testManySubprocessInToken() throws Exception {
        User user = th.getAdminUser();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("Variable1", "Variable for subprocess 1");
        variables.put("Variable2", "Variable for subprocess 2");
        variables.put("multi", new String[] { "sub-mult 1", "sub-mult 2", "sub-mult 3" });
        long processId = executionService.startProcess(user, "MultiInstance - MainProcess", variables);

        List<WfTask> tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(1, tasks.size());
        assertEquals("Variable for subprocess 1", (String) executionService.getVariable(user, tasks.get(0).getProcessId(), "Variable1").getValue());
        executionService.completeTask(user, tasks.get(0).getId(), new HashMap<String, Object>(), null);

        tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        Collections.sort(tasks, new Comparator<WfTask>() {
            @Override
            public int compare(WfTask o1, WfTask o2) {
                return o1.getId() < o2.getId() ? -1 : o1.getId() == o2.getId() ? 0 : 1;
            }
        });
        int idx = 1;
        for (WfTask task : tasks) {
            String descriminatorValue = (String) executionService.getVariable(user, task.getProcessId(), "Variable1").getValue();
            assertEquals("sub-mult " + idx, descriminatorValue);
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
            ++idx;
        }

        tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        Collections.sort(tasks, new Comparator<WfTask>() {
            @Override
            public int compare(WfTask o1, WfTask o2) {
                return o1.getId() < o2.getId() ? -1 : o1.getId() == o2.getId() ? 0 : 1;
            }
        });
        idx = 1;
        for (WfTask task : tasks) {
            String descriminatorValue = (String) executionService.getVariable(user, task.getProcessId(), "Variable1").getValue();
            assertEquals("sub-mult " + idx, descriminatorValue);
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
            ++idx;
        }

        tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(1, tasks.size());
        assertEquals("Variable for subprocess 2", (String) executionService.getVariable(user, tasks.get(0).getProcessId(), "Variable1").getValue());
        executionService.completeTask(user, tasks.get(0).getId(), new HashMap<String, Object>(), null);

        ArrayAssert.assertEqualArrays(Lists.newArrayList("sub-mult 1", "sub-mult 2", "sub-mult 3"), (List<?>) executionService
                .getVariablesFromProcesses(user, Lists.newArrayList(processId), "multiOut").values().iterator().next().getValue());
    }

    public void testDifferentTypes() throws Exception {
        // internalDifferentTypes(new Date());
        internalDifferentTypes(new Long(1));
        internalDifferentTypes(new Double(1));
    }

    public void testAllTypes() throws Exception {
        User user = th.getAdminUser();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("Variable1", "group1");
        variables.put("Variable2", "relation1");
        variables.put("Variable3", "relationparam1");
        variables.put("multi", new String[] { "sub-mult 1", "sub-mult 2", "sub-mult 3" });
        executionService.startProcess(user, "MultiInstance - TypeMainProcess", variables);

        List<WfTask> tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        Collections.sort(tasks, new Comparator<WfTask>() {
            @Override
            public int compare(WfTask o1, WfTask o2) {
                return o1.getId() < o2.getId() ? -1 : o1.getId() == o2.getId() ? 0 : 1;
            }
        });
        int idx = 1;
        for (WfTask task : tasks) {
            String discriminatorValue = (String) executionService.getVariable(user, task.getProcessId(), "Variable1").getValue();
            assertEquals("sub-mult " + idx, discriminatorValue);
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
            ++idx;
        }

        List<Actor> actorList = Lists.newArrayList(actor1, actor2, actor3);
        tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        idx = 1;
        for (WfTask task : tasks) {
            Actor discriminatorValue = (Actor) executionService.getVariable(user, task.getProcessId(), "Variable1").getValue();
            assertTrue(actorList.contains(discriminatorValue));
            actorList.remove(actorList.indexOf(discriminatorValue));
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
            ++idx;
        }
        assertTrue(actorList.isEmpty());

        actorList.add(actor1);
        actorList.add(actor2);
        actorList.add(actor3);
        tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        idx = 1;
        for (WfTask task : tasks) {
            Actor discriminatorValue = (Actor) executionService.getVariable(user, task.getProcessId(), "Variable1").getValue();
            assertTrue(actorList.contains(discriminatorValue));
            actorList.remove(actorList.indexOf(discriminatorValue));
            executionService.completeTask(user, task.getId(), new HashMap<String, Object>(), null);
            ++idx;
        }
        assertTrue(actorList.isEmpty());
    }

    private void internalDifferentTypes(Object varValue) throws Exception {
        User user = th.getAdminUser();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("discriminator", new String[] { "d1", "d2", "d3" });
        variables.put("discriminator_r", new String[] { "d1_r", "d2_r", "d3_r" });
        variables.put("discriminator_rw", new String[] { "d1_rw", "d2_rw", "d3_rw" });
        long processId = executionService.startProcess(user, "multiinstance superprocess", variables);
        List<WfTask> tasks = executionService.getTasks(user, BatchPresentationFactory.TASKS.createDefault());
        assertEquals(3, tasks.size());
        for (WfTask task : tasks) {
            String descriminatorValue = (String) executionService.getVariable(user, task.getProcessId(), "d").getValue();
            assertEquals(descriminatorValue + "_r", (String) executionService.getVariable(user, task.getProcessId(), "d_r").getValue());
            assertEquals(descriminatorValue + "_rw", (String) executionService.getVariable(user, task.getProcessId(), "d_rw").getValue());
            Map<String, Object> var = new HashMap<String, Object>();
            var.put("d_rw", varValue);
            var.put("d_w", varValue);
            executionService.completeTask(user, task.getId(), var, null);
        }
        ArrayAssert.assertEqualArrays("", new String[] { "d1", "d2", "d3" },
                (Object[]) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays("", new String[] { "d1_r", "d2_r", "d3_r" },
                (Object[]) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_r").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_w").values().iterator()
                        .next().getValue());
        ArrayAssert.assertEqualArrays(Lists.newArrayList("d1", "d2", "d3"),
                (List<?>) executionService.getVariablesFromProcesses(user, Lists.newArrayList(processId), "discriminator_rw").values().iterator()
                        .next().getValue());
    }
}
