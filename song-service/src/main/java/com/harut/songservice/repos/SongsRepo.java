package com.harut.songservice.repos;

import com.harut.songservice.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongsRepo extends JpaRepository<Song, Long> {
}
