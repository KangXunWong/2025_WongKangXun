import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL;

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
