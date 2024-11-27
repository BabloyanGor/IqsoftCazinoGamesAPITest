package testCases;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;
import testCases.WebCasinoGames_Tests.BaseTest;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class RegClient {


    public RegClient() throws AWTException {
    }

    public static void main(String[] args) {
//        Register();

        System.out.println();
        readExel();
    }

    static java.util.List<String> FirstName = new ArrayList<>();
    static java.util.List<String> LastName = new ArrayList<>();
    static java.util.List<String> UserName = new ArrayList<>();
    static java.util.List<String> SecondName = new ArrayList<>();
    static java.util.List<String> Email = new ArrayList<>();
    static java.util.List<String> Mobile = new ArrayList<>();
    static List<String> MobileCode = new ArrayList<>();

    static String password = randomPassword(16);
    static String regUrl = "https://websitewebapi.craftbet.io/79/api/Main/RegisterClient";


    static String excelFilePath = "C:\\Users\\Nerses Khachatryan\\Downloads\\PlayerIO.xlsx";

    public static void readExel() {



        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            // Assuming the data is in the first sheet
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over each row and extract data from columns
            for (Row row : sheet) {
//                if (row.getRowNum() == 0) { // Skip header row if necessary
//                    continue;
//                }

                String firstNameS = (getCellValueAsString(row.getCell(1)));
                String UserNameS;
                if (getCellValueAsString(row.getCell(0)).isEmpty()){
                    UserNameS = firstNameS;
                }else{
                    UserNameS = (getCellValueAsString(row.getCell(0)));
                }
                String LastNameS = (getCellValueAsString(row.getCell(2)));
                String MobileS = (getCellValueAsString(row.getCell(3)));
                String MobileCodeS = getCellValueAsString(row.getCell(3)).substring(2);
                String EmailS = (getCellValueAsString(row.getCell(4)));

                // Get values from each column and add them to respective lists
                FirstName.add(firstNameS);
                LastName.add(LastNameS);
                UserName.add(UserNameS);
                Mobile.add(MobileS);
                MobileCode.add(MobileCodeS);
                Email.add(EmailS);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println();
    }


    public static String randomPassword(int length) {
        return RandomStringUtils.randomAlphanumeric(length) ;
    }

    private static String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return cell.toString();
        }
    }






    @Test
    public static void main() {
        int rows = 10; // Example with 3 rows
        int columns = 2;

        // Generate all possible configurations
        int totalConfigurations = (int) Math.pow(2, rows);
        for (int i = 0; i < totalConfigurations; i++) {
            String config = Integer.toBinaryString(i);
            config = String.format("%" + rows + "s", config).replace(' ', '0');
            System.out.println("Testing configuration: " + config);

            // Check if this configuration meets the criteria
            if (isValidConfiguration(config)) {
                System.out.println("Valid Configuration: " + config);
                // Optionally print or store the valid configuration
            }
        }
    }

    // Validate if the given configuration is correct based on game constraints
    private static boolean isValidConfiguration(String config) {
        // Add logic to validate based on the game’s constraints
        // e.g., check against revealed numbers and ensure it fits the given clues
        return true; // Placeholder, replace with actual validation logic
    }





}
