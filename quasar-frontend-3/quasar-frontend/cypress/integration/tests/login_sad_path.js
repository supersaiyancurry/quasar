// the database should be disconnected before starting this test
context('Startup', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000');
    cy.get('img[alt=logo]').should('be.visible');
    cy.get('button').should('be.visible');
    cy.get('button').click();
  });

  it('Login sad path', () => {
    cy.get('button').click({ multiple: true });
    cy.location('pathname', { timeout: 10000 }).should('eq', '/login');
    cy.get('input[type=email]').should('be.visible');
    cy.get('input[type=password]').should('be.visible');
    cy.get('button').should('have.text', 'LoginSubmit');
    cy.get('a[id=register]').should('be.visible');
    // Fill the username
    cy.get('input[type=email]').type('janed@gmail.com');
    // Fill the password
    cy.get('input[type=password]').type('Pa$$word');
    // Locate and submit the form
    cy.get('button')
      .should('have.text', 'LoginSubmit')
      .click({ multiple: true });
    cy.get('h1', { timeout: 21000 })
      .should('have.text', '503')
      .should('be.visible');
    cy.get('h2')
      .should('have.text', 'Oops, something went wrong.Login')
      .should('be.visible');
  });
});
