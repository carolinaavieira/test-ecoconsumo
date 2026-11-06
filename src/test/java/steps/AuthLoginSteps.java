package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import services.AuthLoginService;

import java.util.List;
import java.util.Map;

public class AuthLoginSteps {

    AuthLoginService authLoginService = new AuthLoginService();

    @Dado("que eu tenha o seguinte usuario:")
    public void queEuTenhaOSeguinteUsuario(List<Map<String, String>> rows) {
        for (Map<String, String> field : rows) {
            authLoginService.setFieldsAuthLogin(field.get("campo"), field.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} de auth login")
    public void euEnviarARequisicaoParaOEndpointDeAuthLogin(String endpoint) {
        authLoginService.accessLogin(endpoint);
        authLoginService.recuperarToken();
    }

    @Entao("status code deve ser {int}")
    public void statusCodeDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, authLoginService.response.statusCode());
    }
}
