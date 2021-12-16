context('Startup', () => {
  beforeEach(() => {
    cy.visit('http://localhost:3000/products/men');
    cy.get('img[alt=logo]').should('be.visible');
    cy.get('button').should('be.visible');
  });

  it('Modal happy path', () => {
    cy.get('button', { timeout: 10000 }).contains('View').should('be.visible');
    // Click a View Link
    cy.get('button').contains('View').click();
    // Check that all elements of a modal are present
    cy.get('div').should('have.class', 'modal fade show').should('be.visible');
    cy.get('button').should('have.class', 'close').should('be.visible');
    cy.get('h5').should('have.class', 'modal-title').should('be.visible');
    cy.get('img').should('have.class', 'card-img-top').should('be.visible');
    cy.get('h6')
      .should('have.class', 'card-subtitle mb-2 font-weight-bold')
      .should('be.visible');
    cy.get('p').should('have.class', 'card-text').should('be.visible');
    cy.get('div')
      .should('have.class', 'col-6')
      .contains('$')
      .should('be.visible');
    cy.get('div').contains('Product Type').should('be.visible');
    cy.get('div')
      .should('have.class', 'col-6 pt-2')
      .contains('Primary Color Swatch:')
      .scrollIntoView()
      .should('be.visible');
    cy.get('div')
      .should('have.class', 'col-6 pt-2')
      .contains('Secondary Color Swatch:')
      .should('be.visible');
  });
});
