import React, { useEffect, useRef } from "react";
import {
    Chart,
    LineController,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement,
} from "chart.js";

const RealTimeGraph = () => {
    const chartRef = useRef(null);
    let chartInstance = null;

    useEffect(() => {
    const ctx = chartRef.current.getContext("2d");

    const createChart = () => {
        Chart.register(
        LineController,
        CategoryScale,
        LinearScale,
        PointElement,
        LineElement
        );
        chartInstance = new Chart(ctx, {
        type: "line",
        data: {
            labels: ["January", "February", "March", "April", "May", "June"],
            datasets: [
            {
                label: "Data 1",
                data: [0, 20, 30, 40, 50, 60],
                borderColor: "rgba(255, 99, 132, 1)",
                backgroundColor: "rgba(255, 99, 132, 0.2)",
                pointRadius: 5, // 포인트 크기
                pointBackgroundColor: "rgba(255, 99, 132, 1)", // 포인트 배경색
                pointBorderColor: "rgba(255, 255, 255, 1)", // 포인트 테두리 색
                pointHoverRadius: 7, // 호버 시 포인트 크기
                pointHoverBackgroundColor: "rgba(255, 99, 132, 1)", // 호버 시 포인트 배경색
                pointHoverBorderColor: "rgba(255, 255, 255, 1)", // 호버 시 포인트 테두리 색
                fill: false, // 라인 그래프에서 영역 채우기 비활성화
            },
            ],
        },
        options: {
            scales: {
            x: {
                display: true,
            },
            y: {
                beginAtZero: true,
                max: 100, // 최대값 설정
            },
            },
        },
        });
    };

    const destroyChart = () => {
        if (chartInstance) {
        chartInstance.destroy();
        chartInstance = null;
        }
    };

    const initializeChart = () => {
        destroyChart(); // 이전 차트 파괴
        createChart(); // 새로운 차트 생성
    };

    initializeChart();

    return () => {
        destroyChart();
    };
    }, []);

    return <canvas ref={chartRef} />;
};

export default RealTimeGraph;