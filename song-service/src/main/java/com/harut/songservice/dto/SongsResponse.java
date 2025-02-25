package com.harut.songservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class SongsResponse {
	@JsonProperty("id")
	private Long id;
}
