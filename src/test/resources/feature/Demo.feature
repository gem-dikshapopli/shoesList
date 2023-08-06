@Demo1
Feature: Data Handling with excel
  Scenario: Storing Shoes According to the brand in the excel
    Given Open Amazon and search for the shoes
    Then In the brand section click on puma and Adidas
    And Make a workbook and sheet of the excel then Store the name of the brand in the first row of the excel then Store the name of the shoes under there respective brands
    Then Read the excel and parse into JSON format