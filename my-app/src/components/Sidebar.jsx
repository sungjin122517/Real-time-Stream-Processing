import React from 'react';
import { BOS, NYK } from 'react-nba-logos';

function Sidebar() {
    return (
    <div className="sidebar p-4   align-items-center">
        <div>
            <h2 className="mb-4 text-white ">2024 May 10</h2>
        </div>
        <div className="teams d-flex align-items-center mb-4">
        <div className="mr-3">
            <BOS size={140} />
        </div>
        <div>
            <h4 className="mb-1 text-white">Home Team</h4>
            <p className="m-0 text-white">Boston Celtics</p>
        </div>
        </div>
        <div className="teams d-flex align-items-center mt-auto">
        <div className="mr-3">
            <NYK size={140} />
        </div>
        <div>
            <h4 className="mb-1 text-white">Away Team</h4>
            <p className="m-0 text-white">New York Knicks</p>
        </div>
        </div>
    </div>
    );
}

export default Sidebar;
