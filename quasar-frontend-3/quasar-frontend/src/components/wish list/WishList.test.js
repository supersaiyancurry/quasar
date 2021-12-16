import React from 'react';
import { render, waitFor } from '@testing-library/react';
import WishList from './WishList';

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

const dummyWishList = [dummyProduct1, dummyProduct2];

it('renders properly', () => {
  const { getByText } = render(
    <WishList />
  );

  expect(getByText('Wish List')).toBeInTheDocument();
});

it('loads wishlist given a list of products', async () => {
  const fetchSpy = jest.spyOn(window, 'fetch')
    .mockImplementation(() => Promise.resolve(new Response(JSON.stringify(dummyWishList))));
  const { getAllByText } = render(
    <WishList />
  );

  await waitFor(() => {
    expect(fetchSpy).toHaveBeenCalledTimes(1);
    expect(getAllByText(dummyProduct1.name).length !== 0);
    expect(getAllByText(dummyProduct2.name).length !== 0);
  });
});

it('shows an error is when wishlist cannot be fetched', async () => {
  const fetchSpy = jest.spyOn(window, 'fetch')
    .mockImplementation(() => Promise.reject());
  const { getByText } = render(
    <WishList />
  );

  await waitFor(() => {
    expect(fetchSpy).toHaveBeenCalledTimes(1);
    expect(getByText('503')).toBeInTheDocument();
  });
});

it('tells the user if they have no items in their wishlist', async () => {
  const fetchSpy = jest.spyOn(window, 'fetch')
    .mockImplementation(() => Promise.resolve(new Response(JSON.stringify([]))));
  const { getByText } = render(
    <WishList />
  );

  await waitFor(() => {
    expect(fetchSpy).toHaveBeenCalledTimes(1);
    expect(getByText('No items in wishlist')).toBeInTheDocument();
  });
});
