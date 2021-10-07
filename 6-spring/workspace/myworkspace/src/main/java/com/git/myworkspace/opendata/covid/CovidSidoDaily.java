package com.git.myworkspace.opendata.covid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//Entity: 클래스와 테이블 매핑
//Table: 엔티티클래스에 매핑할 테이블 정보

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(indexes = @Index(name = "idx_covid_sido_daily_1", columnList = "gubun"))
@IdClass(CovidSidoDailyId.class)
public class CovidSidoDaily {

	// 20210930 22:00, 제주

	@Id
	private String stdDay; // 기준일시
	@Id
	@Column(columnDefinition = "varchar(255) collate \"ko_KR.utf8\"")
	private String gubun; // 시도명

	private Integer overFlowCnt; // 해외유입 수
	private Integer localOccCnt; // 지역발생 수

//	private String createDt;
//	private String isolIngCnt; // 격리중 환자수
//	private String isolClearCnt; // 격리 해제 수
//	private String deathCnt; // 사망자 수
//	private String defCnt; // 확진자수
}
