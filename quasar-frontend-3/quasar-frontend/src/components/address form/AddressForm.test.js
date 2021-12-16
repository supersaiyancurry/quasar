import React from 'react';
import { render, fireEvent } from '@testing-library/react';
import { MemoryRouter } from 'react-router-dom';
import AddressForm from './AddressForm';

const dummyUser = {
  firstName: 'Test',
  lastName: 'User',
  state: 'KS',
  city: 'Wichita',
  zip: 12345,
  street: '123 W. Hickory st'
};

const noAddressUser = {
  firstName: 'Test',
  lastName: 'User'
};

it('renders address fields in non-edit mode', () => {
  const { getByLabelText } = render(
    <MemoryRouter>
      <AddressForm user={dummyUser} />
    </MemoryRouter>
  );
  expect(getByLabelText('State').value === dummyUser.state);
  expect(getByLabelText('City').value === dummyUser.city);
  expect(getByLabelText('Zip Code').value === dummyUser.zip);
  expect(getByLabelText('Street').value === dummyUser.street);
});

it('shows error message when invalid inputs are given', () => {
  const mockCallback = jest.fn();
  const { getByText, getByLabelText } = render(
    <MemoryRouter>
      <AddressForm user={dummyUser} handleSubmit={mockCallback} edit />
    </MemoryRouter>
  );
  fireEvent.change(getByLabelText('State'), { target: { value: 'invalid state'}})
  fireEvent.click(getByText('Submit'));
  expect(mockCallback).not.toHaveBeenCalled();
  expect(getByText('Must use a state abbreviation. (eg. KS)')).toBeInTheDocument();
});

it('calls the submit function when valid inputs are entered', () => {
  const mockCallback = jest.fn();
  const { getByText, getByLabelText } = render(
    <MemoryRouter>
      <AddressForm user={dummyUser} handleSubmit={mockCallback} edit />
    </MemoryRouter>
  );
  fireEvent.change(getByLabelText('City'), { target: { value: 'new city'}})
  fireEvent.click(getByText('Submit'));
  expect(mockCallback).toHaveBeenCalledTimes(1);
});

it('shows a message if the user has no address info stored', () => {
  const { getByText } = render(
    <MemoryRouter>
      <AddressForm user={noAddressUser} />
    </MemoryRouter>
  );
  expect(getByText('No Address Provided')).toBeInTheDocument();
});

it('disables the submit button when a required field is empty', () => {
  const { getByText } = render(
    <MemoryRouter>
      <AddressForm user={noAddressUser} edit />
    </MemoryRouter>
  );
  expect(getByText('Submit').disabled == true);
});