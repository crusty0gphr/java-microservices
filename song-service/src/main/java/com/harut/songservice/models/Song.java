package com.harut.songservice.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Table(name = "songs")
@Data
@Entity
public class Song {
	@Id
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "artist")
	private String artist;

	@Column(name = "album")
	private String album;

	@Column(name = "duration")
	private String duration;

	@Column(name = "year")
	private String year;

	@Column(name = "dateCreated")
	private LocalTime dateCreated;
}
