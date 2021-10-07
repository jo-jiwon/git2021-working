package com.git.myworkspace.opendata.covid;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

// 외부 시스템 통신
// 데이터 트렌젝션 처리
@Service
public class CovidService {

	// 내 인증키 값
	private final String SERVICE_KEY = "c9sJH%2FdoAdEMpdE9phwC1peoOvr72BcpFL03UXX9UTbph%2FptgZva0QxYWspwlEV0D%2F%2FuhYlfaP0kxnkNZ0soCQ%3D%3D";

	// 리포지터리
	private CovidSidoDailyRepository repo;

	// 스프링에 의해서 의존주입
	@Autowired
	public CovidService(CovidSidoDailyRepository repo) {
		this.repo = repo;
	}

	// 1시간 마다 실행
//	@Scheduled(fixedRate = 1000 * 60 * 1)
	// 매일 10시 5분 마다 실행
	@Scheduled(cron = "0 5 10 * * *")

	// 해당 캐시에 하위 키 전부 삭제
	@CacheEvict(value = "covid-current", allEntries = true)
	@SuppressWarnings("deprecation")
	public void requestCovidSidoDaily() throws IOException {
		System.out.println(new Date().toLocaleString());

		/* ------------------------ 데이터 요청하고 XML 받아오기 시작 ------------------------ */
//		// 1. 요청 URL 만들기
		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.data.go.kr/openapi");
		builder.append("/service/rest/Covid19");
		builder.append("/getCovid19SidoInfStateJson");
		builder.append("?serviceKey=" + SERVICE_KEY);
		builder.append("&pageNo=1&numOfRows=10");
		builder.append("&startCreateDt=20210922");

		System.out.println(builder.toString());
//
//		// 2. URL 객체 생성
		URL url = new URL(builder.toString());
//
//		// 3. Http 접속 생성
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//		// 4. byte[]배열로 데이터를 읽어옴
		byte[] result = con.getInputStream().readAllBytes();
//
//		// 5. byte[] -> 문자열(XML) 변환
		String data = new String(result, "UTF-8");
		System.out.println(data);
		/* ------------------------ 데이터 요청하고 XML 받아오기 끝 ------------------------ */

		/* ------------------- XML -> JSON -> Object(Java) 시작 ------------------- */
		// JSON(객체) -> JSON(문자열)
		String json = XML.toJSONObject(data).toString(2);
		System.out.println(json);

		// JSON(문자열) -> Java(object)
		CovidSidoDailyResponse response = new Gson().fromJson(json, CovidSidoDailyResponse.class);
		System.out.println(response);

		// 첫번째 데이터 읽어와 보기
//		CovidStatusResponse.Item item = response.getResponse().getBody().getItems().getItem().get(1);
//		System.out.println(item);
		/* ------------------- XML -> JSON -> Object(Java) 끝 ------------------- */

		/* ---------------------- 응답 객체 -> 엔티티 시작 ----------------- */
		List<CovidSidoDaily> list = new ArrayList<CovidSidoDaily>();
		for (CovidSidoDailyResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			CovidSidoDaily record = CovidSidoDaily.builder().stdDay(item.getStdDay()).gubun(item.getGubun())
					.overFlowCnt(item.getOverFlowCnt().isEmpty() ? null : Integer.valueOf(item.getOverFlowCnt()))
					.localOccCnt(item.getLocalOccCnt().isEmpty() ? null : Integer.valueOf(item.getLocalOccCnt()))
					.build();

			list.add(record);
		}
		/* ---------------------- 응답 객체 -> 엔티티 끝 ----------------- */

		/* ---------------------- 엔티티객체 -> 리포지터리로 저장 시작 ----------------- */
		repo.saveAll(list);
		/* ---------------------- 엔티티객체 -> 리포지터리로 저장 끝 ----------------- */
	}
}
