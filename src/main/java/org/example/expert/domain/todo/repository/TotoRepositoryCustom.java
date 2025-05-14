package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.example.expert.domain.todo.dto.response.SummaryResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TotoRepositoryCustom {
	Optional<Todo> findByIdWithUser(Long userId);
	Page<SummaryResponse> getSummary(Pageable pageable, String title, LocalDateTime startLocal, LocalDateTime endLocal,
		String nickname);
}
