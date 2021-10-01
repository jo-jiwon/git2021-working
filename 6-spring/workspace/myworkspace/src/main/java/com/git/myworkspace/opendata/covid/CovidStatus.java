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
// Entity: Ŭ������ ���̺� ����
@Entity
// Table: ��ƼƼŬ������ ������ ���̺� ����
@Table(indexes = @Index(name = "idx_covid_status_1", columnList = "gubun"))

@IdClass(CovidStatusId.class)
public class CovidStatus {

	// 20210930 22:00, ����, Ȯ���ڼ�

	@Id
	private String stdDay; // �����Ͻ�
	@Id
	private String gubun; // �õ���
	@Id
	private String incDec; // ���ϴ�� ������

	private String createDt;
	private String isolIngCnt; // �ݸ��� ȯ�ڼ�
	private String isolClearCnt; // �ݸ� ���� ��
	private String deathCnt; // ����� ��
	private String defCnt; // Ȯ���ڼ�
}
