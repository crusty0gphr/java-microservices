package com.harut.resourceservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteResourceResponse {
	@JsonProperty("ids")
	private Long[] ids;
}
