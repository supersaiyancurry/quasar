context('Startup', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000');
    cy.get('img[alt=logo]').should('be.visible');
    cy.get('button').should('be.visible');
  });

  it('Create customer happy path', () => {
    cy.get('button').contains('Login').click();
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
    cy.scrollTo('bottom');
    cy.get('button').contains('Sign Up').click({ force: true });
    cy.location('pathname', { timeout: 20000 }).should('eq', '/');
    cy.get('button').contains('Logout').click({ force: true });
  });

  it('Create customer validation error', () => {
    cy.get('button').contains('Logout').click();
    cy.get('button').contains('Login').click();
    cy.get('a[id=register]').should('be.visible');
    cy.get('a[id=register]').click();
    cy.location('pathname', { timeout: 10000 }).should('eq', '/signup');
    cy.get('[id="First Name*"]').focus().type('    ');
    cy.get('[id="Last Name*"]').focus().type('    ');
    cy.get('[id="Email*"]').focus().type('    ');
    cy.get('[id="Password*"]').focus().type('Password');
    cy.get('[id="Re-Enter Password*"]').focus().type('Password1');
    cy.get('[id="Street"]').focus().type('123 first ave');
    cy.get('[id="City"]').focus().type('Amsterdam');
    cy.get('[id="State"]').focus().type('    ');
    cy.get('[id="Zip Code"]').focus().type('    ');
    cy.get('[id="Phone Number"]').focus().type('123456789');
    cy.get('button').contains('Sign Up').click({ force: true });
    cy.get('p[id="First Name*"]').should('have.text', 'Must be a valid name');
    cy.get('p[id="Last Name*"]').should('have.text', 'Must be a valid name');
    cy.get('p[id="Email*"]').should('have.text', 'Must be a valid email');
    cy.get('p[id="Password*"]').should('have.text', 'Must be a valid password');

    cy.get('p[id="Re-Enter Password*"]').should(
      'have.text',
      'Must match first password'
    );
    cy.get('p[id="State"]').should(
      'have.text',
      'Must use a state abbreviation. (eg. KS)'
    );
    cy.get('p[id="Zip Code"]').should(
      'have.text',
      'Must fit the format XXXXX or XXXXX-XXXX'
    );
    cy.get('p[id="Phone Number"]').should(
      'have.text',
      'Phone number should meet one of these formats: (111) 222-3333 | 1112223333 | 111-222-3333'
    );
    cy.get('button').contains('Login').click();
  });

  it('Create customer conflict error', () => {
    cy.get('button').contains('Login').click();
    cy.get('a[id=register]').should('be.visible');
    cy.get('a[id=register]').click();
    cy.location('pathname', { timeout: 10000 }).should('eq', '/signup');
    cy.get('[id="First Name*"]').focus().type('Matt');
    cy.get('[id="Last Name*"]').focus().type('Nakagama');
    cy.get('[id="Email*"]').focus().type('ilovecoding@noname.com');
    cy.get('[id="Password*"]').focus().type('Password1!');
    cy.get('[id="Re-Enter Password*"]').focus().type('Password1!');
    cy.get('button').contains('Sign Up').click({ force: true });
    cy.get('h1', { timeout: 20000 }).should('have.text', '409Register');
    cy.get('h2').should('have.text', 'The server responded with a conflict.');
  });
});
