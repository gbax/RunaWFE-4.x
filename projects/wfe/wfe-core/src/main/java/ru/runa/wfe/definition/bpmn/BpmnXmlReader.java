package ru.runa.wfe.definition.bpmn;

import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.QName;
import org.springframework.beans.factory.annotation.Autowired;

import ru.runa.wfe.InternalApplicationException;
import ru.runa.wfe.commons.ApplicationContextFactory;
import ru.runa.wfe.commons.ClassLoaderUtil;
import ru.runa.wfe.commons.dao.LocalizationDAO;
import ru.runa.wfe.definition.InvalidDefinitionException;
import ru.runa.wfe.definition.ProcessDefinitionAccessType;
import ru.runa.wfe.definition.logic.SwimlaneUtils;
import ru.runa.wfe.job.CancelTimerAction;
import ru.runa.wfe.job.CreateTimerAction;
import ru.runa.wfe.job.Timer;
import ru.runa.wfe.lang.Action;
import ru.runa.wfe.lang.AsyncCompletionMode;
import ru.runa.wfe.lang.BaseTaskNode;
import ru.runa.wfe.lang.Delegation;
import ru.runa.wfe.lang.EmbeddedSubprocessEndNode;
import ru.runa.wfe.lang.EmbeddedSubprocessStartNode;
import ru.runa.wfe.lang.EndNode;
import ru.runa.wfe.lang.Event;
import ru.runa.wfe.lang.GraphElement;
import ru.runa.wfe.lang.InteractionNode;
import ru.runa.wfe.lang.MultiProcessState;
import ru.runa.wfe.lang.Node;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.lang.ReceiveMessage;
import ru.runa.wfe.lang.ScriptTask;
import ru.runa.wfe.lang.SendMessage;
import ru.runa.wfe.lang.StartState;
import ru.runa.wfe.lang.SubProcessState;
import ru.runa.wfe.lang.SubprocessDefinition;
import ru.runa.wfe.lang.SwimlaneDefinition;
import ru.runa.wfe.lang.TaskDefinition;
import ru.runa.wfe.lang.TaskNode;
import ru.runa.wfe.lang.Transition;
import ru.runa.wfe.lang.VariableContainerNode;
import ru.runa.wfe.lang.WaitState;
import ru.runa.wfe.lang.bpmn2.EndToken;
import ru.runa.wfe.lang.bpmn2.ExclusiveGateway;
import ru.runa.wfe.lang.bpmn2.ParallelGateway;
import ru.runa.wfe.var.VariableMapping;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@SuppressWarnings({ "unchecked" })
public class BpmnXmlReader {
    private static final String RUNA_NAMESPACE = "http://runa.ru/wfe/xml";
    private static final String PROCESS = "process";
    private static final String EXTENSION_ELEMENTS = "extensionElements";
    private static final String IS_EXECUTABLE = "isExecutable";
    private static final String PROPERTY = "property";
    private static final String END_EVENT = "endEvent";
    private static final String SERVICE_TASK = "serviceTask";
    private static final String SCRIPT_TASK = "scriptTask";
    private static final String TOKEN = "token";
    private static final String VARIABLES = "variables";
    private static final String VARIABLE = "variable";
    private static final String SOURCE_REF = "sourceRef";
    private static final String TARGET_REF = "targetRef";
    private static final String SUBPROCESS = "subProcess";
    private static final String MULTI_INSTANCE = "multiInstance";
    private static final String EXCLUSIVE_GATEWAY = "exclusiveGateway";
    private static final String PARALLEL_GATEWAY = "parallelGateway";
    private static final String DEFAULT_TASK_TIMEOUT = "default-task-timeout";
    private static final String USER_TASK = "userTask";
    private static final String START_EVENT = "startEvent";
    private static final String LANE_SET = "laneSet";
    private static final String LANE = "lane";
    private static final String FLOW_NODE_REF = "flowNodeRef";
    public static final String SHOW_WIMLANE = "showSwimlane";
    private static final String REASSIGN = "reassign";//
    private static final String CLASS = "class";
    private static final String SEQUENCE_FLOW = "sequenceFlow";
    private static final String DOCUMENTATION = "documentation";
    private static final String CONFIG = "config";
    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String MAPPED_NAME = "mappedName";
    private static final String USAGE = "usage";
    private static final String ID = "id";
    private static final String SEND_TASK = "sendTask";
    private static final String RECEIVE_TASK = "receiveTask";
    private static final String BOUNDARY_EVENT = "boundaryEvent";
    private static final String INTERMEDIATE_CATCH_EVENT = "intermediateCatchEvent";
    private static final String ATTACHED_TO_REF = "attachedToRef";
    private static final String TIMER_EVENT_DEFINITION = "timerEventDefinition";
    private static final String TIME_DURATION = "timeDuration";
    private static final String ASYNC = "async";
    private static final String ASYNC_COMPLETION_MODE = "asyncCompletionMode";
    private static final String ACCESS_TYPE = "accessType";
    private static final String EMBEDDED = "embedded";
    private static final String IGNORE_SUBSTITUTION_RULES = "ignoreSubstitutionRules";

