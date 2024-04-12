Feature: Sample Playwright feature with BDD Style
@E2E
  Scenario: Sample use case using cucumber feature file
    Given User set the screen dimensions
    And user sets below environment properties
      | Property                         | Value         |
      | PLAYWRIGHT_SKIP_BROWSER_DOWNLOAD | 1             |
      | PLAYWRIGHT_BROWSERS_PATH         | ms-playwright |
    Then user launches website as "http://playwright.dev"
    Then user checks the title of page as "Playwright"
    Then user close browser

  @dummy @E2E
    Scenario:
      Given Dummy Test