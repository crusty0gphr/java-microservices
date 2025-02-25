package com.harut.songservice.services;

import com.harut.songservice.dto.SongEntity;
import com.harut.songservice.dto.SongsResponse;
import com.harut.songservice.exceptions.EntityNotFoundException;
import com.harut.songservice.models.Song;
import com.harut.songservice.repos.SongsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SongsService {
	private final SongsRepo songsRepo;

	public SongsResponse saveSong(SongEntity song) {
		Song model = new Song();
		model.setId(song.getId());
		model.setName(song.getName());
		model.setAlbum(song.getAlbum());
		model.setArtist(song.getArtist());
		model.setDuration(song.getDuration());
		model.setYear(song.getYear());
		model.setDateCreated(LocalTime.now());

		Song saved = this.songsRepo.save(model);
		SongsResponse result = new SongsResponse();
		result.setId(saved.getId());

		return result;
	}

	public SongEntity getById(Long id) {
		Song model = this.songsRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Failed retrieving song by id: " + id));

		SongEntity result = new SongEntity();
		result.setId(model.getId());
		result.setName(model.getName());
		result.setAlbum(model.getAlbum());
		result.setArtist(model.getArtist());
		result.setDuration(model.getDuration());
		result.setYear(model.getYear());

		return result;
	}

	public void deleteAllByIds(Long[] ids) {
		this.songsRepo.deleteAllById(List.of(ids));
	}
}
