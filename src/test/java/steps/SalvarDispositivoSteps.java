package steps;

import com.networknt.schema.ValidationMessage;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.E;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
import model.TokenModel;
import org.junit.Assert;
import services.AuthLoginService;
import services.SalvarDispositivoService;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SalvarDispositivoSteps {
    AuthLoginService authLoginService = new AuthLoginService();
    SalvarDispositivoService salvarDispositivoService = new SalvarDispositivoService();

    @Dado("que eu recupere o token retornado no contexto e salve o dispositivo:")
    public void queEuRecupereOTokenRetornadoNoContextoESalveODispositivo(List<Map<String, String>> rows) {
        for (Map<String, String> field : rows) {
            salvarDispositivoService.setFieldsDispositivo(field.get("campo"), field.get("valor"));
        }
    }

    @Quando("eu enviar a requisição para o endpoint {string} para salvar os dados de um dispositivo")
    public void euEnviarARequisicaoParaOEndpointParaSalvarOsDadosDeUmDispositivo(String endpoint) {
        salvarDispositivoService.createDispositivo(endpoint, authLoginService.getToken());
    }

    @Entao("status code do dispositivo criado deve ser {int}")
    public void statusCodeDoDispositivoCriadoDeveSer(int statusCode) {
        Assert.assertEquals(statusCode, salvarDispositivoService.response.statusCode());
    }

    @E("o contrato selecionado será {string}")
    public void oContratoSelecionadoSera(String contrato) throws IOException {
        salvarDispositivoService.setContract(contrato);
    }

    @Entao("a resposta da requisicao deve estar de acordo com o contrato")
    public void aRespostaDaRequisicaoDeveEstarDeAcordoComOContrato() throws IOException {
        Set<ValidationMessage> validateResponse = salvarDispositivoService.validateResponseAgainstSchema();
        Assert.assertTrue("O contrato está inválido. Erros encontrados: " + validateResponse, validateResponse.isEmpty());
    }
}
