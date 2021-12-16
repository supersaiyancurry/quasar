import React from 'react';
import styles from './SuccessMessage.module.css';

const SuccessMessage = ({ message }) => (
  <div className="container mt-3">
    <h2 className="mb-4">Success!</h2>
    <h3 className={styles.message}>
      {message}
    </h3>
  </div>
);

export default SuccessMessage;
