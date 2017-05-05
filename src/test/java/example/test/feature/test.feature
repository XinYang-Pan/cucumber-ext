Feature: Person Add
  I want to use this template for my feature file

  Scenario: 
    Given Add Person as following:
      | name | age | address.city |
      | Bob  |  10 | Dalian       |
     Then person should be like following:
      | name | age |
      | Bob  |  10 |
