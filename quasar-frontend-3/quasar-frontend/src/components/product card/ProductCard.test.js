import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import ProductCard from './ProductCard';

const dummyProduct = {
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

it('renders properly', () => {
  const { getByText } = render(
    <MemoryRouter>
      <ProductCard product={dummyProduct} />
    </MemoryRouter>
  );
  expect(getByText('Slim Hockey Belt')).toBeInTheDocument();
});
