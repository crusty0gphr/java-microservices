package com.harut.songservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

@Data
public class SongEntity {
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


