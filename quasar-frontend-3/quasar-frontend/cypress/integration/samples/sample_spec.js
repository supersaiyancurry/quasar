/** Provides a sample of a passing test and a failing test for reference.
 *  With Cypress installed and running, click the test on the gui to run it.
*/

describe('First Test', () => {
  it('Shows a passing test', () => {
    expect(true).to.equal(true);
  });
});

describe('Second Test', () => {
  it('Shows a failing test', () => {
    expect(true).to.equal(false);
  });
});
