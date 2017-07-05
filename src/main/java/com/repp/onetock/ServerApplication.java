package com.repp.onetock;

import com.opentok.OpenTok;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	@Value("${onetok.api.key}")
	int apiKey;

	@Value("${onetok.api.secret}")
	String apiSecret;

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	OpenTok openTok(){
		return new OpenTok(apiKey, apiSecret);
	}
}
