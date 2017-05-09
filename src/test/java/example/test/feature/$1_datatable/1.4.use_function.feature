Feature: Use Function

  Scenario: Use Function
    Given Add Person as following:
      | name | age@dob    |
      | Bob  | 2000-05-09 |
     Then Person should be like following:
      | name | age |
      | Bob  |  17 |
