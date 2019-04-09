Feature: Use Function - Assuming we were on 2017.1.1

  Scenario: Use Function
    Given Add Person as following:
      | name | age@dob    |
      | Bob  | 2001-05-09 |
     Then Person should be like following:
      | name | age |
      | Bob  |  15 |
