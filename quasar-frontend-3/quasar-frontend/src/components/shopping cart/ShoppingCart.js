import React, { useCallback, useEffect, useState } from 'react';
import ProductCard from '../product card/ProductCard';
import ErrorPage from '../error page/ErrorPage';
import styles from './ShoppingCart.module.css';
import { QUASAR_API } from '../Constants';

/**
 * component that displays the current user's shopping cart
 */
const ShoppingCart = () => {
  const [cart, setCart] = useState([]);
  const [errorCode, setErrorCode] = useState();
  const [isLoading, setIsLoading] = useState(false);

  /**
   * Retrieves the shopping cart information from the api by customer
   */
  const getCart = async () => {
    setErrorCode();
    setIsLoading(true);
    let serverError;
    await fetch(`${QUASAR_API}/customers/${sessionStorage.getItem('id')}/cart`, {
      method: 'GET',
      headers: new Headers({
        token: sessionStorage.getItem('token')
      })
    }).then(async (response) => {
      if (response.ok) {
        setCart(await response.json());
      } else {
        serverError = response.status;
      }
      setIsLoading(false);
    }).catch(() => {
      setErrorCode(serverError || 503);
    });
    setIsLoading(false);
  };
  const getCartCallBack = useCallback(getCart, []);
  useEffect(getCartCallBack, [getCartCallBack]);

  let pageContent;
  const products = cart.map(
    (product) => <ProductCard key={product.id} product={product} />
  );
  const prices = cart.map(
    (product) => product.price
  );
  if (isLoading) {
    pageContent = (
      <div className={styles.loading}>
        <p className="text-warning">One moment...</p>
      </div>
    );
  } else if (cart.length > 0) {
    pageContent = (
      <div>
        <p className={styles.price}>
          {`Subtotal: $
          ${prices.reduce((a, b) => a + b, 0).toFixed(2)}`}
        </p>
        <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 m-4">
          {products}
        </div>
      </div>
    );
  } else {
    pageContent = <h3>Nothing to see here. Add products to your cart to get started.</h3>;
  }

  return (
    <div>
      <h1 className={styles.title}>Shopping Cart</h1>
      {errorCode
        ? <ErrorPage errorCode={errorCode} />
        : pageContent}
    </div>
  );
};

export default ShoppingCart;
