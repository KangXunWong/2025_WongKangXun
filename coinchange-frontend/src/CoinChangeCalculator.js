import React, { useState, useEffect } from "react";
import { calculateCoinChange } from "./apiService";
import "./App.css"; // Import the CSS file for styling

const CoinChangeCalculator = () => {
  const [targetAmount, setTargetAmount] = useState("");
  const [coinChange, setCoinChange] = useState([]);
  const [error, setError] = useState("");

  const coinDenominations = [
    0.01, 0.05, 0.1, 0.2, 0.5, 1, 2, 5, 10, 50, 100, 1000,
  ];

  const handleCalculate = async () => {
    try {
      const target = parseFloat(targetAmount);
      const filteredDenominations = coinDenominations.filter(
        (denomination) => denomination <= target
      );
      const result = await calculateCoinChange(target, filteredDenominations);
      setCoinChange(result); // Directly set the result as coinChange
      setError("");
    } catch (err) {
      console.error("Error calculating coin change:", err);
      setError("Failed to calculate coin change. Please check your input.");
      setCoinChange([]);
    }
  };

  useEffect(() => {
    if (targetAmount === "") {
      setCoinChange([]);
    }
  }, [targetAmount]);

  const filteredDenominations = coinDenominations.filter(
    (denomination) => denomination <= parseFloat(targetAmount)
  );

  return (
    <div className="calculator-container">
      <h1>Coin Change Calculator</h1>
      {filteredDenominations.length > 0 && (
        <div className="section common-style">
          <label>Available Coin Denominations:</label>
          <div className="coin-denominations-list">
            {filteredDenominations.map((denomination, index) => (
              <span key={index} className="coin-item">
                {denomination}
              </span>
            ))}
          </div>
        </div>
      )}
      <div className="section input-group common-style">
        <label>
          Target Amount:
          <input
            type="number"
            value={targetAmount}
            onChange={(e) => setTargetAmount(e.target.value)}
          />
        </label>
      </div>
      <div className="section">
        <button onClick={handleCalculate}>Calculate</button>
      </div>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {coinChange.length > 0 && (
        <div className="section common-style">
          <label>Coin Change:</label>
          <div className="coin-change-list">
            {coinChange.map((coin, index) => (
              <span key={index} className="coin-item">
                {coin}
              </span>
            ))}
          </div>
        </div>
      )}
      <footer className="footer">
        <p>&copy; {new Date().getFullYear()} KX Wong. All rights reserved.</p>
      </footer>
    </div>
  );
};

export default CoinChangeCalculator;
