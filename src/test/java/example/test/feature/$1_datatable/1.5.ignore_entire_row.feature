Feature: Ignore Entire Row

  Scenario: Ignore Entire Row
    Given Add Person as following:
      | name | age@dob    |
      | Bob  | 2000-05-09 |
     Then Person should be like following:
      | name | age | _ignoreRow |
      | Bob  |  17 |            |
      | Ann  |  15 | yes        |
