Feature: Partial Fields Value Assertion
  I want to use this template for my feature file

  Scenario: 
    Given Add Person as following:
      | name | age |
      | Bob  |  10 |
     Then person should be like following:
      | name | age |
      | Bob  |     |
