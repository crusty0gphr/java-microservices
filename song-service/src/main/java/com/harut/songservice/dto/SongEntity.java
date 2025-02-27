package com.harut.songservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harut.songservice.exceptions.BadRequestException;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Data
public class SongEntity {
	private Map<String, String> validationErrors = new HashMap<>();

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
			throw new BadRequestException("Validation failed.", this.validationErrors);
		}
	}

	private void validateName() {
		validateStringField("name", this.name, 100);
	}

	private void validateAlbum() {
		validateStringField("album", this.album, 100);
	}

	private void validateArtist() {
		validateStringField("artist", this.artist, 100);
	}

	private void validateDuration() {
		if (this.duration == null || this.duration.isEmpty()) {
			this.validationErrors.put("duration", "Duration is not provided.");
			return;
		}

		// Check format (mm:ss)
		if (!this.duration.matches("^\\d{2}:\\d{2}$")) {
			this.validationErrors.put("duration", "Duration must be in mm:ss format with leading zeros.");
			return;
		}

		String[] split = this.duration.split(":");
		int minutes = Integer.parseInt(split[0]);
		int seconds = Integer.parseInt(split[1]);

		// Validate minutes and seconds
		if (minutes >= 60 || seconds >= 60) {
			this.validationErrors.put("duration", "Duration must be in in mm:ss format with mm less then 60 and ss less then 60.");
		}
	}

	private void validateYear() {
		if (this.year == null || this.year.isEmpty()) {
			this.validationErrors.put("year", "Year is not provided.");
			return;
		}

		// Check format using regex
		if (!this.year.matches("^(19|20)\\d{2}$")) {
			this.validationErrors.put("year", "Year must be in the format YYYY.");
			return;
		}

		// Check numeric range
		int yearValue = Integer.parseInt(this.year);
		if (yearValue < 1900 || yearValue > 2099) {
			this.validationErrors.put("year", "Year must be between 1900 and 2099.");
		}
	}

	private void validateStringField(String fieldName, String fieldValue, int maxLength) {
		if (fieldValue == null || fieldValue.isEmpty()) {
			this.validationErrors.put(fieldName, StringUtils.capitalize(fieldName) + " is not provided.");
			return;
		}

		if (fieldValue.length() > maxLength) {
			this.validationErrors.put(fieldName, StringUtils.capitalize(fieldName) + " length must be must be between 1-100 characters long.");
		}
	}
}


