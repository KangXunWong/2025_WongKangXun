import React, { useState } from "react";
import { calculateCoinChange } from "./apiService";

const CoinChangeCalculator = () => {
  const [targetAmount, setTargetAmount] = useState("");
  const [coinDenominations, setCoinDenominations] = useState("");
  const [coinChange, setCoinChange] = useState([]);
  const [error, setError] = useState("");

  const handleCalculate = async () => {
    try {
      const denominations = coinDenominations.split(",").map(Number);
      const result = await calculateCoinChange(
        parseFloat(targetAmount),
        denominations
      );
      setCoinChange(result.coinChange);
      setError("");
    } catch (err) {
      setError("Failed to calculate coin change. Please check your input.");
      setCoinChange([]);
    }
  };

  return (
    <div>
      <h1>Coin Change Calculator</h1>
      <div>
        <label>
          Target Amount:
          <input
            type="number"
            value={targetAmount}
            onChange={(e) => setTargetAmount(e.target.value)}
          />
        </label>
      </div>
      <div>
        <label>
          Coin Denominations (comma-separated):
          <input
            type="text"
            value={coinDenominations}
            onChange={(e) => setCoinDenominations(e.target.value)}
          />
        </label>
      </div>
      <button onClick={handleCalculate}>Calculate</button>
      {error && <p style={{ color: "red" }}>{error}</p>}
      {coinChange && coinChange.length > 0 && (
        <div>
          <h2>Coin Change:</h2>
          <ul>
            {coinChange.map((coin, index) => (
              <li key={index}>{coin}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default CoinChangeCalculator;
