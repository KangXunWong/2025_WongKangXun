import React from "react";
import { render, screen, fireEvent } from "@testing-library/react";
import CoinChangeCalculator from "./CoinChangeCalculator";
import { calculateCoinChange } from "./apiService";

// Mock the calculateCoinChange function
jest.mock("./apiService");

describe("CoinChangeCalculator", () => {
  test("renders Coin Change Calculator heading", () => {
    render(<CoinChangeCalculator />);
    const headingElement = screen.getByText(/Coin Change Calculator/i);
    expect(headingElement).toBeInTheDocument();
  });

  test("displays error message on invalid input", async () => {
    calculateCoinChange.mockRejectedValueOnce(new Error("Invalid input"));

    render(<CoinChangeCalculator />);

    fireEvent.change(screen.getByLabelText(/Target Amount/i), {
      target: { value: "invalid" },
    });
    fireEvent.change(screen.getByLabelText(/Coin Denominations/i), {
      target: { value: "0.01,0.05,0.1" },
    });
    fireEvent.click(screen.getByText(/Calculate/i));

    const errorMessage = await screen.findByText(
      /Failed to calculate coin change. Please check your input./i
    );
    expect(errorMessage).toBeInTheDocument();
  });

  test("displays coin change on valid input", async () => {
    const mockResponse = { coinChange: [1.0, 0.25, 0.1, 0.1, 0.01] };
    calculateCoinChange.mockResolvedValueOnce(mockResponse);

    render(<CoinChangeCalculator />);

    fireEvent.change(screen.getByLabelText(/Target Amount/i), {
      target: { value: "1.46" },
    });
    fireEvent.change(screen.getByLabelText(/Coin Denominations/i), {
      target: { value: "0.01,0.05,0.1,0.25,1.0" },
    });
    fireEvent.click(screen.getByText(/Calculate/i));

    const coinChangeList = await screen.findByText(/Coin Change:/i);
    expect(coinChangeList).toBeInTheDocument();

    mockResponse.coinChange.forEach((coin) => {
      expect(screen.getByText(coin.toString())).toBeInTheDocument();
    });
  });
});
