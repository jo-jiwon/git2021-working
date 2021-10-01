package com.git.myworkspace.opendata.air;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

// ���� ������Ʈ
// 1. �ܺ� �ý��� ���
// 2. ������ Ʈ������ ó��
@Service
public class AirService {

	private final String SERVICE_KEY = "c9sJH%2FdoAdEMpdE9phwC1peoOvr72BcpFL03UXX9UTbph%2FptgZva0QxYWspwlEV0D%2F%2FuhYlfaP0kxnkNZ0soCQ%3D%3D";

	private AirSigunguHourRepository repo;

	@Autowired
	public AirService(AirSigunguHourRepository repo) {
		this.repo = repo;
	}

	// ���� 2�ð����� ����
//	@Scheduled(cron = "0 0 */2 * * *")

	// �Ž� 30�п� ����, 1�� 30��, 2�� 30��
	// �� �ð��� �Ǿ�߸� �����
	// cron = "�� �� �� �� �� ��"
	// cron = "0 30 * * * *"
//	@Scheduled(cron = "0 30 * * * *")

	// �ñ����� ����� �ð����� ��ȸ �޼ҵ�
	// 1�ð� ���� ����(js, setInterval)
	// fixedRate: ���� ó���� ����ǰ� ���ݺ��� �����
	@Scheduled(fixedRate = 1000 * 60 * 60 * 1)
	public void requestAir() throws IOException {
		String[] sidoNames = { "����", "���" };
		for (String sidoName : sidoNames) {
			requestAirSigunguHour(sidoName);

		}
	}

	// throws IOException: �����߸� ����ó��
	@SuppressWarnings("deprecation")
	public void requestAirSigunguHour(String sido) throws IOException {
		System.out.println(new Date().toLocaleString());

		/* ------------------------ ������ ��û�ϰ� XML �޾ƿ��� ���� ------------------------ */
		// http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst?sidoName=����&searchCondition=HOUR&pageNo=1&numOfRows=25&returnType=xml&serviceKey=c9sJH%2FdoAdEMpdE9phwC1peoOvr72BcpFL03UXX9UTbph%2FptgZva0QxYWspwlEV0D%2F%2FuhYlfaP0kxnkNZ0soCQ%3D%3D
		// StringBuilder ���ڿ��� ���� ������� �����ϴ� Ŭ����

		// 1. ��ûURL �����
		StringBuilder builder = new StringBuilder();
		builder.append("http://apis.data.go.kr/B552584"); // ȣ��Ʈ/����Ʈ����
		builder.append("/ArpltnStatsSvc"); // ����
		builder.append("/getCtprvnMesureSidoLIst"); // ���(�õ�-�ñ�������ȸ ��) ����-������..�߶���)
		builder.append("?sidoName=" + URLEncoder.encode(sido, "UTF-8"));
		builder.append("&searchCondition=HOUR"); // 1�ð�����
		builder.append("&pageNo=1&numOfRows=100"); // ������ �� 25��
		builder.append("&serviceKey=" + SERVICE_KEY); // ����Ű

		System.out.println(builder.toString());

		// 2. URL ��ü ����
		URL url = new URL(builder.toString());

		// 3. Http ���� ����
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		// 4. byte[]�迭�� �����͸� �о��
		byte[] result = con.getInputStream().readAllBytes();

		// 5. byte[] -> ���ڿ�(XML) ��ȯ
		String data = new String(result, "UTF-8");
//		System.out.println(data);
		/* ------------------- ������ ��û�ϰ� XML �޾ƿ��� �� ------------------- */

		/* ------------------- XML -> JSON -> Object(Java) ���� ------------------- */
		// JSON(��ü) -> JSON(���ڿ�)
		String json = XML.toJSONObject(data).toString(2);
//		System.out.println(json);

		// JSON(���ڿ�) -> Java(object)
		AirSigunguHourResponse response = new Gson().fromJson(json, AirSigunguHourResponse.class);
		System.out.println(response);

//		������ ������
//		AirSigunguHourResponse.Item item = response.getResponse().getBody().getItems().getItem().get(1);
//		System.out.println(item);
		/* ------------------- XML -> JSON -> Object(Java) �� ------------------- */

		/* ---------------------- ���� ��ü -> ��ƼƼ ���� ----------------- */
		List<AirSigunguHour> list = new ArrayList<AirSigunguHour>();
		for (AirSigunguHourResponse.Item item : response.getResponse().getBody().getItems().getItem()) {
			AirSigunguHour record = AirSigunguHour.builder().dataTime(item.getDataTime()).sidoName(item.getSidoName())
					.cityName(item.getCityName()).pm10Value(item.getPm10Value()).pm25Value(item.getPm25Value()).build();

			list.add(record);
		}
		/* ---------------------- ���� ��ü -> ��ƼƼ �� ----------------- */

		/* ---------------------- ��ƼƼ��ü -> �������͸��� ���� ���� ----------------- */
		repo.saveAll(list);
		/* ---------------------- ��ƼƼ��ü -> �������͸��� ���� �� ----------------- */
	}
}
