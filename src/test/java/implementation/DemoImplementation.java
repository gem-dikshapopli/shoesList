package implementation;
import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import com.google.gson.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.bidi.log.Log;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class DemoImplementation {
    static WebDriver driver=new ChromeDriver();
    static JavascriptExecutor executor=(JavascriptExecutor) driver;
    static SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
    static Date date = new Date();
    static final Logger logger = LoggerFactory.getLogger(Log.class);
    static ArrayList<String> adidasList=new ArrayList<>();
    static ArrayList<String> pumaList=new ArrayList<>();


    public static void openAndSearch(){
        driver.get("https://www.amazon.in/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().window().maximize();
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Shoes"+ Keys.ENTER);
    }

    public static void brands() throws InterruptedException {
        Thread.sleep(2000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        executor.executeScript("arguments[0].scrollIntoView();", driver.findElement(By.id("brandsRefinements")));
        driver.findElement(By.xpath("//li[@id='p_89/Adidas']//child::i[@class='a-icon a-icon-checkbox']")).click();
        driver.findElement(By.xpath("//li[@id='p_89/Puma']//child::i[@class='a-icon a-icon-checkbox']")).click();
    }


    public static void printShoesNameInExcel() throws IOException, InterruptedException {

        List list=driver.findElements(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal']"));
        Thread.sleep(1000);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        int a;
        for(int i=0;i<50;i++) {
            a = i + 1;
            String str = driver.findElement(By.xpath("(//span[@class='a-size-base-plus a-color-base a-text-normal']//preceding::div[@class='a-row a-size-base a-color-secondary']//descendant::span)[" + a + "]")).getText();
            String names = driver.findElement(By.xpath("(//span[@class='a-size-base-plus a-color-base a-text-normal'])[" + a + "]")).getText();
            if (str.equalsIgnoreCase("Adidas")) {
                adidasList.add(names);
            } else if (str.equalsIgnoreCase("Puma")) {
                pumaList.add(names);
            }
            else{
                continue;
            }
        }
        Thread.sleep(2000);
        XSSFWorkbook workbook = new XSSFWorkbook();
        // spreadsheet object
        XSSFSheet spreadsheet = workbook.createSheet("List of Shoes");

        // creating a row object
        Row row;
        Map<String, Object[]> shoes = new TreeMap<String, Object[]>();
        shoes.put("1",new Object[]{"Adidas","Puma"});
        int count=2;
        int m;
        for(m=0;m<pumaList.size();m++){
            shoes.put(""+count,new Object[]{(adidasList.get(m)),(pumaList.get(m))});
            System.out.println(count+" : "+adidasList.get(m)+" : "+pumaList.get(m));
            count++;
        }
        System.out.println("count = "+count+"m = "+m);
        for(int n=m;n<adidasList.size();n++){
            shoes.put(""+count,new Object[]{(adidasList.get(n))});
            System.out.println(count+" : "+adidasList.get(n)+" : "+"-");
            count++;
        }
        // writing on the Excel
        Set<String> keyId = shoes.keySet();
        int rowId = 0;
        for (String key : keyId) {

            row = spreadsheet.createRow(rowId++);
            Object[] objectArr = shoes.get(key);
            int cellId = 0;
            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellId++);
                cell.setCellValue(obj.toString());
            }
        }

        //formatter class so that every time new file is created in date and time format
        FileOutputStream out = new FileOutputStream(new File("C:/Users/Diksha.Popli/Desktop/shoesExcel.xlsx"));
        workbook.write(out);
        out.close();

    }


    public static void readExcel() throws IOException {
        FileInputStream file = new FileInputStream("C:/Users/Diksha.Popli/Desktop/shoesExcel.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheet = workbook.getSheet("List of Shoes");

        JsonObject mainJson=new JsonObject();

        List adidasList1=new ArrayList<>();
        List pumaList1=new ArrayList<>();

        for (int i=1;i<= sheet.getLastRowNum();i++){
            String value = sheet.getRow(i).getCell(0).getStringCellValue();
            adidasList1.add(value);
        }
        for (int i=1;i<= sheet.getLastRowNum();i++){
            try {
                String value = sheet.getRow(i).getCell(1).getStringCellValue();
                pumaList1.add(value);
            }
            catch (Exception e){
                continue;
            }
        }

        // To convert ArrayList in jsonArray
        JsonArray jsonArray1 = new Gson().toJsonTree(adidasList1).getAsJsonArray();
        JsonArray jsonArray2 = new Gson().toJsonTree(pumaList1).getAsJsonArray();

        //Added those list in main json body
        mainJson.add("adidas", String.valueOf(jsonArray1));
        mainJson.add("puma", String.valueOf(jsonArray2));

        String str=mainJson.toString();
        System.out.println("********************************* Parsing The JSON Object***************************");
        System.out.println(str);
        driver.quit();
    }

}
