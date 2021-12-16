import React from 'react';
import styles from './HomePage.module.css';

const HomePage = () => (
  <div className="container h-100">
    <h1 className={`text-center ${styles.heading}`}>Home Page</h1>
    <h4 className={`text-center mt-5 ${styles.heading}`}>Fun stuff to come!</h4>
  </div>
);

export default HomePage;
