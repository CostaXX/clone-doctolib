package com.example.api_medecin;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import com.example.api_medecin.config.JwtConfig;

@SpringBootTest
@Import(JwtConfig.class)
class ApiMedecinApplicationTests {

	@Test
	void contextLoads() {
	}

}
