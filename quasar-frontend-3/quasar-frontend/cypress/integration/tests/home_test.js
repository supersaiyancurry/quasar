context('Startup', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000');
    cy.get('img[alt=logo]').should('be.visible');
    cy.get('button').should('be.visible');
  });

  it('Login happy path', () => {
    cy.get('button').contains('Login').click();
    cy.location('pathname', { timeout: 10000 }).should('eq', '/login');
    cy.get('input[type=email]').should('be.visible');
    cy.get('input[type=password]').should('be.visible');
    cy.get('button').should('have.text', 'LoginSubmit').should('be.visible');
    cy.get('a[id=register]').should('be.visible');
    // Fill the username
    cy.get('input[type=email]').type('janed@gmail.com');
    // Fill the password
    cy.get('input[type=password]').type('Pa$$word');
    // Locate and submit the form
    cy.get('button')
      .should('have.text', 'LoginSubmit')
      .click({ multiple: true });
    // Verify the app redirected you to the homepage
    cy.location('pathname', { timeout: 10000 }).should('eq', '/');
    cy.get('button').contains('Logout').click({ force: true });
    cy.get('button').last().click();
  });
  it('Login sad path', () => {
    cy.get('button').contains('Logout').click();
    cy.get('button').contains('Login').click();
    cy.location('pathname', { timeout: 10000 }).should('eq', '/login');
    cy.get('input[type=email]').should('be.visible');
    cy.get('input[type=password]').should('be.visible');
    cy.get('button').should('have.text', 'LoginSubmit').should('be.visible');
    cy.get('a[id=register]').should('be.visible');
    // Fill the username
    cy.get('input[type=email]').type('janed@gmail.com');
    // Fill the password
    cy.get('input[type=password]').type('Password11!');
    // Locate and submit the form
    cy.get('button')
      .should('have.text', 'LoginSubmit')
      .click({ multiple: true });
    cy.get('h5').should('have.text', ' Invalid email or password');
    cy.get('input[type=email]').clear();
    cy.get('input[type=password]').clear();
    // Fill the username
    cy.get('input[type=email]').type(' ');
    // Fill the password
    cy.get('input[type=password]').type(' ');
    // Locate and submit the form
    cy.get('button')
      .should('have.text', 'LoginSubmit')
      .click({ multiple: true });
    // Verify the app redirected you to the homepage
    cy.get('h5').should('have.text', ' Invalid email or password');
    cy.get('button').first().should('have.text', 'Login');
  });
});
