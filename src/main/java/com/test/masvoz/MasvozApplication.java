package com.test.masvoz;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.masvoz.model.Provider;
import com.test.masvoz.service.MessageDistribution;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MasvozApplication {

	public static void main(String[] args) throws IOException {

		ApplicationContext applicationContext = SpringApplication.run(MasvozApplication.class, args);
		MessageDistribution service = applicationContext.getBean(MessageDistribution.class);

		List<Provider> providers;

		String fuu = "";
		ClassPathResource classPathResource = new ClassPathResource("templates/providers.json");
		try {
			byte[] binaryData = FileCopyUtils.copyToByteArray(classPathResource.getInputStream());
			fuu = new String(binaryData, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		ObjectMapper mapper = new ObjectMapper();
		providers = Arrays.asList(mapper.readValue(fuu, Provider[].class));

		service.setupProvider(providers);

		// Logica para ejecutar escenarios de prueba
		if(args.length > 0 && args[0]!=null && args[1]!=null) {
			for(int i = 0; i < Integer.parseInt(args[0]); i++){
				System.out.println(service.processNumber(args[1], "Hello World"));
			}
			System.exit(0);
		}



	}

}
