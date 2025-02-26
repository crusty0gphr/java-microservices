package com.harut.resourceservice.repos;

import com.harut.resourceservice.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepo extends JpaRepository<Resource, Long> {
	@Query(value = "SELECT id FROM resource WHERE id IN :ids", nativeQuery = true)
	List<Long> filterExistingIds(@Param("ids") List<Long> ids);
}
