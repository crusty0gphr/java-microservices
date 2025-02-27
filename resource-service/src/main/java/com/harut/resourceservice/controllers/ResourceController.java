package com.harut.resourceservice.controllers;

import com.harut.resourceservice.dto.DeleteResourceResponse;
import com.harut.resourceservice.dto.SaveResourceResponse;
import com.harut.resourceservice.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
	private final ResourceService resourceService;
	private final Logger logger = LogManager.getLogger(this.getClass());

	@PostMapping()
	public ResponseEntity<SaveResourceResponse> createResource(@RequestHeader("Content-Type") String contentType, @RequestBody byte[] file) {
		this.resourceService.verifyContentType(contentType);
		var metadata = this.resourceService.extractMetadata(file);
		var response = this.resourceService.saveResource(file);
		this.resourceService.sendMetadata(response.getId(), metadata);

		return ResponseEntity.ok(response);
	}

	@GetMapping(value = "/{id}", produces = "audio/mpeg")
	public ResponseEntity<byte[]> getResource(@PathVariable String id) {
		var response = this.resourceService.getById(id);
		var resource = response.getResource();

		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType("audio/mpeg"))
				.contentLength(resource.length)
				.body(resource);
	}

	@DeleteMapping
	public ResponseEntity<DeleteResourceResponse> deleteResource(@RequestParam(value = "id", required = false) String csv) {
		var response = this.resourceService.deleteAllByIds(csv);
		this.resourceService.deleteSongs(response.getIds());

		return ResponseEntity.ok(response);
	}
}
