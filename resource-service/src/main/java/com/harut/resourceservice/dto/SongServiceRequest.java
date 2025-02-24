package com.harut.resourceservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

@Setter
public class SongServiceRequest {
	@JsonProperty("id")
	private Long id;

	@JsonProperty("name")
	private String name;

	@JsonProperty("artist")
	private String artist;

	@JsonProperty("album")
	private String album;

	@JsonProperty("duration")
	private String duration;

	@JsonProperty("year")
	private String year;
}


