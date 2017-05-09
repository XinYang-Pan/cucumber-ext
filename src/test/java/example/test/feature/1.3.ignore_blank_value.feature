Feature: Ignore Blank Value

  Scenario Outline: Ignore Blank Value
    Given Add Person as following:
      | name   | age |
      | <name> |  20 |
     Then person should be like following:
      | name   | age |
      | <name> |  20 |

    Examples: 
      | name | expected name |
      | Bob  | Bob           |
      |      | <null>        |
