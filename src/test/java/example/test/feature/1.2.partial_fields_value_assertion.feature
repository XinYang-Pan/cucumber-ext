Feature: Partial Fields Value Assertion

  Scenario: Partial Fields Value Assertion
    Given Add Person as following:
      | name | age |
      | Bob  |  10 |
     Then person should be like following:
      | name | age |
      | Bob  |     |
