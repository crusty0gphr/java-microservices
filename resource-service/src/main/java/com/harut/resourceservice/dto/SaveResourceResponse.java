package com.harut.resourceservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Setter;

@Data
public class SaveResourceResponse {
	@JsonProperty("id")
	private Long id;
}
