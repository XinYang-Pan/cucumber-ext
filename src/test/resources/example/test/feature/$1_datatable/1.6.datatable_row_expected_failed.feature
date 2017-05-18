Feature: Datatable Row Expected Failed

  Scenario: Datatable Row Expected Failed
    Given Add Person as following:
      | name | age |
      | Bob  |  80 |
     Then Person should be like following:
      | name | age | _expectFail |
      | Amy  |  17 | yes         |
