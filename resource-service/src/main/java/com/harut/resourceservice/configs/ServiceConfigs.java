package com.harut.resourceservice.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class ServiceConfigs {
	@Value("${external.services.song-service-host}")
	private String songServiceUrl;
}
