import React from 'react';
import { render, waitFor } from '@testing-library/react';
import ShoppingCart from './ShoppingCart';

const dummyProduct1 = {
  id: 2263,
  price: 26.44,
  name: 'Slim Hockey Belt',
  description: "This hockey belt for men is extremely slim. You're going to love it!",
  demographic: 'Men',
  category: 'Hockey',
  type: 'Belt',
  releaseDate: '2017-01-01',
  primaryColorCode: '#000000',
  secondaryColorCode: '#39add1',
  styleNumber: 'sc47638',
  globalProductCode: 'po-3555174'
};

const dummyProduct2 = {
  id: 2234,
  price: 23.33,
  name: 'Rainbow Hockey Belt',
  description: "This hockey belt for men is extremely cool. You're going to love it!",
  demographic: 'Men',
  category: 'Hockey',
  type: 'Belt',
  releaseDate: '2017-01-01',
  primaryColorCode: '#000000',
  secondaryColorCode: '#39add1',
  styleNumber: 'sc47638',
  globalProductCode: 'po-3555174'
};

const dummyShoppingCart = [dummyProduct1, dummyProduct2];

it('renders properly', () => {
  const { getByText } = render(
    <ShoppingCart />
  );

  expect(getByText('Shopping Cart')).toBeInTheDocument();
});

it('loads shopping cart given a list of products', async () => {
  const fetchSpy = jest.spyOn(window, 'fetch')
    .mockImplementation(() => Promise.resolve(new Response(JSON.stringify(dummyShoppingCart))));
  const { getAllByText } = render(
    <ShoppingCart />
  );

  await waitFor(() => {
    expect(fetchSpy).toHaveBeenCalledTimes(1);
    expect(getAllByText(dummyProduct1.name).length !== 0);
    expect(getAllByText(dummyProduct2.name).length !== 0);
  });
});

it('shows an error is when shopping cart cannot be fetched', async () => {
  const fetchSpy = jest.spyOn(window, 'fetch')
    .mockImplementation(() => Promise.reject());
  const { getByText } = render(
    <ShoppingCart />
  );

  await waitFor(() => {
    expect(fetchSpy).toHaveBeenCalledTimes(1);
    expect(getByText('503')).toBeInTheDocument();
  });
});

it('tells the user if they have no items in their shopping cart', async () => {
  const fetchSpy = jest.spyOn(window, 'fetch')
    .mockImplementation(() => Promise.resolve(new Response(JSON.stringify([]))));
  const { getByText } = render(
    <ShoppingCart />
  );

  await waitFor(() => {
    expect(fetchSpy).toHaveBeenCalledTimes(1);
    expect(getByText('Nothing to see here. Add products to your cart to get started.')).toBeInTheDocument();
  });
});
