import React, { useState } from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import LogRocket from 'logrocket';
import styles from './App.module.css';
import NavBar from './components/navbar/NavBar';
import Footer from './components/footer/Footer';
import HomePage from './components/home/Homepage';
import Login from './components/Login/Login';
import { AppContext } from './components/utils/Context';
import CustomerPage from './components/customer/CustomerPage';
import ErrorPage from './components/error page/ErrorPage';
import ProductMap from './components/product map/ProductMap';
import WishList from './components/wish list/WishList';
import ShoppingCart from './components/shopping cart/ShoppingCart';

LogRocket.init('gqlrdv/quasar');

const App = () => {
  const [loggedOut, setLoggedState] = useState((sessionStorage.getItem('Email') != null));
  const user = sessionStorage.getItem('user');
  const [isLoggedIn, setIsLoggedIn] = useState(user);
  const [userEmail, setUserEmail] = useState(user);
  const [userObject, setUserObject] = useState();
  const [crt, setCrt] = useState(0);

  return (
    <>
      <div className={styles.app}>
        <AppContext.Provider value={{
          isLoggedIn, setIsLoggedIn, userEmail, setUserEmail, userObject, setUserObject, crt, setCrt
        }}
        >
          <BrowserRouter>
            <NavBar setLoggedState={setLoggedState} loggedOut={loggedOut} />
            <div className={styles.container}>
              <Switch>
                <Route exact path="/login" render={() => <Login />} />
                <Route exact path="/signup" render={() => <CustomerPage />} />
                <Route exact path="/" component={HomePage} />
                <Route exact path="/products/:demographic/:category/:type" render={() => <ProductMap />} />
                <Route exact path="/products/:demographic/:category" render={() => <ProductMap />} />
                <Route exact path="/products/:demographic" render={() => <ProductMap />} />
                {isLoggedIn && <Route exact path="/wishlist" component={WishList} />}
                <Route exact path="/cart" component={ShoppingCart} />
                <Route render={() => <ErrorPage errorCode={404} />} />
              </Switch>
            </div>
          </BrowserRouter>
        </AppContext.Provider>
      </div>
      <Footer />
    </>
  );
};

export default App;
