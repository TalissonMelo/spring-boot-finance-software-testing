package com.talissonmelo.finance.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LaunchDTO {

	private Long id;
	private String description;
	private Integer month;
	private Integer year;
	private Double value;
	private String type;
	private String status;
	private Long user;

}
