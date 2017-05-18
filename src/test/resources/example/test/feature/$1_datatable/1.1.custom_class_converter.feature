Feature: Custom Class Converter

  Scenario: Custom Class Converter
    Given Add Person as following:
      | name | age | address.city | nickNames  |
      | Bob  |  10 | Liaoning/Dalian   | xy, magicb |
     Then Person should be like following:
      | name | age | address.city.province | address.city.name | nickNames  |
      | Bob  |  10 | Liaoning              | Dalian            | xy, magicb |
