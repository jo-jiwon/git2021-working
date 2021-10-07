import { ApexOptions } from "apexcharts";
import axios from "axios";
import { useEffect, useState } from "react";
import Chart from "react-apexcharts";
const CovidBar = () => {
  const [chartData, setChartData] = useState<{
    options: ApexOptions;
    series: {
      name: string;
      data: number[];
    }[];
  }>();

  // 백엔드에서 데이터 받아오고
  const getData = async () => {
    // 배열타입으로 받아온다
    const result = await axios.get<
      {
        stdDay: string;
        gubun: string;
        overFlowCnt: number;
        localOccCnt: number;
      }[]
    >(`${process.env.REACT_APP_API_BASE}/opendata/covid/sido/current`);

    const data = result.data;

    // 차트의 옵션들, x축 문자열
    const options: ApexOptions = {
      title: {
        text: `서울 미세먼지 현황 (${result.data[0].stdDay})`,
      },
      xaxis: {
        // 배열 -> 배열, 요소의 개수가 동일함, 요소의 형식은 다름
        // map함수를 사용
        categories: data.map((item) => item.gubun),
      },
      colors: ["#434343", "#FF3150"],
      chart: {
        stacked: true,
      },
    };
    // 실게 값들
    const series = [
      {
        name: "overFlowCnt",
        data: data.map((item) => item.overFlowCnt),
      },
      {
        name: "localOccCnt",
        data: data.map((item) => item.localOccCnt),
      },
    ];

    // state로 set을 해야 차트가 다시그려짐
    setChartData({ options, series });
  };

  // useEffect 훅으로 처음 마운팅 되서 렌더링 될 때
  useEffect(() => {
    getData();
  }, []);

  return (
    <div>
      {/* 차트데이터가 있을때 차트를 그려라 */}
      {chartData && (
        <Chart
          options={chartData?.options}
          series={chartData?.series}
          type="bar"
          width="1000"
          height="400"
        />
      )}
    </div>
  );
};

export default CovidBar;
