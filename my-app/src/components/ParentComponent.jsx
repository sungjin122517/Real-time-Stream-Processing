// ParentComponent.jsx
import React, { useEffect, useState } from "react";
import HomeTeamChart from "./HomeGraph";
import AwayTeamChart from "./AwayGraph";

const ParentComponent = () => {
    const [teamData, setTeamData] = useState([]);
    const homeChartId = "home-team-chart"; // Unique chart ID
    const awayChartId = "away-team-chart"; // Unique chart ID


    // Generate random team data at regular intervals
    useEffect(() => {
        const interval = setInterval(() => {
            const generateRandomData = () => {
                //Receive home team value from backend
                const homeTeamValue = Math.floor(Math.random() * 101);
                const newData = {
                    timestamp: new Date().getTime(), // Current timestamp
                    homeTeamValue: homeTeamValue, // Random value for home team
                    awayTeamValue: 100 - homeTeamValue // Random value for away team
                };
                setTeamData([newData]);
                console.log("NEW DATA:", newData);
            };

            generateRandomData();
        }, 5000); // Interval set to 5 seconds (5000 milliseconds)

        // Cleanup interval on component unmount
        return () => clearInterval(interval);
    }, []);

    return (
        <div>
            <HomeTeamChart teamData={teamData} id={homeChartId}/>
            <AwayTeamChart teamData={teamData} id={awayChartId}/>
        </div>
    );
};

export default ParentComponent;