    @Autowired
    private LocalizationDAO localizationDAO;

    private final Document document;

    private static Map<String, Class<? extends Node>> nodeTypes = Maps.newHashMap();
    static {
        nodeTypes.put(USER_TASK, TaskNode.class);
        nodeTypes.put(INTERMEDIATE_CATCH_EVENT, WaitState.class);
        nodeTypes.put(SEND_TASK, SendMessage.class);
        nodeTypes.put(RECEIVE_TASK, ReceiveMessage.class);
        // back compatibility v < 4.0.4
        nodeTypes.put(SERVICE_TASK, ScriptTask.class);
        nodeTypes.put(SCRIPT_TASK, ScriptTask.class);
        nodeTypes.put(EXCLUSIVE_GATEWAY, ExclusiveGateway.class);
        nodeTypes.put(PARALLEL_GATEWAY, ParallelGateway.class);
    }

    public BpmnXmlReader(Document document) {
        this.document = document;
    }

    public ProcessDefinition readProcessDefinition(ProcessDefinition processDefinition) {
        try {
            Element definitionsElement = document.getRootElement();
            Element process = definitionsElement.element(PROCESS);
            processDefinition.setName(process.attributeValue(NAME));
            Map<String, String> processProperties = parseExtensionProperties(process);
            processDefinition.setDescription(processProperties.get(DOCUMENTATION));
            String defaultTaskTimeout = processProperties.get(DEFAULT_TASK_TIMEOUT);
            if (!Strings.isNullOrEmpty(defaultTaskTimeout)) {
                // processDefinition.setDefaultTaskTimeoutDelay(new
                // Delay(defaultTaskTimeout));
            }
            String swimlaneDisplayModeName = processProperties.get(SHOW_WIMLANE);
            if (swimlaneDisplayModeName != null) {
                // definition.setSwimlaneDisplayMode(SwimlaneDisplayMode.valueOf(swimlaneDisplayModeName));
            }
            String accessTypeString = processProperties.get(ACCESS_TYPE);
            if (!Strings.isNullOrEmpty(accessTypeString)) {
                processDefinition.setAccessType(ProcessDefinitionAccessType.valueOf(accessTypeString));
            }
            if ("false".equals(process.attributeValue(IS_EXECUTABLE))) {
                throw new InvalidDefinitionException(processDefinition.getName(), "process is not executable");
            }

            // 1: read most content
            readSwimlanes(processDefinition, process);
            readNodes(processDefinition, process);

            // 2: processing transitions
            readTransitions(processDefinition, process);

            // 3: verify
            verifyElements(processDefinition);
        } catch (Exception e) {
            throw new InvalidDefinitionException(processDefinition.getName(), e);
        }
        return processDefinition;
    }

    private void readSwimlanes(ProcessDefinition processDefinition, Element processElement) {
        Element swimlaneSetElement = processElement.element(LANE_SET);
        if (swimlaneSetElement != null) {
            List<Element> swimlanes = swimlaneSetElement.elements(LANE);
            for (Element swimlaneElement : swimlanes) {
                String swimlaneName = swimlaneElement.attributeValue(NAME);
                if (swimlaneName == null) {
                    throw new InternalApplicationException("there's a swimlane without a name");
                }
                SwimlaneDefinition swimlaneDefinition = new SwimlaneDefinition();
                swimlaneDefinition.setNodeId(swimlaneElement.attributeValue(ID));
                swimlaneDefinition.setName(swimlaneName);
                swimlaneDefinition.setDelegation(readDelegation(swimlaneElement, true));
                SwimlaneUtils.setOrgFunctionLabel(swimlaneDefinition, localizationDAO);
                List<Element> flowNodeRefElements = swimlaneElement.elements(FLOW_NODE_REF);
                List<String> flowNodeIds = Lists.newArrayList();
                for (Element flowNodeRefElement : flowNodeRefElements) {
                    flowNodeIds.add(flowNodeRefElement.getTextTrim());
                }
                swimlaneDefinition.setFlowNodeIds(flowNodeIds);
                processDefinition.addSwimlane(swimlaneDefinition);
            }
        }
    }

