package com.harut.resourceservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class DeleteResourceResponse {
	@JsonProperty("ids")
	private Long[] ids;
}
