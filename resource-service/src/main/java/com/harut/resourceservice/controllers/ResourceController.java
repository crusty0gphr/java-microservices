package com.harut.resourceservice.controllers;

import com.harut.resourceservice.dto.GetResourceResponse;
import com.harut.resourceservice.dto.SaveResourceResponse;
import com.harut.resourceservice.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resources")
@RequiredArgsConstructor
public class ResourceController {
	private final ResourceService resourceService;

	@PostMapping(consumes = "audio/mpeg")
	public ResponseEntity<SaveResourceResponse> createResource(@RequestBody byte[] file) {
		var metadata = this.resourceService.extractMetadata(file);
		this.resourceService.sendMetadata(metadata);
		var response = this.resourceService.saveResource(file);

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
