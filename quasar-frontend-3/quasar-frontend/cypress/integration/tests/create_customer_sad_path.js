// the database should be disconnected before starting this test
context('Startup', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000');
    cy.get('img[alt=logo]').should('be.visible');
    cy.get('button').should('be.visible');
    cy.get('button').click();
  });

  it('Create cutomer sad path', () => {
    cy.get('a[id=register]').should('be.visible');
    cy.get('a[id=register]').click();
    cy.location('pathname', { timeout: 10000 }).should('eq', '/signup');
    cy.get('[id="First Name*"]').focus().type('Matt');
    cy.get('[id="Last Name*"]').focus().type('Nakagama');
    cy.get('[id="Email*"]').focus().type('ilovecoding@noname.com');
    cy.get('[id="Password*"]').focus().type('Password1!');
    cy.get('[id="Re-Enter Password*"]').focus().type('Password1!');
    cy.get('[id="Street"]').focus().type('123 first ave');
    cy.get('[id="City"]').focus().type('Amsterdam');
    cy.get('[id="State"]').focus().type('FL');
    cy.get('[id="Zip Code"]').focus().type('12345');
    cy.get('[id="Phone Number"]').focus().type('1234567890');
    cy.get('[id="Profile Image"]').should('be.visible');
    cy.get('button')
      .contains('Sign Up')
      .click({ force: true }, { multiple: true });
    cy.get('h1', { timeout: 35000 })
      .should('have.text', '503Register')
      .should('be.visible');
    cy.get('h2')
      .should('have.text', 'Oops, something went wrong.')
      .should('be.visible');
  });
});
