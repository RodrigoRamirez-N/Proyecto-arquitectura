package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;

public class ExcelToMySQL {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();

        try (FileInputStream fis = new FileInputStream("Gestion_Actividades_de_Limpieza\\src\\util\\Colonias.xlsx");
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);
            String call = "{CALL sp_CreateColonia(?)}";

            for (Row row : sheet) {

                Cell cell = row.getCell(0);
                if (cell != null && cell.getCellType() == CellType.STRING) {
                    String nombreColonia = cell.getStringCellValue().trim();

                    try (CallableStatement statement = conexion.cnx.prepareCall(call)) {
                        statement.setString(1, nombreColonia);
                        statement.execute();
                    } catch (SQLException e) {
                        System.err.println("Error al insertar: " + nombreColonia);
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Datos importados exitosamente.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
