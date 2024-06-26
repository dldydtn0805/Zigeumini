package com.backend.api.domain.hadoop.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinDataDto implements Serializable {
	private String stockCode;
	// private Integer minPrice;
	private String date;
}
