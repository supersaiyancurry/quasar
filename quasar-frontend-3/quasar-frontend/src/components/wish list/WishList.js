import React, { useCallback, useEffect, useState } from 'react';
import ProductCard from '../product card/ProductCard';
import ErrorPage from '../error page/ErrorPage';
import styles from './WishList.module.css';

/**
 * component that displays the current user's wishlist
 */
const WishList = () => {
  const [wishList, setWishList] = useState([]);
  const [errorCode, setErrorCode] = useState();
  const [isLoading, setIsLoading] = useState(false);

  const getWishList = async () => {
    setErrorCode();
    setIsLoading(true);
    let serverError;
    await fetch(`http://localhost:8080/customers/${sessionStorage.getItem('id')}/wishlist`, {
      method: 'GET',
      headers: new Headers({
        token: sessionStorage.getItem('token')
      })
    }).then(async (response) => {
      if (response.ok) {
        setWishList(await response.json());
      } else {
        serverError = response.status;
      }
      setIsLoading(false);
    }).catch(() => {
      setErrorCode(serverError || 503);
    });
    setIsLoading(false);
  };
  const getWishListCallBack = useCallback(getWishList, []);
  useEffect(getWishListCallBack, [getWishListCallBack]);

  let pageContent;
  if (isLoading) {
    pageContent = (
      <div className={styles.loading}>
        <p className="text-warning">One moment...</p>
      </div>
    );
  } else if (wishList.length > 0) {
    pageContent = (
      <div className="row row-cols-1 row-cols-md-2 row-cols-lg-3 m-4">
        {wishList.map((product) => <ProductCard key={product.id} product={product} />)}
      </div>
    );
  } else {
    pageContent = <h3>No items in wishlist</h3>;
  }

  return (
    <div>
      <h1 className={styles.title}>Wish List</h1>
      {errorCode
        ? <ErrorPage errorCode={errorCode} />
        : pageContent}
    </div>
  );
};

export default WishList;
