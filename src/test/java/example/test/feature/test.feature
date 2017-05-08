Feature: Person Add
  I want to use this template for my feature file

  Scenario: 
    Given Add Person as following:
      | name | age | address.city.name | nickNames  |
      | Bob  |  10 | Liaoning/Dalian   | xy, magicb |
     Then person should be like following:
      | name | age | address.city.province | address.city.name | nickNames  |
      | Bob  |  10 | Liaoning              | Dalian            | xy, magicb |
