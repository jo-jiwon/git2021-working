import { ApexOptions } from "apexcharts";
import axios from "axios";
import { useEffect, useRef, useState } from "react";
import Chart from "react-apexcharts";
// import lineData from "./covidLineData";
import barData from "./covidBarData";

const CovidLine = () => {
  const [chartData, setChartData] = useState<{
    options: ApexOptions;
    series: {
      name: string;
      data: number[];
    }[];
  }>();

  const cityRef = useRef<HTMLSelectElement>(null);

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
    >(
      `${process.env.REACT_APP_API_BASE}/opendata/covid/sido/current/${cityRef.current?.value}`
    );

    const data = result.data;
    // const data = lineData;

    const options: ApexOptions = {
      xaxis: {
        // 배열 -> 배열, 요소의 개수가 동일함, 요소의 형식은 다름
        // map함수를 사용
        categories: data
          .map((item) => item.stdDay)
          .sort()
          .map((item) => item.substr(6, 7)),
      },
      colors: ["#434343", "#FF3150"],
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

    setChartData({ options, series });
  };

  useEffect(() => {
    getData();
  }, []);

  return (
    <div>
      {chartData && (
        <>
          <div
            style={{ width: "1000px" }}
            className="d-flex justify-content-end"
          >
            <select
              className="form-select form-select-sm me-2"
              style={{ width: "100px" }}
              onChange={() => {
                getData();
              }}
              ref={cityRef}
            >
              {barData
                .map((item) => item.gubun)
                .map((city) => (
                  <option value={city}>{city}</option>
                ))}
            </select>
          </div>
          <Chart
            options={chartData.options}
            series={chartData.series}
            type="line"
            width="1000"
            height="400"
          />
        </>
      )}
    </div>
  );
};

export default CovidLine;
