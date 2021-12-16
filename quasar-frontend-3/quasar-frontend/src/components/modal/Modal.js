import React from 'react';
import styles from './Modal.module.css';

/**
 * Displays a modal for a product when the view Link on a product card is clicked
 * @param {Object} product - the product json object to display in the modal
 * @param {String} imageSrc - the route to retrieve the product image from
 */
const Modal = ({
  product, imageSrc, wishListBtn, shoppingCartBtn
}) => (
  <div className="modal fade" id={`${product.name.replace(/ /g, '')}Modal`} tabIndex="-1" role="dialog" aria-labelledby={`${product.name.replace(/ /g, '')}Label`} aria-hidden="true">
    <div className="modal-dialog" role="document">
      <div className={`modal-content card w-100 m-auto mt-5 ${styles.card}`}>
        <div className="modal-header">
          <h5 className={`modal-title ${styles.title}`} id={`${product.name.replace(/ /g, '')}Label`}>{product.name}</h5>
          <button type="button" className="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div className="modal-body">
          <div className="col">
            <div>
              <div className="card-body m-auto">
                <div className="text-center">
                  <img
                    className={`card-img-top img-fluid mb-3 ${styles.image}`}
                    src={imageSrc || 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAOEAAADhCAMAAAAJbSJIAAAAJ1BMVEXl5eX////t7e339/fm5ubu7u7q6urz8/P7+/vx8fH8/Pz4+Pji4uKci8yOAAADa0lEQVR4nO3Y2ZasKBRFUaRV5P+/txQ4gE1khd689VBjzZdIW9gIiKkUAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAADIrHbaP7lALyG4R1f8GjdV0di2faiKnc673FwuCUqlqdkOrPVX/kz1DkbKcGOpcsu11UD22HbTmIYizKsm0r2KuUh9SRjK3XvieSw+jgn3JLGcZXpCNxQx22tC0w+bc8J5TDhN+g8TTvYuYWwBSuk903cJl+ng2lOGhFO0PyYsV79IuJryXMJNQl/vLR1s7sW1hHHzKaFcL00x3yeMcThcEu53XetAmIORGr5K6FPStcUuCaWF57HLrdpaPU814ZqGc88JS83D1vh+bk11SbhdZctwWCThUu+aE5qU/FCL5wnrfeI1Ye8weWd5ZqaUE9THhEkSllHgU98ZPyRUpZWn+4S9hm8Tqg8J8yjSrYP4qYVQLfHstc4vj5xg39Be5tK8K0i9pKluE9YW0DWhqXetz7DsfP8My4S+XsfhnIPPUgl3Ggx9ponqOGeUhPnKPj+EGuE+oZKW7B1HXkird/FU9KOEwl0S+vIIFtm71CpeE863CcswbKe7nxP6S8JFHefS5wEPCaO6JAz1Ee8/Rqro+uX/lrA8w3a6NNDLhPOLl8WYMF9/SliCWyUVzYdNHww5YQwhmCB19ZmMw3V86CXvx3FY2tPVhOt+Vz0mXJ4PwlblTfB92x+ONk71uWLnv5hLl2nqU1ObLO8Tlkdnb+fSWA+9TOjT/XaSJWN9yEo6ot5PcPGL92Gp9WpTexu0zi6l5Ku2W/koh28SeqnAy4SnbbN1kODG8TDJs6tLlGi2VpWEey8NrZceE9Zl7WSWUFdDVhJKKeWFMh5uvTQs7W1hys8vJZS2zrUL2jmny5/qsMz8bl06LPN2eZrqi3Fzmp/ykL2uS02y/erfS5jGqX6S2veIX35b9FMm6XsfE5Y56SahXPP8A+r8PTiUXcaNfDaZdqJvVU7ffR8OEfyllP59uG3UqeT6fbi2SeFxQruvsexpO/PKj8esrkuzrSwf1nk2S367NHv2+nv8c19U50WR0TKM/FBK2xjnu+Gwkt9cg//uHwXp2ZhPu79UFQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD4//sHTLwZnXd2P/8AAAAASUVORK5CYII='}
                    alt={product.name}
                  />
                </div>
                <h6 className={`card-subtitle mb-2 font-weight-bold ${styles['category-tag']}`}>
                  {product.demographic}
                  {' '}
                  {product.category}
                </h6>
                <p className="card-text">{product.description}</p>
                <div className="row">
                  <b className="col-6">{`Price: $${product.price}`}</b>
                  <div className="col-6">
                    <b>Product Type: </b>
                    <span>{product.type}</span>
                  </div>
                  <div className="col-6 pt-2">
                    <b>Primary Color Swatch: </b>
                    <span style={{ backgroundColor: `${product.primaryColorCode}` }} className={`${styles.swatch}`} />
                  </div>
                  <div className="col-6 pt-2">
                    <b>Secondary Color Swatch: </b>
                    <span style={{ backgroundColor: `${product.secondaryColorCode}` }} className={`${styles.swatch}`} />
                  </div>
                </div>
                <div className={styles.wishlistcontainer}>
                  {wishListBtn}
                </div>
                <div className={styles.cartImageModal}>
                  {shoppingCartBtn}
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
);

export default Modal;
