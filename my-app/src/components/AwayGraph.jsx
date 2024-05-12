import React, { useEffect, useState, useRef } from "react";
import ApexCharts from 'apexcharts';

const getRandomDataPoint = () => Math.floor(Math.random() * 101);

const AwayTeamChart = () => {
    const [series, setSeries] = useState([{ data: [] }]);
    const chartRef = useRef(null);
    const [lastDate, setLastDate] = useState(new Date().getTime()); // Track last timestamp

    useEffect(() => {
    const interval = setInterval(() => {
        const newDataPoint = getRandomDataPoint();
        const newTimestamp = new Date().getTime(); // Get current timestamp
        setSeries(prevSeries => [{ data: [...prevSeries[0].data, { x: newTimestamp, y: newDataPoint }] }]);
        setLastDate(newTimestamp); // Update last timestamp
    }, 10000);

    // Cleanup interval on component unmount
    return () => clearInterval(interval);
    }, []);

    useEffect(() => {
    if (chartRef.current) {
        const chart = new ApexCharts(chartRef.current, {
        chart: {
            id: 'realtime',
            height: '100%', // Set height to cover the whole page
            type: 'line',
            animations: {
            enabled: true,
            easing: 'linear',
            dynamicAnimation: {
                speed: 1000
            }
            },
            toolbar: {
            show: false
            },
            zoom: {
            enabled: false
            }
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            curve: 'smooth'
        },
        title: {
            text: 'Win Probability',
            align: 'left'
        },
        markers: {
            size: 0
        },
        xaxis: {
            type: 'datetime',
            // Set initial range to show a period of time
            min: lastDate - 30000, // 30 seconds ago
            max: lastDate 
        },
        yaxis: {
            min: 0,
            max: 100, 
        },
        legend: {
            show: false
        },
        series: series
        });

        chart.render();

        return () => chart.destroy(); // Destroy the chart instance on component unmount
    }
    }, [series, lastDate]);

    useEffect(() => {
    if (chartRef.current) {
        ApexCharts.exec('realtime', 'updateSeries', [{
        data: series[0].data
        }]);
    }
    }, [series]);

    return (
    <div style={{ height: '50vh' }}> {/* Set height to cover the whole page */}
        <div ref={chartRef}></div>
    </div>
    );
};

export default AwayTeamChart;
