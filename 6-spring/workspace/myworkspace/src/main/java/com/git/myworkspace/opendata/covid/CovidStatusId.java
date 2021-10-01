package com.git.myworkspace.opendata.covid;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class CovidStatusId implements Serializable {

	private static final long serialVersionUID = 1L;

	private String stdDay; // 기준일시
	private String gubun; // 시도명
	private String incDec; // 전일대비 증감수

}
