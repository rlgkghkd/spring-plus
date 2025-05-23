package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TodoRepository extends JpaRepository<Todo, Long>, TotoRepositoryCustom{

    @Query("SELECT t FROM Todo t WHERE (:weather IS NULL OR t.weather = :weather) " +
        "AND (:startLocal IS NULL OR t.modifiedAt >= :startLocal) " +
        "AND (:endLocal IS NULL OR t.modifiedAt <= :endLocal)")
    Page<Todo> findAllByParameter(
        Pageable pageable,
        @Param("weather") String weather,
        @Param("startLocal") LocalDateTime startLocal,
        @Param("endLocal") LocalDateTime endLocal);


   // @Query("SELECT t FROM Todo t " +
    //        "LEFT JOIN t.user " +
    //        "WHERE t.id = :todoId")
   // Optional<Todo> findByIdWithUser(@Param("todoId") Long todoId);
}
