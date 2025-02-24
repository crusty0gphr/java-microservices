package com.harut.resourceservice.controllers;

import com.harut.resourceservice.dto.GetResourceResponse;
import com.harut.resourceservice.dto.SaveResourceResponse;
import com.harut.resourceservice.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/{id}")
	public ResponseEntity<GetResourceResponse> getResource(@PathVariable Long id) {
		var response = this.resourceService.getResourceById(id);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{ids}")
	public ResponseEntity<Boolean> deleteResource(@PathVariable Long[] ids) {
		this.resourceService.deleteByIds(ids);

		return ResponseEntity.ok(true);
	}
}
