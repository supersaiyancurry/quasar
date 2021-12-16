import React from 'react';
import styles from './Footer.module.css';

/**
 * Page footer component used to display copyright information at the bottom of each page.
 */
const Footer = () => (
  <div className={styles.container}>
    <footer className={`p-3 mt-5 ${styles.footer}`}>
      <small className="text-left ml-4">&copy; 2021 Sports Apparel Inc.</small>
    </footer>
  </div>
);

export default Footer;
