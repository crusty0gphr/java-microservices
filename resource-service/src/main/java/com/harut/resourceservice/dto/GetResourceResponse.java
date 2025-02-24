package com.harut.resourceservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

import java.time.LocalTime;

@Setter
public class GetResourceResponse {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("resource")
	private byte[] resource;

	@JsonProperty("dateCreated")
	private LocalTime dateCreated;
}
