package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.DispositivoModel;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class SalvarDispositivoService {
    DispositivoModel dispositivoModel = new DispositivoModel();
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public Response response;
    String baseUrl = "https://web-ecoconsumo-fiap-caawamh6gvc5e8cj.brazilsouth-01.azurewebsites.net";

    String schemasPath = "src/test/resources/schemas/";
    JSONObject jsonSchema;
    private final ObjectMapper mapper = new ObjectMapper();

    public void setFieldsDispositivo(String field, String value) {
        switch (field) {
            case "nome" -> dispositivoModel.setNome(value);
            case "dispositivo" -> dispositivoModel.setDispositivo(value);
            case "status" -> dispositivoModel.setStatus(value);
            case "nomeUnidade" -> dispositivoModel.setNomeUnidade(value);
            case "setorUnidade" -> dispositivoModel.setSetorUnidade(value);
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

    public void createDispositivo(String endpoint, String token) {
        String url = baseUrl + endpoint;
        String body = gson.toJson(dispositivoModel);
        response = given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .auth().oauth2(token)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    private JSONObject loadJsonFromFile(String filePath) throws IOException {
        try (InputStream inputStream = Files.newInputStream(Paths.get(filePath))) {
            JSONTokener tokener = new JSONTokener(inputStream);
            return new JSONObject(tokener);
        }
    }

    public void setContract(String contract) throws IOException {
        if (contract.equals("Salvar Dispositivo Sucesso")) {
            jsonSchema = loadJsonFromFile(schemasPath + "salvar-dispositivo-sucesso.json");
        } else {
            throw new IllegalStateException("Unexpected contract" + contract);
        }
    }

    public Set<ValidationMessage> validateResponseAgainstSchema() throws IOException {


        JSONObject jsonResponse = new JSONObject(response.getBody().asString());

        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V4);
        JsonSchema schema = schemaFactory.getSchema(jsonSchema.toString());

        JsonNode jsonResponseNode = mapper.readTree(jsonResponse.toString());

        return schema.validate(jsonResponseNode);

    }
}
