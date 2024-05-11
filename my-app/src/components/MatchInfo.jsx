import React, { useState, useEffect } from 'react';

function MatchInfo() {
    const [matchData, setMatchData] = useState(null);

    useEffect(() => {
    // Fetch match data here
    // Update matchData state
    }, []);

    return (
        <div className="match-info">
        {/* Display match information */}
        </div>
    );
}

export default MatchInfo;
