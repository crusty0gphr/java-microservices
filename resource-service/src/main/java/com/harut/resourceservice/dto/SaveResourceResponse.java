package com.harut.resourceservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class SaveResourceResponse {
	@JsonProperty("id")
	private Long id;
}
