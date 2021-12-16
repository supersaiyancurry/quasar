import React from 'react';
import { render, screen, cleanup } from '@testing-library/react';
import { createMemoryHistory } from 'history';
import { Router } from 'react-router-dom';
// eslint-disable-next-line import/no-unresolved
import Login from './Login';

afterEach(cleanup);

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
    <Login />
  );
  expect(screen.getByRole('button', { name: /Login/i }));
});

it('header component matches snapshot', () => {
  const { asFragment } = renderWithRouter(
    <Login />
  );

  expect(asFragment()).toMatchSnapshot();
});

it('has the correct URL', () => {
  global.window = { location: { pathname: null } };
  expect(global.window.location.pathname).toEqual('/');
});
