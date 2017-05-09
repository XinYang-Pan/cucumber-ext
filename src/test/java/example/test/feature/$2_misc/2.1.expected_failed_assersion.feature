Feature: Expected Failed

  Scenario: Expected to fail
    Given Add Person as following:
      | name | age |
      | Bob  |  80 |
     Then Failedable - Person should be like following:(expected to fail)
      | name | age |
      | Amy  |  17 |

  Scenario: Expected to succeed
    Given Add Person as following:
      | name | age |
      | Bob  |  80 |
     Then Failedable - Person should be like following:(expected to fail)
      | name | age |
      | Bob  |  80 |

  Scenario Outline: Expected to ignore
    Given Add Person as following:
      | name | age |
      | Bob  |  80 |
     Then Failedable - Person should be like following:(expected to ignore)
      | name | age   |
      | Bob  | <age> |

    Examples: 
      | age |
      |  80 |
      |  17 |
