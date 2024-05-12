import React from 'react';
import { BOS, NYK } from 'react-nba-logos';


function Sidebar() {
    const currentDate = new Date().toLocaleDateString('en-US', { year: 'numeric', month: 'long', day: 'numeric' });

    return (
        <div className="sidebar p-4 d-flex flex-column justify-content-center">
        <div className="fixed-top p-4">
            <h1 className="mb-4 text-white ">{currentDate}</h1>
        </div>
        <div className="teams-container d-flex flex-column justify-content-center">
            <div className="teams d-flex align-items-center mb-4">
                <div className="mr-3">
                    <BOS size={140} />
                </div>
                <div>
                    <h4 className="mb-1 text-white">Home Team</h4>
                    <h6 className="m-0 text-white">Boston Celtics</h6>
                </div>
            </div>
            <br></br><br></br><br></br><br></br><br></br><br></br><br></br>
            <div className="teams d-flex align-items-center">
                <div className="mr-3">
                    <NYK size={140} />
                </div>
                <div>
                    <h4 className="mb-1 text-white">Away Team</h4>
                    <h6 className="m-0 text-white">New York Knicks</h6>
                </div>
            </div>
        </div>
    </div>
    );
}

export default Sidebar;
