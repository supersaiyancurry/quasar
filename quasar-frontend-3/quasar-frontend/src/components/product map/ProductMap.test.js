import React from 'react';
import { createMemoryHistory } from 'history';
import fetchMock from 'jest-fetch-mock';
import { render, cleanup, waitFor } from '@testing-library/react';
import { Router } from 'react-router-dom';
import ProductMap from './ProductMap';

afterEach(cleanup);

fetchMock.enableMocks();

const products = [
  {
    id: 163,
    price: 50.71,
    name: 'Heavy Duty Soccer Hoodie',
    description: "This soccer hoodie for men is extremely heavy duty. You're going to love it!",
    demographic: 'Men',
    category: 'Soccer',
    type: 'Hoodie',
    releaseDate: '2017-01-01',
    primaryColorCode: '#3079ab',
    secondaryColorCode: '#f092b0',
    styleNumber: 'sc97427',
    globalProductCode: 'po-7560240'
  },
  {
    id: 3426,
    price: 70.36,
    name: 'Trendy Baseball Shin Guard',
    description: "This baseball shin guard for men is extremely trendy. You're going to love it!",
    demographic: 'Men',
    category: 'Baseball',
    type: 'Shin Guard',
    releaseDate: '2017-01-01',
    primaryColorCode: '#e15258',
    secondaryColorCode: '#000000',
    styleNumber: 'sc06765',
    globalProductCode: 'po-0223877'
  }
];

const renderWithRouter = (component) => {
  const history = createMemoryHistory();
  return {
    ...render(
      <Router history={history}>
        {component}
      </Router>
    )
  };
};

it('renders without crashing', () => {
  renderWithRouter(
    <ProductMap />
  );
});

it('product card component matches snapshot', () => {
  const { asFragment } = renderWithRouter(
    <ProductMap />
  );
  expect(asFragment()).toMatchSnapshot();
});

it('should load product data from api', async () => {
  fetch.mockResponse(JSON.stringify({ data: products }));
  const { getAllByTestId } = renderWithRouter(<ProductMap />);
  const productData = await waitFor(() => getAllByTestId('products'));

  expect(productData).toHaveLength(2);
});
