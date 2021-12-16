import React from 'react';
import styles from './ErrorPage.module.css';
import { ERROR } from '../Constants';

const ErrorPage = ({ errorCode }) => (
  <div className="container mt-3">
    <h1 className={`mb-4 ${styles.error}`}>{errorCode}</h1>
    <h2 className={` ${styles['error-message']}`}>
      {errorCode === 409
        ? 'The server responded with a conflict.'
        : ERROR}
    </h2>
  </div>
);

export default ErrorPage;
