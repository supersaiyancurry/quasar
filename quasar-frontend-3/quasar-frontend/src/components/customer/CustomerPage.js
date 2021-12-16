/* eslint-disable no-nested-ternary */
import React, { useState } from 'react';
import { useHistory } from 'react-router-dom';
import LogRocket from 'logrocket';
import { useAppContext } from '../utils/Context';
import FormField from '../form field/FormField';
import ErrorPage from '../error page/ErrorPage';
import {
  isValidEmail, isValidName, isValidPassword, isValidState, isValidZipCode, isValidPhone
} from '../regex/regex';
import { PASSWORD_INSTRUCTIONS, QUASAR_API, ERROR } from '../Constants';
import styles from './Customer.module.css';
import Button from '../button/Button';

const CustomerPage = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [phone, setPhone] = useState('');
  const [email, setEmail] = useState('');
  const [street, setStreet] = useState('');
  const [city, setCity] = useState('');
  const [state, setState] = useState('');
  const [zip, setZip] = useState('');
  const [password, setPassword] = useState('');
  const [reenterPassword, setReenterPassword] = useState('');
  const [image, setImage] = useState('');
  const [errorCode, setErrorCode] = useState();
  const history = useHistory();
  const { setIsLoggedIn, setUserEmail } = useAppContext();

  const [errors, setErrors] = useState({});

  const handleSubmit = async (event) => {
    event.preventDefault();

    const invalidEmail = !isValidEmail(email);
    if (invalidEmail) {
      LogRocket.error('Invalid email.');
    }
    const invalidFirst = !isValidName(firstName);
    if (invalidFirst) {
      LogRocket.error('Invalid first name.');
    }
    const invalidLast = !isValidName(lastName);
    if (invalidLast) {
      LogRocket.error('Invalid last name.');
    }
    const invalidPass = !isValidPassword(password);
    if (invalidPass) {
      LogRocket.error('Invalid password.');
    }
    const invalidReenterPass = password !== reenterPassword || reenterPassword === '';
    if (invalidReenterPass) {
      LogRocket.error('Password was not re-entered correctly.');
    }
    const invalidState = state && !isValidState(state);
    if (invalidState) {
      LogRocket.error('Invalid state. Must use a state abbreviation. (eg. KS)');
    }
    const invalidZip = zip && !isValidZipCode(zip);
    if (invalidZip) {
      LogRocket.error('Invalid zip code. Must fit the format XXXXX or XXXXX-XXXX');
    }
    const invalidPhone = phone && !isValidPhone(phone);
    if (invalidPhone) {
      LogRocket.error('Invalid phone number. Phone number should meet one of these formats: (111) 222-3333 | 1112223333 | 111-222-3333');
    }

    setErrors({
      email: invalidEmail,
      firstName: invalidFirst,
      lastName: invalidLast,
      password: invalidPass,
      reenterPassword: invalidReenterPass,
      state: invalidState,
      zip: invalidZip,
      phone: invalidPhone
    });

    // don't submit the form if any fields didn't pass validation
    if (invalidEmail || invalidFirst || invalidLast || invalidPass || invalidReenterPass
      || invalidState || invalidZip || invalidPhone) return;

    // creates customer object for request, placing null for optional fields that were not entered
    const createCustomer = {
      firstName,
      lastName,
      email,
      password,
      street: street !== '' ? street : null,
      city: city !== '' ? city : null,
      state: state !== '' ? state : null,
      zip: zip !== '' ? zip : null,
      phone: phone !== '' ? phone : null,
      image: image !== '' ? image : null
    };
    const customerLogin = {
      email,
      password
    };
    let serverError;
    await fetch(`${QUASAR_API}/customers`, {
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json'
      }),
      body: JSON.stringify(createCustomer)
    })
      .then((response) => {
        if (!response.ok) {
          serverError = response.status;
          throw new Error(<p>{ERROR}</p>);
        }
      })
      .then(() => {
        fetch(`${QUASAR_API}/customers/login`, {
          method: 'POST',
          headers: new Headers({
            'Content-Type': 'application/json'
          }),
          body: JSON.stringify(customerLogin)
        })
          .then(async (response) => {
            if (!response.ok) {
              serverError = response.status;
              throw new Error(<p>{ERROR}</p>);
            }
            const token = await response.text();
            sessionStorage.setItem('token', token);
            // parse JWT for payload containing user email
            const user = JSON.parse(atob(token.split('.')[1]));
            // set user email in storage
            sessionStorage.setItem('user', user.sub);
            history.push('/');
            setIsLoggedIn(true);
            setUserEmail(user.sub);
          });
        LogRocket.log(`User '${firstName} ${lastName}' was added to the database. Registered with the email: '${email}'`);
        LogRocket.log(`User '${firstName} ${lastName}' logged in with the email '${email}'`);
        LogRocket.identify('123456', {
          name: `${firstName} ${lastName}`,
          email: `${email}`
        });
      })
      .catch(() => {
        // if server doesn't respond with an error message, display a 503
        setErrorCode(serverError || 503);
        LogRocket.error(serverError || '503 error: the service is unavailable.');
        // scrolls back to the top so user can see the error
        document.body.scrollTop = 0; // For Safari
        document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
      });
  };

  return (
    <>
      {!errorCode
        ? (<div className={styles.signUp}>Welcome to the sign up page!</div>)
        : errorCode && errorCode === 409
          ? (<div className={styles.conflict}><ErrorPage errorCode={errorCode} /></div>)
          : errorCode && errorCode !== 409
            ? (<div className={styles.code}><ErrorPage errorCode={errorCode} /></div>)
            : (<div className={styles.signUp}>Welcome to the sign up page!</div>)}
      <div className={`container ${styles.container}`}>
        <div className={`jumbotron ${styles.jumbotron}`}>
          <form onSubmit={handleSubmit} noValidate>
            <h1 className={styles.title}>Register</h1>
            <FormField
              type="text"
              name="First Name*"
              value={firstName}
              changeCallback={setFirstName}
              error={errors.firstName}
              errorMessage="Must be a valid name"
              required
            />
            <FormField
              type="text"
              name="Last Name*"
              value={lastName}
              changeCallback={setLastName}
              error={errors.lastName}
              errorMessage="Must be a valid name"
              required
            />
            <FormField
              type="text"
              name="Email*"
              value={email}
              changeCallback={setEmail}
              error={errors.email}
              errorMessage="Must be a valid email"
              required
            />
            <div className="form-group row">
              <small className="col-sm-7 offset-4 mb-2">{PASSWORD_INSTRUCTIONS}</small>
            </div>
            <FormField
              type="password"
              name="Password*"
              value={password}
              changeCallback={setPassword}
              error={errors.password}
              errorMessage="Must be a valid password"
              required
            />
            <FormField
              type="password"
              name="Re-Enter Password*"
              value={reenterPassword}
              changeCallback={setReenterPassword}
              error={errors.reenterPassword}
              errorMessage="Must match first password"
              required
            />
            <FormField
              type="text"
              name="Street"
              value={street}
              changeCallback={setStreet}
            />
            <FormField
              type="text"
              name="City"
              value={city}
              changeCallback={setCity}
            />
            <FormField
              type="text"
              name="State"
              value={state}
              changeCallback={setState}
              error={errors.state}
              errorMessage="Must use a state abbreviation. (eg. KS)"
            />
            <FormField
              type="text"
              name="Zip Code"
              value={zip}
              changeCallback={setZip}
              error={errors.zip}
              errorMessage="Must fit the format XXXXX or XXXXX-XXXX"
            />
            <FormField
              type="phone"
              name="Phone Number"
              value={phone}
              changeCallback={setPhone}
              error={errors.phone}
              errorMessage="Phone number should meet one of these formats: (111) 222-3333 | 1112223333 | 111-222-3333"
            />
            <FormField
              type="file"
              name="Profile Image"
              value={image}
              changeCallback={setImage}
            />
            <Button text="Sign Up" type="submit" />
          </form>
        </div>
      </div>
    </>
  );
};

export default CustomerPage;
