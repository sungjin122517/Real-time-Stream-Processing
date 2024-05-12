import React from 'react';
import Sidebar from '../components/Sidebar';
import Header from '../components/Header';
import AwayTeamChart from '../components/AwayGraph';
import HomeTeamChart from '../components/HomeGraph';
import ParentComponent from '../components/ParentComponent';
import { Box } from "@mui/material";

function HomePage() {
  return (
    <div className="homepage d-flex">
      <Sidebar />
      <div className="homepage__main flex-grow-1" style={{ backgroundColor: 'white' }}>
        <Header />
        <Box
          sx={{
            height: "100vh",
            display: "flex",
            flexDirection: "column",
            backgroundColor: '#FFFFFF', // White background for the graph
          }}
        >
            <ParentComponent />
        </Box>
      </div>
    </div>
  );
}

export default HomePage;
