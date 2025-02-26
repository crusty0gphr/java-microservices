package com.harut.songservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harut.songservice.exceptions.BadRequestException;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SongEntity {
	private List<String> validationErrors = new ArrayList<>();

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

	public void validate() {
		this.validateName();
		this.validateAlbum();
		this.validateArtist();
		this.validateDuration();
		this.validateYear();

		if (!this.validationErrors.isEmpty()) {
			throw new BadRequestException(this.validationErrors.toString());
		}
	}

	private void validateName() {
		if (this.name == null) {
			this.validationErrors.add("Field name is not provided.");
		}

		if (this.name.length() > 100 || this.name.isEmpty()) {
			this.validationErrors.add("Field name must be 1-100 characters text.");
		}
	}

	private void validateDuration() {
		if (this.duration == null || this.album.isEmpty()) {
			this.validationErrors.add("Field artist is not provided.");
		}

		if (!this.duration.matches("^\\d{2}:\\d{2}$")) {
			this.validationErrors.add("Field year must be in the format YYYY.");
		}

		String[] split = this.duration.split(":");
		int minutes = Integer.parseInt(split[0]);
		int seconds = Integer.parseInt(split[1]);

		if (minutes >= 60 || seconds >= 60) {
			this.validationErrors.add("Field duration is invalid.");
		}
	}

	private void validateAlbum() {
		if (this.album == null) {
			this.validationErrors.add("Field album is not provided.");
		}

		if (this.album.length() > 100 || this.album.isEmpty()) {
			this.validationErrors.add("Field album must be 1-100 characters text.");
		}
	}

	private void validateArtist() {
		if (this.artist == null) {
			this.validationErrors.add("Field artist is not provided.");
		}

		if (this.artist.length() > 100 || this.artist.isEmpty()) {
			this.validationErrors.add("Field artist must be 1-100 characters text.");
		}
	}

	private void validateYear() {
		if (this.year == null) {
			this.validationErrors.add("Field year is not provided.");
		}

		if (!this.year.matches("^(19|20)\\d{2}$")) {
			this.validationErrors.add("Field year must be in the format YYYY.");
		}

		int val = Integer.parseInt(this.year);
		if (val < 1900 || val > 2099) {
			this.validationErrors.add("Field year must be in between 1900 and 2099.");
		}
	}
}


