package org.example.expert.domain.todo.repository;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.example.expert.domain.todo.dto.response.SummaryResponse;
import org.example.expert.domain.todo.entity.QTodo;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.user.entity.QUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TodoRepositoryImpl implements TotoRepositoryCustom{
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Todo> findByIdWithUser(Long todoId) {
		QTodo todo = QTodo.todo;
		QUser user = QUser.user;
		return Optional.ofNullable(queryFactory.selectFrom(todo)
				.leftJoin(todo.user, user).fetchJoin()
			.where(todo.id.eq(todoId))
			.fetchOne());
	}

	@Override
	public Page<SummaryResponse> getSummary(Pageable pageable, String title, LocalDateTime startLocal,
		LocalDateTime endLocal, String nickname) {
		QTodo todo = QTodo.todo;
		QUser user = QUser.user;
		List<SummaryResponse> todos  = queryFactory.select(Projections.constructor(SummaryResponse.class,
				todo.title,
				todo.managers.size(),
				todo.comments.size()
			))
			.from(todo)
			.leftJoin(todo.user, user)
			.where(
				title != null ? todo.title.like(title) : null,
				startLocal != null && endLocal != null? todo.createdAt.between(startLocal, endLocal) : null,
				nickname != null ? user.nickname.eq(nickname) : null
			).orderBy(todo.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		long total = queryFactory.select(todo.count())
			.from(todo)
			.leftJoin(todo.user, user)
			.where(
				title != null ? todo.title.like(title) : null,
				startLocal != null && endLocal != null ? todo.createdAt.between(startLocal, endLocal) : null,
				nickname != null ? user.nickname.eq(nickname) : null
			)
			.fetchOne();

		return new PageImpl<>(todos, pageable, total);
	}
}
