import React from 'react';
import Links from "./Route";
import { BrowserRouter } from "react-router-dom";

function App() {
  return (
    <div
      className="App"
      style={{
        backgroundColor: "#1B1C31",
        height: "100vh",
        width: "100vw",
        margin: "0px",
      }}
    >
      <BrowserRouter>
        <Links />
      </BrowserRouter>
    </div>
  );
}

export default App;
