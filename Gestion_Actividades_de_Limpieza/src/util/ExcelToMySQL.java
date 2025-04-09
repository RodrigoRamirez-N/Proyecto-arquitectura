package util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.Conexion;

public class ExcelToMySQL {
    public static void main(String[] args) {
        Conexion conexion = new Conexion();

        try (FileInputStream fis = new FileInputStream(excelFilePath);
             Workbook workbook = new XSSFWorkbook(fis))
             {

            Sheet sheet = workbook.getSheetAt(0);
            String sql = "INSERT INTO tu_tabla (columna1, columna2, columna3) VALUES (?, ?, ?)";
            PreparedStatement statement = ((Connection) conexion).prepareStatement(sql);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) { // Saltar la fila de encabezado
                    continue;
                }

                String columna1 = row.getCell(0).getStringCellValue();
                double columna2 = row.getCell(1).getNumericCellValue();

                statement.setString(1, columna1);
                statement.setDouble(2, columna2);

                statement.addBatch();
            }

            statement.executeBatch();
            System.out.println("Datos importados exitosamente.");

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}