    private void readNodes(ProcessDefinition processDefinition, Element parentElement) {
        List<Element> elements = parentElement.elements();
        for (Element element : elements) {
            String nodeName = element.getName();
            Node node = null;
            if (nodeTypes.containsKey(nodeName)) {
                node = ApplicationContextFactory.createAutowiredBean(nodeTypes.get(nodeName));
            } else if (START_EVENT.equals(nodeName)) {
                if (processDefinition instanceof SubprocessDefinition) {
                    node = ApplicationContextFactory.createAutowiredBean(EmbeddedSubprocessStartNode.class);
                } else {
                    node = ApplicationContextFactory.createAutowiredBean(StartState.class);
                }
            } else if (END_EVENT.equals(nodeName)) {
                Map<String, String> properties = parseExtensionProperties(element);
                if (properties.containsKey(TOKEN)) {
                    if (processDefinition instanceof SubprocessDefinition) {
                        node = ApplicationContextFactory.createAutowiredBean(EmbeddedSubprocessEndNode.class);
                    } else {
                        node = ApplicationContextFactory.createAutowiredBean(EndToken.class);
                    }
                } else {
                    node = ApplicationContextFactory.createAutowiredBean(EndNode.class);
                }
            } else if (SUBPROCESS.equals(nodeName)) {
                Map<String, String> properties = parseExtensionProperties(element);
                if (properties.containsKey(MULTI_INSTANCE)) {
                    node = ApplicationContextFactory.createAutowiredBean(MultiProcessState.class);
                } else {
                    node = ApplicationContextFactory.createAutowiredBean(SubProcessState.class);
                }
            }
            if (node != null) {
                node.setProcessDefinition(processDefinition);
                readNode(processDefinition, element, node);
            }
        }
    }

    private void readNode(ProcessDefinition processDefinition, Element element, Node node) {
        node.setNodeId(element.attributeValue(ID));
        node.setName(element.attributeValue(NAME));
        node.setDescription(element.elementTextTrim(DOCUMENTATION));
        processDefinition.addNode(node);

        if (node instanceof StartState) {
            StartState startState = (StartState) node;
            readTask(processDefinition, element, startState);
        }
        if (node instanceof TaskNode) {
            TaskNode taskNode = (TaskNode) node;
            readTask(processDefinition, element, taskNode);
            List<Element> boundaryEventElements = element.getParent().elements(BOUNDARY_EVENT);
            for (Element boundaryEventElement : boundaryEventElements) {
                String parentNodeId = boundaryEventElement.attributeValue(ATTACHED_TO_REF);
                if (Objects.equal(parentNodeId, taskNode.getNodeId())) {
                    readTimer(processDefinition, boundaryEventElement, taskNode);
                }
            }
        }
        if (node instanceof BaseTaskNode) {
            Map<String, String> properties = parseExtensionProperties(element);
            BaseTaskNode taskNode = (BaseTaskNode) node;
            if (properties.containsKey(ASYNC)) {
                taskNode.setAsync(Boolean.valueOf(properties.get(ASYNC)));
            }
            if (properties.containsKey(ASYNC_COMPLETION_MODE)) {
                taskNode.setCompletionMode(AsyncCompletionMode.valueOf(properties.get(ASYNC_COMPLETION_MODE)));
            }
        }
        if (node instanceof VariableContainerNode) {
            VariableContainerNode variableContainerNode = (VariableContainerNode) node;
            variableContainerNode.setVariableMappings(readVariableMappings(element));
        }
        if (node instanceof SubProcessState) {
            SubProcessState subProcessState = (SubProcessState) node;
            subProcessState.setSubProcessName(element.attributeValue(QName.get(PROCESS, RUNA_NAMESPACE)));
            Map<String, String> properties = parseExtensionProperties(element);
            if (properties.containsKey(EMBEDDED)) {
                subProcessState.setEmbedded(Boolean.parseBoolean(properties.get(EMBEDDED)));
            }
        }
        if (node instanceof ExclusiveGateway) {
            ExclusiveGateway gateway = (ExclusiveGateway) node;
            gateway.setDelegation(readDelegation(element, false));
        }
        if (node instanceof WaitState) {
            WaitState waitState = (WaitState) node;
            readTimer(processDefinition, element, waitState);
        }
        if (node instanceof ScriptTask) {
            ScriptTask serviceTask = (ScriptTask) node;
            serviceTask.setDelegation(readDelegation(element, true));
        }
        if (node instanceof SendMessage) {
            SendMessage sendMessage = (SendMessage) node;
            sendMessage.setTtlDuration(element.attributeValue(QName.get(TIME_DURATION, RUNA_NAMESPACE), "1 days"));
        }
    }

