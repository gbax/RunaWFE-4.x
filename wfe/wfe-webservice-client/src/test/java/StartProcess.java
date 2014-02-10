import java.util.ArrayList;
import java.util.List;

import ru.runa.wfe.webservice.AuthenticationAPI;
import ru.runa.wfe.webservice.AuthenticationWebService;
import ru.runa.wfe.webservice.DefinitionAPI;
import ru.runa.wfe.webservice.DefinitionWebService;
import ru.runa.wfe.webservice.ExecutionAPI;
import ru.runa.wfe.webservice.ExecutionWebService;
import ru.runa.wfe.webservice.User;
import ru.runa.wfe.webservice.Variable;
import ru.runa.wfe.webservice.WfDefinition;

public class StartProcess {

    public static void main(String[] args) {
        try {
            AuthenticationAPI authenticationAPI = new AuthenticationWebService().getAuthenticationAPIPort();
            User user = authenticationAPI.authenticateByLoginPassword("Administrator", "wf");
            DefinitionAPI definitionAPI = new DefinitionWebService().getDefinitionAPIPort();
            ExecutionAPI executionAPI = new ExecutionWebService().getExecutionAPIPort();

            List<Variable> variables = new ArrayList<Variable>();

            Variable usersListVariable = new Variable();
            usersListVariable.setName("Список пользователей");
            usersListVariable.setValue("[{\"name\": \"julius\"}, {\"name\": \"nero\"}, {\"name\": \"Administrator\"}]");
            variables.add(usersListVariable);

            Long processId = executionAPI.startProcessWS(user, "messagingFailureTest", variables);
            System.out.println("Started process " + processId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
