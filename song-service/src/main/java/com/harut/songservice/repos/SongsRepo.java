package com.harut.songservice.repos;

import com.harut.songservice.models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongsRepo extends JpaRepository<Song, Long> {
	@Query(value = "SELECT id FROM songs WHERE id = :id", nativeQuery = true)
	Long songExists(@Param("id") Long id);

	@Query(value = "SELECT id FROM songs WHERE id IN :ids", nativeQuery = true)
	List<Long> filterExistingIds(@Param("ids") List<Long> ids);
}
