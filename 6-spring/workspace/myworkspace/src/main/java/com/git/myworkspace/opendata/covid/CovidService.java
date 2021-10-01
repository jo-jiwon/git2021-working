package com.git.myworkspace.opendata.covid;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

// �ܺ� �ý��� ���
// ������ Ʈ������ ó��
@Service
public class CovidService {

	// �� ����Ű ��
	private final String SERVICE_KEY = "c9sJH%2FdoAdEMpdE9phwC1peoOvr72BcpFL03UXX9UTbph%2FptgZva0QxYWspwlEV0D%2F%2FuhYlfaP0kxnkNZ0soCQ%3D%3D";

	// �������͸�
	private CovidStatusRepository repo;

	// �������� ���ؼ� ��������
	@Autowired
	public CovidService(CovidStatusRepository repo) {
		this.repo = repo;
	}

	@SuppressWarnings("deprecation")
	// 1�ð� ���� ����
	@Scheduled(fixedRate = 1000 * 60 * 60 * 1)
	// ���� 9�� ���� ����
//	@Scheduled(cron = "* 0 9 * * *")
	public void requestCovidStatus() throws IOException {
		System.out.println(new Date().toLocaleString());

		/* ------------------------ ������ ��û�ϰ� XML �޾ƿ��� ���� ------------------------ */
//		// 1. ��û URL �����
		StringBuilder builder = new StringBuilder();
		builder.append("http://openapi.data.go.kr/openapi");
		builder.append("/service/rest/Covid19");
		builder.append("/getCovid19SidoInfStateJson");
		builder.append("?serviceKey=" + SERVICE_KEY);
		builder.append("&pageNo=1&numOfRows=10");

		System.out.println(builder.toString());
//
//		// 2. URL ��ü ����
		URL url = new URL(builder.toString());
//
//		// 3. Http ���� ����
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
//
//		// 4. byte[]�迭�� �����͸� �о��
		byte[] result = con.getInputStream().readAllBytes();
//
//		// 5. byte[] -> ���ڿ�(XML) ��ȯ
		String data = new String(result, "UTF-8");
		System.out.println(data);
		/* ------------------------ ������ ��û�ϰ� XML �޾ƿ��� �� ------------------------ */

		/* ------------------- XML -> JSON -> Object(Java) ���� ------------------- */
		// JSON(��ü) -> JSON(���ڿ�)
		String json = XML.toJSONObject(data).toString(2);
//		System.out.println(json);

		// JSON(���ڿ�) -> Java(object)
		CovidStatusResponse response = new Gson().fromJson(json, CovidStatusResponse.class);
		System.out.println(response);

		// ù��° ������ �о�� ����
//		CovidStatusResponse.Item item = response.getResponse().getBody().getItems().getItem().get(1);
//		System.out.println(item);
		/* ------------------- XML -> JSON -> Object(Java) �� ------------------- */

		/* ---------------------- ���� ��ü -> ��ƼƼ ���� ----------------- */
		List<CovidStatus> list = new ArrayList<CovidStatus>();
		for (CovidStatusResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			CovidStatus record = CovidStatus.builder().stdDay(item.getStdDay()).gubun(item.getGubun())
					.incDec(item.getIncDec()).isolIngCnt(item.getIsolIngCnt()).isolClearCnt(item.getIsolClearCnt())
					.deathCnt(item.getDeathCnt()).defCnt(item.getDefCnt()).build();

			list.add(record);
		}
		/* ---------------------- ���� ��ü -> ��ƼƼ �� ----------------- */

		/* ---------------------- ��ƼƼ��ü -> �������͸��� ���� ���� ----------------- */
		repo.saveAll(list);
		/* ---------------------- ��ƼƼ��ü -> �������͸��� ���� �� ----------------- */
	}
}
