import axios from "axios";

const API_URL = "http://localhost:8080/coin-change";

export const calculateCoinChange = async (targetAmount, coinDenominations) => {
  try {
    const response = await axios.post(API_URL, {
      targetAmount,
      coinDenominations,
    });
    return response.data;
  } catch (error) {
    console.error("Error calculating coin change:", error);
    throw error;
  }
};
