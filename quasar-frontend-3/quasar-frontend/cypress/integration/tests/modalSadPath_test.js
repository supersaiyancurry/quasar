// the database should be disconnected before starting this test
context('Startup', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/products/men');
    cy.get('img[alt=logo]').should('be.visible');
    cy.get('button').should('be.visible');
  });

  it('Modal sad path', () => {
    cy.get('p').should('have.class', 'text-warning').contains('One moment...').should('be.visible');
    cy.get('h1', { timeout: 15000 }).contains('503').should('be.visible');
    cy.get('h2').contains('Oops, something went wrong.').should('be.visible');
  });
});
