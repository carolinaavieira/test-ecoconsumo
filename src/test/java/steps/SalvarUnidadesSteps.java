package steps;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import org.junit.Assert;
import services.AuthLoginService;
import services.SalvarUnidadeService;

import java.util.List;
import java.util.Map;

public class SalvarUnidadesSteps {
    AuthLoginService authLoginService = new AuthLoginService();
    SalvarUnidadeService salvarUnidadeService = new SalvarUnidadeService();

    @Dado("que eu recupere o token retornado no contexto e salve a unidade:")
    public void queEuRecupereOTokenRetornadoNoContextoESalveAUnidade(List<Map<String, String>> rows) {
        for (Map<String, String> field : rows) {
            salvarUnidadeService.setFieldsDispositivo(field.get("campo"), field.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} para salvar os dados de uma unidade")
    public void euEnviarARequisicaoParaOEndpointParaSalvarOsDadosDeUmaUnidade(String endpoint) {
        salvarUnidadeService.createUnidade(endpoint, authLoginService.getToken());
    }

    @Entao("status code da unidade criada deve ser {int}")
    public void statusCodeDaUnidadeCriadaDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, salvarUnidadeService.response.statusCode());
    }
}