    private void readTimer(ProcessDefinition processDefinition, Element eventElement, GraphElement node) {
        Element timerElement = eventElement.element(TIMER_EVENT_DEFINITION);
        CreateTimerAction createTimerAction = ApplicationContextFactory.createAutowiredBean(CreateTimerAction.class);
        createTimerAction.setNodeId(eventElement.attributeValue(ID));
        String name = eventElement.attributeValue(NAME, node.getNodeId());
        createTimerAction.setName(name);
        String durationString = timerElement.elementTextTrim(TIME_DURATION);
        if (Strings.isNullOrEmpty(durationString) && node instanceof TaskNode && Timer.ESCALATION_NAME.equals(name)) {
            durationString = ((TaskNode) node).getFirstTaskNotNull().getDeadlineDuration();
            if (Strings.isNullOrEmpty(durationString)) {
                throw new InternalApplicationException("No '" + TIME_DURATION + "' specified for timer in " + node);
            }
        }
        createTimerAction.setDueDate(durationString);
        String createEventType = node instanceof TaskNode ? Event.TASK_CREATE : Event.NODE_ENTER;
        addAction(node, createEventType, createTimerAction);

        CancelTimerAction cancelTimerAction = ApplicationContextFactory.createAutowiredBean(CancelTimerAction.class);
        cancelTimerAction.setNodeId(createTimerAction.getNodeId());
        cancelTimerAction.setName(createTimerAction.getName());
        String cancelEventType = node instanceof TaskDefinition ? Event.TASK_END : Event.NODE_LEAVE;
        addAction(node, cancelEventType, cancelTimerAction);
    }

    private void addAction(GraphElement graphElement, String eventType, Action action) {
        Event event = graphElement.getEvent(eventType);
        if (event == null) {
            event = new Event(eventType);
            graphElement.addEvent(event);
        }
        action.setParent(graphElement);
        event.addAction(action);
    }

    private Map<String, String> parseExtensionProperties(Element element) {
        Map<String, String> map = Maps.newHashMap();
        Element extensionsElement = element.element(EXTENSION_ELEMENTS);
        if (extensionsElement != null) {
            List<Element> propertyElements = extensionsElement.elements(QName.get(PROPERTY, RUNA_NAMESPACE));
            for (Element propertyElement : propertyElements) {
                String name = propertyElement.attributeValue(NAME);
                String value = propertyElement.attributeValue(VALUE);
                if (value == null) {
                    value = propertyElement.getTextTrim();
                }
                map.put(name, value);
            }
        }
        return map;
    }

    private List<VariableMapping> readVariableMappings(Element element) {
        List<VariableMapping> list = Lists.newArrayList();
        Element extensionsElement = element.element(EXTENSION_ELEMENTS);
        if (extensionsElement != null) {
            Element variablesElement = extensionsElement.element(QName.get(VARIABLES, RUNA_NAMESPACE));
            if (variablesElement != null) {
                List<Element> variableElements = variablesElement.elements(QName.get(VARIABLE, RUNA_NAMESPACE));
                for (Element variableElement : variableElements) {
                    VariableMapping variableMapping = new VariableMapping(variableElement.attributeValue(NAME),
                            variableElement.attributeValue(MAPPED_NAME), variableElement.attributeValue(USAGE));
                    list.add(variableMapping);
                }
            }
        }
        return list;
    }

