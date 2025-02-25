package com.harut.songservice.controllers;

import com.harut.songservice.dto.SongEntity;
import com.harut.songservice.dto.SongsResponse;
import com.harut.songservice.exceptions.BadRequestException;
import com.harut.songservice.services.SongsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/songs")
@RequiredArgsConstructor
public class SongsController {

	private final SongsService songsService;

	@PostMapping
	public ResponseEntity<SongsResponse> createSong(@RequestBody SongEntity song) {
		var result = this.songsService.saveSong(song);

		return ResponseEntity.ok(result);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SongEntity> getSongById(@PathVariable Long id) {
		if (id <= 0) {
			throw new BadRequestException("Id must be a positive number");
		}

		var response = this.songsService.getById(id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping
	public ResponseEntity<Long[]> deleteSongs(@RequestParam(value = "id", required = false) String csv) {
		if (csv == null || csv.isEmpty()) {
			throw new com.harut.resourceservice.exceptions.BadRequestException("No ids provided.");
		}

		if (csv.length() > 200) {
			throw new com.harut.resourceservice.exceptions.BadRequestException("Ids out of range");
		}

		Long[] ids = Arrays.stream(csv.split(","))
				.map(Long::valueOf)
				.toArray(Long[]::new);

		this.songsService.deleteAllByIds(ids);

		return ResponseEntity.ok(ids);
	}
}
