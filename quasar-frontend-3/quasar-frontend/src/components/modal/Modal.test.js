import React from 'react';
import { render } from '@testing-library/react';
import { createMemoryHistory } from 'history';
import { Router, MemoryRouter } from 'react-router-dom';
import Modal from './Modal';

const product = {
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
};

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
    <Modal
      product={product}
    />
  );
});

it('renders properly', () => {
  const { getByText } = render(
    <MemoryRouter>
      <Modal
        product={product}
      />
    </MemoryRouter>
  );
  expect(getByText('Heavy Duty Soccer Hoodie')).toBeInTheDocument();
});
