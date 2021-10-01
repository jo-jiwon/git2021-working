package com.git.myworkspace.opendata.covid;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Entity: 클래스와 테이블 매핑
@Entity
// Table: 엔티티클래스에 매핑할 테이블 정보
@Table(indexes = @Index(name = "idx_covid_status_1", columnList = "gubun"))

@IdClass(CovidStatusId.class)
public class CovidStatus {

	// 20210930 22:00, 제주, 확진자수

	@Id
	private String stdDay; // 기준일시
	@Id
	private String gubun; // 시도명
	@Id
	private String incDec; // 전일대비 증감수

	private String createDt;
	private String isolIngCnt; // 격리중 환자수
	private String isolClearCnt; // 격리 해제 수
	private String deathCnt; // 사망자 수
	private String defCnt; // 확진자수
}
