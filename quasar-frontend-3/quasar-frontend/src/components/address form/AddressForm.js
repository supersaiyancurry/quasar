import React, { useState } from 'react';
import styles from './AddressForm.module.css';
import Button from '../button/Button';
import FormField from '../form field/FormField';
import { isValidState, isValidZipCode } from '../regex/regex';

const AddressForm = ({ user, handleSubmit, edit }) => {
  const [street, setStreet] = useState(user.street || '');
  const [city, setCity] = useState(user.city || '');
  const [zip, setZip] = useState(user.zip || '');
  const [state, setState] = useState(user.state || '');
  const [errors, setErrors] = useState({});

  const validateAddress = (event) => {
    event.preventDefault();
    const validState = isValidState(state);
    const validZip = isValidZipCode(zip);
    setErrors({
      street: !street,
      city: !city,
      state: !validState,
      zip: !validZip
    });
    if (validZip && validState && city && street) {
      handleSubmit(street, city, state, zip);
    }
  };

  const addressData = edit || user.street || user.city || user.state || user.zip;
  const enabled = street && city && state && zip;

  return (
    <form className={`${styles['form-container']} p-2 m-4`}>
      <h3 className="text-center">Address</h3>
      {addressData
        ? (
          <>
            <FormField
              type="text"
              name="Street"
              value={street}
              changeCallback={setStreet}
              error={errors.street}
              errorMessage="Street is required"
              disabled={!edit}
            />
            <FormField
              type="text"
              name="City"
              value={city}
              changeCallback={setCity}
              error={errors.city}
              errorMessage="City is required"
              disabled={!edit}
            />
            <FormField
              type="text"
              name="State"
              error={errors.state}
              errorMessage="Must use a state abbreviation. (eg. KS)"
              value={state}
              changeCallback={setState}
              disabled={!edit}
            />
            <FormField
              type="text"
              name="Zip Code"
              value={zip}
              error={errors.zip}
              errorMessage="Must fit the format XXXXX or XXXXX-XXXX"
              changeCallback={setZip}
              disabled={!edit}
            />
            {edit && <Button text="Submit" onClick={validateAddress} disabled={!enabled} />}
          </>
        ) : <p className="text-center text-dark mt-3">No Address Provided</p>}
    </form>
  );
};

export default AddressForm;
