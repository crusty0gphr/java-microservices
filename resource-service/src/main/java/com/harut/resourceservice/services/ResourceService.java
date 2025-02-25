package com.harut.resourceservice.services;

import com.harut.resourceservice.configs.ServiceConfigs;
import com.harut.resourceservice.dto.GetResourceResponse;
import com.harut.resourceservice.dto.SaveResourceResponse;
import com.harut.resourceservice.dto.SongsServiceRequest;
import com.harut.resourceservice.dto.SongsServiceResponse;
import com.harut.resourceservice.exceptions.BadRequestException;
import com.harut.resourceservice.exceptions.EntityNotFoundException;
import com.harut.resourceservice.exceptions.ProcessingException;
import com.harut.resourceservice.models.Resource;
import com.harut.resourceservice.repos.ResourceRepo;
import com.harut.resourceservice.utils.ResourceUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ResourceService {
	private final ServiceConfigs configs;
	private final RestTemplate restTemplate;
	private final ResourceRepo resourceRepo;

	public SaveResourceResponse saveResource(byte[] file) {
		Resource model = new Resource();
		model.setResource(file);
		model.setDateCreated(LocalTime.now());

		Resource saved = this.resourceRepo.save(model);
		SaveResourceResponse result = new SaveResourceResponse();
		result.setId(saved.getId());

		return result;
	}

	public GetResourceResponse getById(Long id) {
		Resource resource = this.resourceRepo
				.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Failed retrieving resource by id: " + id));

		GetResourceResponse result = new GetResourceResponse();
		result.setId(resource.getId());
		result.setResource(resource.getResource());
		result.setDateCreated(resource.getDateCreated());

		return result;
	}

	public void deleteAllByIds(Long[] ids) {
		this.resourceRepo.deleteAllById(List.of(ids));
	}

	public Map<String, String> extractMetadata(byte[] file) {
		try (ByteArrayInputStream inputStream = new ByteArrayInputStream(file)) {
			BodyContentHandler bodyContentHandler = new BodyContentHandler();
			Metadata metadataRaw = new Metadata();
			ParseContext context = new ParseContext();

			Mp3Parser parser = new Mp3Parser();
			parser.parse(
					inputStream,
					bodyContentHandler,
					metadataRaw,
					context
			);

			Map<String, String> metadata = ResourceUtils.extractMetadata(metadataRaw);

			if (metadata.isEmpty()) {
				throw new BadRequestException("Metadata extraction failed: No metadata found in the file.");
			}

			return metadata;

		} catch (TikaException | SAXException | IOException ex) {
			throw new ProcessingException("An error occurred while processing the MP3 file: " + ex.getMessage(), ex);
		}
	}

	public SongsServiceResponse sendMetadata(Long resourceId, Map<String, String> metadata) {
		String url = this.configs.getSongServiceUrl() + "/songs";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		SongsServiceRequest data = new SongsServiceRequest();
		data.setId(resourceId);
		data.setName(metadata.get("title"));
		data.setArtist(metadata.get("artist"));
		data.setYear(metadata.get("releaseDate"));
		data.setAlbum(metadata.get("album"));
		data.setDuration(metadata.get("duration"));

		HttpEntity<SongsServiceRequest> entity = new HttpEntity<>(data, headers);
		ResponseEntity<SongsServiceResponse> response = restTemplate.postForEntity(url, entity, SongsServiceResponse.class);

		if (response.getStatusCode().is2xxSuccessful()){
			return response.getBody();
		} else {
			throw new BadRequestException("Failed sending metadata: " + response.getStatusCode());
		}
    }

	public void deleteSongs(Long[] ids) {
		String queryParams = "?id=" + String.join(",", Arrays.stream(ids)
				.map(String::valueOf)
				.toArray(String[]::new));
		String url = this.configs.getSongServiceUrl() + "/songs" + queryParams;

		restTemplate.delete(url);
	}
}
