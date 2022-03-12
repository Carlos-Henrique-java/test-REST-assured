package br.com.uniesp.test;

import br.com.uniesp.entidate.PessoaRequest;
import br.com.uniesp.entidate.PessoaResponse;
import br.com.uniesp.servicos.Servicos;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class TestRest {

	@BeforeEach
	public void configuraApi() {
		baseURI = "https://reqres.in/api";
	}


	@Test
	void methodDelete() {
		given()
				.when()
				.contentType(ContentType.JSON)
				.delete("users/2")
				.then()
				.statusCode(HttpStatus.SC_NO_CONTENT);

	}

	@Test
	void methodGet() {
		given()
				.when()
				.get(Servicos.getUsers_ID.getValor(), 2)
				.then().contentType(ContentType.JSON)
				.statusCode(HttpStatus.SC_OK)
				.and()
				.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/thiagoExample.json"))
				.log().all();
	}
	@Test
	public void methodPost() {
		PessoaRequest pessoaRequest = new PessoaRequest("henrique", "desenvolvedor");

		basePath= "/api/users";
		PessoaResponse as = given()
				.contentType("application/json")
				.body(pessoaRequest)
				.when()
				.post("/")
				.then()
				.statusCode(HttpStatus.SC_CREATED)
				.extract().response().as(PessoaResponse.class);

		Assertions.assertNotNull(as);
		Assertions.assertNotNull(as.getJob());
		Assertions.assertEquals(as.getNome(), as.getNome());
		Assertions.assertEquals(as.getJob(), as.getJob());
	}

	@Test
	public  void methodPut(){

		PessoaRequest pessoaRequest = new PessoaRequest("henrique","desenvolvedor");
		given().contentType(ContentType.JSON)
				.body(pessoaRequest)
				.when().put("users/2")
				.then().statusCode(HttpStatus.SC_OK)
				.log().all();

	}

}