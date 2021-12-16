/** The Cypress tester should navigate to and display home page with this test.
 *  Test also verifies that homepage is located at localhost:3000.
 *  App must be running for this test
 */

describe('Navigate to Home Page', () => {
  it('successfully loads', () => {
    cy.visit('/');

    cy.url().should('include', '3000');
  });
});
