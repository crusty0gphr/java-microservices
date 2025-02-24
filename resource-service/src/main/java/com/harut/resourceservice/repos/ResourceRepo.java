package com.harut.resourceservice.repos;

import com.harut.resourceservice.models.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepo extends JpaRepository<Resource, Long> {
}
