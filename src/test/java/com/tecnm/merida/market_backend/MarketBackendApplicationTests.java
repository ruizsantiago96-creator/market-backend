package com.tecnm.merida.market_backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "auth.jwt.secret=${random.uuid}${random.uuid}")
class MarketBackendApplicationTests {

	@Test
	void contextLoads() {
	}

}
