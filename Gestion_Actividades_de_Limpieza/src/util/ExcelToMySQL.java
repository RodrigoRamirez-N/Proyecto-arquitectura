package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import controller.ColoniaController;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelToMySQL {
    public static void main(String[] args) {
        ColoniaController coloniaController = new ColoniaController();
        
        System.out.println("Working directory: " + System.getProperty("user.dir"));

        try (FileInputStream fis = new FileInputStream("src\\util\\Colonias.xlsx");
            Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {

                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String nombreColonia = cell.getStringCellValue().trim();
                    try {
                        int idColonia = coloniaController.crearColonia(nombreColonia);
                        System.out.println("Colonia creada: " + nombreColonia + " con ID: " + idColonia);
                    } catch (NullPointerException e) {
                        System.out.println("Error: la celda está vacía o no es del tipo correcto.");
                    } catch (Exception e) {
                        System.out.println("Error inesperado: " + e.getMessage());
                    }
                }
            }

            System.out.println("Datos ingresados exitosamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
