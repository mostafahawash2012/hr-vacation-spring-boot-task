package com.orange.task.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.orange.task.model.History;

@Repository
public interface HistoryRepository extends JpaRepository<History, UUID> {

	List<History> findAllByOrderByStartDateAsc();
}
