import React from 'react';
import styles from './FormField.module.css';

/**
 * styled form controlled input wrapper. Can display errors
 * @param {string} type - the input type to use
 * @param {string} name - the text to show in the label
 * @param {function} changeCallback - the function to call when the input changes
 * @param {string} value - the current value of the input element
 * @param {boolean} error - true if validation failed for this field
 * @param {string} errorMessage - the message to display when there is an error
 */
const FormField = ({
  type, name, changeCallback, value, error, errorMessage, required, disabled
}) => (
  <div className="form-group row m-0">
    <label className={`col-sm-3 col-form-label ${styles.label}`} htmlFor={name}>{name}</label>
    <div className="col-sm-9">
      <input
        type={type}
        id={name}
        className={disabled ? styles.disabled : styles.input}
        onChange={(event) => changeCallback(event.target.value)}
        value={value}
        required={required}
        placeholder={name}
        disabled={disabled}
      />
      <p id={name} className={styles.error}>{error && errorMessage}</p>
    </div>
  </div>
);

export default FormField;
