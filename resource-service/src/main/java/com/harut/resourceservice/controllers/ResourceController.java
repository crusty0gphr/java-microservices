package com.harut.resourceservice.controllers;

import com.harut.resourceservice.dto.DeleteResourceResponse;
import com.harut.resourceservice.dto.SaveResourceResponse;
import com.harut.resourceservice.exceptions.BadRequestException;
import com.harut.resourceservice.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
	private final ResourceService resourceService;
	private final Logger logger = LogManager.getLogger(this.getClass());

	@PostMapping(consumes = "audio/mpeg")
	public ResponseEntity<SaveResourceResponse> createResource(@RequestBody byte[] file) {
		var metadata = this.resourceService.extractMetadata(file);
		var response = this.resourceService.saveResource(file);
		this.resourceService.sendMetadata(response.getId(), metadata);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}", produces = "audio/mpeg")
	public ResponseEntity<byte[]> getResource(@PathVariable Long id) {
		if (id <= 0) {
			throw new BadRequestException("Id must be a positive number");
		}

		var response = this.resourceService.getById(id);
		var resource = response.getResource();

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("audio/mpeg"))
				.contentLength(resource.length)
				.body(resource);
	}

	@DeleteMapping
	public ResponseEntity<DeleteResourceResponse> deleteResource(@RequestParam(value = "id", required = false) String csv) {
		if (csv == null || csv.isEmpty()) {
			throw new BadRequestException("No ids provided.");
		}

		if (csv.length() > 200) {
			throw new BadRequestException("Ids out of range");
		}

		Long[] ids = Arrays.stream(csv.split(","))
				.map(Long::valueOf)
				.toArray(Long[]::new);

		var response = this.resourceService.deleteAllByIds(ids);
		this.resourceService.deleteSongs(ids);

		return ResponseEntity.ok(response);
	}
}