    private void readTransitions(ProcessDefinition processDefinition, Element processElement) {
        List<Element> elements = processElement.elements(SEQUENCE_FLOW);
        for (Element element : elements) {
            String id = element.attributeValue(ID);
            if (id == null) {
                throw new InternalApplicationException("transition without an '" + ID + "'-attribute");
            }
            String name = element.attributeValue(NAME);
            String from = element.attributeValue(SOURCE_REF);
            if (from == null) {
                throw new InternalApplicationException("transition '" + id + "' without a '" + SOURCE_REF + "'-attribute");
            }
            String to = element.attributeValue(TARGET_REF);
            if (to == null) {
                throw new InternalApplicationException("transition '" + id + "' without a '" + TARGET_REF + "'-attribute");
            }
            Transition transition = new Transition();
            transition.setNodeId(id);
            GraphElement sourceElement = processDefinition.getGraphElementNotNull(from);
            Node source;
            if (sourceElement instanceof Node) {
                source = (Node) sourceElement;
                if (source instanceof WaitState) {
                    source.getTimerActions(false).get(0).setTransitionName(name);
                    transition.setTimerTransition(true);
                }
            } else if (sourceElement instanceof CreateTimerAction) {
                CreateTimerAction createTimerAction = (CreateTimerAction) sourceElement;
                createTimerAction.setTransitionName(name);
                source = (Node) createTimerAction.getParent();
                transition.setTimerTransition(true);
            } else {
                throw new InternalApplicationException("Unexpected source element " + sourceElement);
            }
            transition.setFrom(source);
            Node target = processDefinition.getNodeNotNull(to);
            transition.setTo(target);
            transition.setName(name);
            transition.setDescription(element.elementTextTrim(DOCUMENTATION));
            transition.setProcessDefinition(processDefinition);
            // add the transition to the node
            source.addLeavingTransition(transition);
            // set destinationNode of the transition
            target.addArrivingTransition(transition);
            // read the actions
            // readActions(processDefinition, element, transition,
            // Event.EVENTTYPE_TRANSITION);
        }
    }

    // private int getOutgoingTransitionsCount(Element processDefinitionElement,
    // String sourceNodeId) {
    // int count = 0;
    // List<Element> elements =
    // processDefinitionElement.elements(SEQUENCE_FLOW);
    // for (Element element : elements) {
    // String from = element.attributeValue(SOURCE_REF);
    // if (Objects.equal(from, sourceNodeId)) {
    // count++;
    // }
    // }
    // return count;
    // }

    private void readTask(ProcessDefinition processDefinition, Element element, InteractionNode node) {
        if (node instanceof EmbeddedSubprocessStartNode) {
            return;
        }
        TaskDefinition taskDefinition = new TaskDefinition();
        taskDefinition.setNodeId(node.getNodeId());
        taskDefinition.setProcessDefinition(processDefinition);
        taskDefinition.setName(node.getName());
        taskDefinition.setDescription(node.getDescription());
        node.addTask(taskDefinition);
        // assignment
        Map<String, String> properties = parseExtensionProperties(element);
        String swimlaneName = properties.get(LANE);
        SwimlaneDefinition swimlaneDefinition = processDefinition.getSwimlaneNotNull(swimlaneName);
        taskDefinition.setSwimlane(swimlaneDefinition);
        if (properties.containsKey(REASSIGN)) {
            taskDefinition.setReassignSwimlane(Boolean.parseBoolean(properties.get(REASSIGN)));
        }
        if (properties.containsKey(IGNORE_SUBSTITUTION_RULES)) {
            taskDefinition.setReassignSwimlane(Boolean.parseBoolean(properties.get(IGNORE_SUBSTITUTION_RULES)));
        }
    }

    private Delegation readDelegation(Element element, boolean required) {
        Map<String, String> swimlaneProperties = parseExtensionProperties(element);
        String className = swimlaneProperties.get(CLASS);
        if (className == null) {
            if (required) {
                throw new InternalApplicationException("no className specified in " + element.asXML());
            }
            return null;
        }
        ClassLoaderUtil.instantiate(className);
        String configuration = swimlaneProperties.get(CONFIG);
        return new Delegation(className, configuration);
    }

    private void verifyElements(ProcessDefinition processDefinition) {
        for (Node node : processDefinition.getNodes(false)) {
            node.validate();
        }
    }
}
