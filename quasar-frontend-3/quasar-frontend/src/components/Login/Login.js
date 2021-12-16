/* eslint-disable no-nested-ternary */
import React, { useState } from 'react';
import { useHistory, Link } from 'react-router-dom';
import LogRocket from 'logrocket';
import { useAppContext } from '../utils/Context';
import { QUASAR_API, ERROR } from '../Constants';
// eslint-disable-next-line import/no-unresolved
import styles from './Login.module.css';
import FormField from '../form field/FormField';
import ErrorPage from '../error page/ErrorPage';
import Button from '../button/Button';
/**
 * Component for handling user login attempts.
 */
const Login = () => {
  const [password, setPassword] = useState('');
  const [email, setEmail] = useState('');
  const [error, setError] = useState(false);
  const [errorCode, setErrorCode] = useState(false);
  const history = useHistory();
  const { setIsLoggedIn, setUserEmail } = useAppContext();

  /**
     * Posts email and password to db to see if the information is valid.
     * @param {e} mouse click event
     */
  const handleSubmit = async (event) => {
    setError(false);
    event.preventDefault();
    const customerLogin = {
      email,
      password
    };
    let serverError;
    await fetch(`${QUASAR_API}/customers/login`, {
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json'
      }),
      body: (JSON.stringify(customerLogin))
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
        setIsLoggedIn(true);
        setUserEmail(user.sub);
        history.push('/');
        LogRocket.log(`User logged in with the email '${user.sub}'`);
      })
      .catch((credentials) => {
        if (credentials !== customerLogin || customerLogin === '') {
          LogRocket.error('Invalid email or password.');
          setError(true);
        }
        // if server doesn't respond with an error message, display a 503
        setErrorCode(serverError || 503);
        LogRocket.error(serverError || '503 error: the service is unavailable.');
      });
  };
  return (
    <>
      {/* nested ternary operator that checks for an error code, if there is no error code
      the title will be displayed, if there is an error code and the error code is a
      404 it will display the title, if it is not a 404, it will display the error code with
      an error message */}
      {!errorCode
        ? (<div className={`${styles.login}`}><p>Welcome to the login page!</p></div>)
        : errorCode && (errorCode === 404 || errorCode === 400)
          ? (<div className={`${styles.login}`}>Welcome to the login page!</div>)
          : errorCode && errorCode !== 404
            ? (<div className={`${styles.code}`}><ErrorPage errorCode={errorCode} /></div>)
            : (<div className={`${styles.login}`}>Welcome to the login page!</div>)}
      <div>
        <div className={`jumbotron ${styles.jumbotron}`}>
          <form onSubmit={handleSubmit}>
            <h2 className={styles.title}>Login</h2>
            <div>
              {/* an error message is diaplyed if the login details to not match a valid user */}
              {(errorCode === 404 || errorCode === 400) && error
                ? <h5 className={styles.error}> Invalid email or password</h5>
                : null}
            </div>
            <FormField
              type="email"
              name="Email"
              value={email}
              changeCallback={setEmail}
            />
            <FormField
              type="password"
              name="Password"
              value={password}
              changeCallback={setPassword}
            />
            <Button type="submit" text="Submit" />
            <div className="mt-3">
              New customer?
              <Link id="register" to="/signup" className={`${styles.link}`}>Sign up here!</Link>
            </div>
          </form>
        </div>
      </div>
    </>
  );
};
export default Login;
