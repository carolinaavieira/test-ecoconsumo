package services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.UnidadeModel;

import static io.restassured.RestAssured.given;

public class SalvarUnidadeService {
    UnidadeModel unidadeModel = new UnidadeModel();
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    public Response response;
    String baseUrl = "https://web-ecoconsumo-fiap-caawamh6gvc5e8cj.brazilsouth-01.azurewebsites.net";

    public void setFieldsDispositivo(String field, String value) {
        switch (field) {
            case "nome" -> unidadeModel.setNome(value);
            case "tpUnidade" -> unidadeModel.setTpUnidade(value);
            default -> throw new IllegalStateException("Unexpected field: " + field);
        }
    }

    public void createUnidade(String endpoint, String token) {
        String url = baseUrl + endpoint;
        String body = gson.toJson(unidadeModel);
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
}
