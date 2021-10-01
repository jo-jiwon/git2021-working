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
		private String stdDay; // 기준일시
		private String gubun; // 시도명
		private String incDec; // 전일대비 증감수
		private String isolIngCnt; // 격리중 환자수
		private String isolClearCnt; // 격리 해제 수
		private String deathCnt; // 사망자 수
		private String defCnt; // 확진자수
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
//		        "defCnt": 6091,	// 확진자수
//		        "isolClearCnt": 5844,	// 격리중 환자수
//		        "localOccCnt": 0,	// 지역 발생수
//		        "incDec": 9,	// 전일대비 증감수
//		        "updateDt": null,	// 수정일시분초
//		        "createDt": "2021-09-30 09:59:13.156",	// 등록일시분초
//		        "gubun": "검역",	// 시도명
//		        "gubunEn": "Lazaretto",	
//		        "deathCnt": 15,	// 사망자 수 
//		        "stdDay": "2021년 09월 30일 00시",	// 기준일시
//		        "qurRate": "-",	// 10만명당 발생률
//		        "overFlowCnt": 9,	// 해외유입 수
//		        "gubunCn": "隔離區",
//		        "isolIngCnt": 232,	// 격리중 환자수
//		        "seq": 12416	// 게시글번호
//		      },
}
