import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import LogRocket from 'logrocket';
import ErrorPage from '../error page/ErrorPage';
import ProductCard from '../product card/ProductCard';
import { QUASAR_API, ERROR } from '../Constants';
import styles from './ProductMap.module.css';

const ProductMap = () => {
  const [products, setProducts] = useState([]);
  const [errorCode, setErrorCode] = useState();
  const [isLoading, setIsLoading] = useState(false);
  const { demographic, category, type } = useParams();

  useEffect(() => {
    let url;
    if (type) {
      url = `${QUASAR_API}/products/demographic/${demographic}/category/${category}/type/${type}`;
    } else if (category) {
      url = `${QUASAR_API}/products/demographic/${demographic}/category/${category}`;
    } else if (demographic) {
      url = `${QUASAR_API}/products/demographic/${demographic}`;
    } else {
      url = `${QUASAR_API}/products`;
    }

    const fetchData = async () => {
      let serverError;
      setIsLoading(true);
      await fetch(url, {
        method: 'GET',
        headers: new Headers({
          'Content-Type': 'application/json'
        })
      })
        .then((response) => {
          if (response.ok) {
            return response.json();
          }
          serverError = response.status;
          throw new Error(<p>{ERROR}</p>);
        })
        .then((productData) => {
          setProducts(productData);
          setIsLoading(false);
        })
        .catch(() => {
          // if server doesn't respond with an error message, display a 503
          setErrorCode(serverError || 503);
          LogRocket.error(serverError || '503: the service is unavailable');
          // scrolls back to the top so user can see the error
          document.body.scrollTop = 0; // For Safari
          document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
          setIsLoading(false);
        });
    };
    fetchData();
  }, [category, demographic, type]);

  return (
    <div>
      <nav aria-label="breadcrumb">
        <ol className={`breadcrumb ${styles.breadcrumb}`}>
          <li className={`breadcrumb-item ${styles.home}`}><a className={styles.link} href="/">Home</a></li>
          {demographic && <li className={`breadcrumb-item ${styles.item}`}><a className={styles.link} href={`/products/${demographic}`}>{demographic}</a></li>}
          {category && <li className={`breadcrumb-item ${styles.item}`}><a className={styles.link} href={`/products/${demographic}/${category}`}>{category}</a></li>}
          {type && <li className={`breadcrumb-item ${styles.item}`}><a className={styles.link} href={`/products/${demographic}/${category}/${type}`}>{type}</a></li>}
        </ol>
      </nav>
      <h3 className={styles.title}>Product Results</h3>
      {errorCode && <ErrorPage errorCode={errorCode} />}
      {isLoading && (
        <div className={styles.loading}>
          <p className="text-warning">One moment...</p>
          {LogRocket.log('Server is loading')}
        </div>
      )}
      {!isLoading && !errorCode && products.length === 0 && (
        <div className="d-flex justify-content-center">
          <h1 className={styles.empty}>Sorry, nothing to see here...</h1>
          {LogRocket.error('No products were retrieved with this fetch call')}
        </div>
      )}
      <div className="row row-cols-1 row-cols-lg-2 row-cols-xl-3 m-4">
        {products && products.map((product) => (
          <div key={product.id}>
            <ProductCard product={product} key={product.id} />
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductMap;
