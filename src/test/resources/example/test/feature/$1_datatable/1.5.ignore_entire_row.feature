Feature: Ignore Entire Row (Assuming we are in year of 2017.1.1)

  Scenario: Ignore Entire Row
    Given Add Person as following:
      | name | age@dob    |
      | Bob  | 2001-05-09 |
     Then Person should be like following:
      | name | age | _ignoreRow |
      | Bob  |  15 |            |
      | Ann  |  15 | yes        |
