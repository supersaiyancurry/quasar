import React from 'react';
import { render } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import Footer from './Footer';

it('renders with the appropriate footer text', () => {
  const { getByText } = render(
    <MemoryRouter>
      <Footer />
    </MemoryRouter>
  );
  expect(getByText('© 2021 Sports Apparel Inc.')).toBeInTheDocument();
});
