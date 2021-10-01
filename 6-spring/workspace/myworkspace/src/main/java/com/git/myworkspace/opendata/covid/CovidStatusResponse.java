package com.git.myworkspace.opendata.covid;

import java.util.List;

import lombok.Data;

@Data
public class CovidStatusResponse {
	private Response response;

	@Data
	public class Response {
		private Header header;
		private Body body;
	}

	@Data
	public class Header {
		private String resultCode;
		private String resultMsg;
	}

	@Data
	public class Body {
		private Items items;
	}

	@Data
	public class Items {
		private List<Item> item;
	}

	@Data
	public class Item {
		private String stdDay; // �����Ͻ�
		private String gubun; // �õ���
		private String incDec; // ���ϴ�� ������
		private String isolIngCnt; // �ݸ��� ȯ�ڼ�
		private String isolClearCnt; // �ݸ� ���� ��
		private String deathCnt; // ����� ��
		private String defCnt; // Ȯ���ڼ�
	}

//	{"response": {
//		  "header": {
//		    "resultCode": "00",
//		    "resultMsg": "NORMAL SERVICE."
//		  },
//		  "body": {
//		    "pageNo": 1,
//		    "totalCount": 19,
//		    "items": {"item": [
//		      {
//		        "defCnt": 6091,	// Ȯ���ڼ�
//		        "isolClearCnt": 5844,	// �ݸ��� ȯ�ڼ�
//		        "localOccCnt": 0,	// ���� �߻���
//		        "incDec": 9,	// ���ϴ�� ������
//		        "updateDt": null,	// �����Ͻú���
//		        "createDt": "2021-09-30 09:59:13.156",	// ����Ͻú���
//		        "gubun": "�˿�",	// �õ���
//		        "gubunEn": "Lazaretto",	
//		        "deathCnt": 15,	// ����� �� 
//		        "stdDay": "2021�� 09�� 30�� 00��",	// �����Ͻ�
//		        "qurRate": "-",	// 10����� �߻���
//		        "overFlowCnt": 9,	// �ؿ����� ��
//		        "gubunCn": "̰��ϡ",
//		        "isolIngCnt": 232,	// �ݸ��� ȯ�ڼ�
//		        "seq": 12416	// �Խñ۹�ȣ
//		      },
}
