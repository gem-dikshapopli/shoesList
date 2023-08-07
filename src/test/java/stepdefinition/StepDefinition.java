package stepdefinition;

import implementation.DemoImplementation;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import java.io.IOException;

public class StepDefinition {
    @Given("Open Amazon and search for the shoes")
    public static void searchShoes(){
        DemoImplementation.openAndSearch();
    }
    @Then("In the brand section click on puma and Adidas")
    public static void brands() throws InterruptedException {
        DemoImplementation.brands();
    }
    @And("Make a workbook and sheet of the excel then Store the name of the brand in the first row of the excel then Store the name of the shoes under there respective brands")
    public static void workWithExcel() throws IOException, InterruptedException {
        DemoImplementation.printShoesNameInExcel();
    }
    @Then("Read the excel and parse into JSON format")
    public static void parseJson() throws IOException {
        DemoImplementation.readExcel();
    }
}
