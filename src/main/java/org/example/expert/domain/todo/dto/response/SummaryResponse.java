package org.example.expert.domain.todo.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class SummaryResponse {
	private String title;
	private Integer managerCount;
	private Integer commentCount;
}
