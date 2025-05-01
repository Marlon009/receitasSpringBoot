package com.edu.fatec.pg.receitas;

import com.edu.fatec.pg.receitas.dto.RecipeDTO;
import com.edu.fatec.pg.receitas.model.ReceitasModel;
import com.edu.fatec.pg.receitas.repository.ReceitasInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

@SpringBootApplication
public class ReceitasjpaApplication implements CommandLineRunner {

	@Autowired
	private ReceitasInterface interfac;

	public static void main(String[] args) {
		SpringApplication.run(ReceitasjpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Digite o id da receita: ");
		int id = scanner.nextInt();
		scanner.nextLine();

		String url = "https://dummyjson.com/recipes/" + id;
		String json = obterDados(url);

		ObjectMapper mapper = new ObjectMapper();
		RecipeDTO dto = mapper.readValue(json, RecipeDTO.class);

		System.out.println("Receita encontrada: ");
		System.out.println(dto);

		System.out.println("Deseja salvar essa receita no banco de dados? (s/n)");
		String resposta = scanner.nextLine();

		if (resposta.equalsIgnoreCase("s")) {
			if (interfac.existsById(dto.getId())) {
				System.out.println("Essa receita já foi salva anteriormente.");
			} else {
				ReceitasModel receita = new ReceitasModel(
						dto.getId(),
						dto.getName(),
						dto.getIngredients(),
						dto.getPrepTimeMinutes(),
						dto.getCookTimeMinutes(),
						dto.getServings(),
						dto.getDifficulty()
				);
				interfac.save(receita);
				System.out.println("Receita salva com sucesso!");
			}
		}
		System.out.println("\nReceitas salvas no banco:");
		interfac.findAll().forEach(System.out::println);
	}

	public static String obterDados(String url) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();

		HttpResponse<String> response = client
				.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}
}
