package com.harut.songservice.controllers;

import com.harut.songservice.dto.DeleteSongsResponse;
import com.harut.songservice.dto.SongEntity;
import com.harut.songservice.dto.SongsResponse;
import com.harut.songservice.services.SongsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		var response = this.songsService.getById(id);
		return ResponseEntity.ok(response);
	}

	@DeleteMapping
	public ResponseEntity<DeleteSongsResponse> deleteSongs(@RequestParam(value = "id", required = false) String csv) {
		var response = this.songsService.deleteAllByIds(csv);
		return ResponseEntity.ok(response);
	}
}
