import { render, screen } from "@testing-library/react";
import App from "./App";

test("renders Coin Change Calculator heading", () => {
  render(<App />);
  const headingElement = screen.getByText(/Coin Change Calculator/i);
  expect(headingElement).toBeInTheDocument();
});
