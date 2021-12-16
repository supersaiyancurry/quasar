import React, { useState } from 'react';
import ReactTooltip from 'react-tooltip';
import { useAppContext } from '../utils/Context';
import styles from './ProductCard.module.css';
import Modal from '../modal/Modal';
import { DEFAULT_IMAGE } from '../Constants';

/**
 * displays a product card to the UI when given its JSON Object
 * @param {Object} product - the product json object to display on the card
 * @param {String} imageSrc - the route to retrieve the product image from
 */
const ProductCard = ({ product, imageSrc }) => {
  const {
    userObject, setUserObject, isLoggedIn, setCrt
  } = useAppContext();
  const [onWishList, setOnWishList] = useState(
    userObject && userObject.wishList.includes(product.id)
  );
  const [onShoppingCart, setOnShoppingCart] = useState(
    userObject && userObject.shoppingCart.includes(product.id)
  );

  /**
   * sends a request to add a product to the user's wishlist
   */
  const addToWishList = async () => {
    await fetch(`http://localhost:8080/customers/${sessionStorage.getItem('id')}/wishlist`, {
      method: 'PUT',
      headers: new Headers({
        token: sessionStorage.getItem('token'),
        'Content-Type': 'application/json'
      }),
      body: JSON.stringify(product)
    }).then(async (response) => {
      if (response.ok) {
        const updatedUser = await response.json();
        setUserObject(updatedUser);
        setOnWishList(true);
      }
    });
  };

  const addToShoppingCart = async () => {
    await fetch(`http://localhost:8080/customers/${sessionStorage.getItem('id')}/cart`, {
      method: 'PUT',
      headers: new Headers({
        token: sessionStorage.getItem('token'),
        'Content-Type': 'application/json'
      }),
      body: JSON.stringify(product)
    }).then(async (response) => {
      if (response.ok) {
        const updatedUser = await response.json();
        setUserObject(updatedUser);
        setOnShoppingCart(true);
        setCrt(updatedUser.shoppingCart.length);
      }
    });
  };

  let wishListBtn;
  let shoppingCartBtn;
  if (isLoggedIn) {
    if (onWishList) {
      wishListBtn = (
        <button
          type="button"
          className={`btn col-auto offset-7 ${styles['wishlist-btn']}`}
          data-tip
          data-for={`wish-list-tip-${product.id}`}
        >
          <i className="fas fa-heart" />
          <ReactTooltip id={`wish-list-tip-${product.id}`} place="right" effect="solid">
            Item is in your wishlist
          </ReactTooltip>
        </button>
      );
    } else {
      wishListBtn = (
        <button
          type="button"
          className={`btn col-auto offset-7 ${styles['wishlist-btn']}`}
          onClick={addToWishList}
          data-tip
          data-for={`add-to-wish-list-tip-${product.id}`}
        >
          <i className="far fa-heart" />
          <ReactTooltip id={`add-to-wish-list-tip-${product.id}`} place="right" effect="solid">
            Add to wishlist
          </ReactTooltip>
        </button>
      );
    }
    if (onShoppingCart) {
      shoppingCartBtn = (
        <button className={styles.cartImageProduct} type="button" data-tip data-for={`shoppingcart-tip-${product.id}`}>
          <i className="fas fa-cart-arrow-down" />
          <ReactTooltip id={`shoppingcart-tip-${product.id}`} place="right" effect="solid">
            Item is in your shopping cart!
          </ReactTooltip>
        </button>
      );
    } else {
      shoppingCartBtn = (
        <button className={styles.cartImageProduct} type="button" onClick={addToShoppingCart} data-tip data-for={`add-to-shoppingcart-tip-${product.id}`}>
          <i className="fas fa-cart-plus" />
          <ReactTooltip id={`add-to-shoppingcart-tip-${product.id}`} place="right" effect="solid">
            Add to shopping cart
          </ReactTooltip>
        </button>
      );
    }
  }

  return (
    <div className="col p-3" data-testid="products">
      <div className={`card border border-dark w-100 m-auto mt-5 ${styles.card}`}>
        <div className="card-body m-auto" key={product.id}>
          <div className="text-center">
            <img
              className={`card-img-top img-fluid mb-3 ${styles.image}`}
              src={imageSrc || DEFAULT_IMAGE}
              alt={product.name}
            />
          </div>
          <h5 className="card-title text-center">{`${product.name}`}</h5>
          <h6 className={`card-subtitle mb-2 font-weight-bold ${styles['category-tag']}`} data-testid="category">{`${product.category}`}</h6>
          <p className={`card-text ${styles.description}`} data-testid="description">{`${product.description}`}</p>
          <div className="row">
            <b className="col-6">{`Price: $${product.price.toFixed(2)}`}</b>
            <div className="col-6">
              <b>Product Type: </b>
              <span>{`${product.type}`}</span>
            </div>
          </div>
        </div>
        <div className="mb-1">
          <button
            type="button"
            style={{ color: '#007bff' }}
            className={`col-3 btn link ${styles.button}`}
            data-toggle="modal"
            data-target={`#${product.name.replace(/ /g, '')}Modal`}
          >
            View
          </button>
          {wishListBtn}
          {shoppingCartBtn}
        </div>
      </div>
      <div>
        <Modal
          product={product}
          imageSrc={imageSrc}
          wishListBtn={wishListBtn}
          shoppingCartBtn={shoppingCartBtn}
        />
      </div>
    </div>
  );
};

export default ProductCard;
