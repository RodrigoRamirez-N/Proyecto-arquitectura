package util;

import java.sql.*;

public class Conexion {
    
    private String host="localhost";
    private String port= "3306";
    private String database= "limpieza"; //nombre de la base de datos
    private String username = "root";
    private String password = "rootpsw";

    // LO DE ARRIBA ES PARA LA CONEXION A LA BASE DE DATOS, A ESA LA PUEDES CAMBIAR A LA TUYA SI NO NO TE VA A FUNCIONAR

    public Connection cnx;
    public CallableStatement comando;
    private String driver = "com.mysql.cj.jdbc.Driver";
    private String url = "jdbc:mysql://"+host+":"+port+"/"+database+"?useSSL=false&useProcedureBodies=false";

    public Conexion() {
        try {
            Class.forName(driver);
            cnx = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al hacer la conexion: " + e.getMessage());
        }
    }


    public void closeConnection() {
        try {
            if (comando != null)
                comando.close();
            if (cnx != null)
                cnx.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }



    public void prepareCall(String sp, int numParams) throws SQLException {
        try {
            StringBuilder callString = new StringBuilder("CALL " + sp);
            if (numParams > 0) {
                callString.append("(");
                for (int i = 0; i < numParams; i++) {
                    callString.append("?");
                    if (i < numParams - 1) {
                        callString.append(",");
                    }
                }
                callString.append(")");
            }
            comando = cnx.prepareCall(callString.toString());
        } catch (SQLException e) {
            System.err.println("Error al preparar el procedimiento almacenado " + sp + ": " + e.getMessage());
            throw e;
        }
    }

    public void addInParameter(String parameterName, Object value) {
        try {
            comando.setObject(parameterName, value);
        } catch (Exception e) {
            System.out.println("Error al agregar el parametro " + parameterName + ": " + e.getMessage());
        }
    }

    public void addOutParameter(String parameterName, int sqlType) {
        try {
            comando.registerOutParameter(parameterName, sqlType);
        } catch (Exception e) {
            System.out.println("Error al agregar el parametro de salida " + parameterName + ": " + e.getMessage());
        }
    }

    public void execute() {
        try {
            comando.execute();
        } catch (SQLException e) {
            System.err.println("Error al ejecutar el procedimiento almacenado  : " + e.getMessage());
        }
    }

    public ResultSet executeResultSet() {
        try {
            return comando.executeQuery();
        } catch (SQLException e) {
            System.err
                    .println("Error al ejecutar el procedimiento almacenado y obtener el ResultSet: " + e.getMessage());

        }
        return null;

    }

    public <T> T getOutParameter(String parameterName, Class<T> type) {
        try {
            if (type == Integer.class) {
                return type.cast(comando.getInt(parameterName));
            } else if (type == String.class) {
                return type.cast(comando.getString(parameterName));
            } else if (type == Date.class) {
                return type.cast(comando.getDate(parameterName));
            } else if (type == Boolean.class) {
                return type.cast(comando.getBoolean(parameterName));
            } else {
                System.err.println("Tipo de dato no soportado: " + type.getName());
                throw new IllegalArgumentException("Tipo de dato no soportado: " + type.getName());
            }
        } catch (SQLException e) {
            System.err.println("Error en el getOutParameter " + parameterName + ": " + e.getMessage());
            throw new IllegalArgumentException("Error en el getOutParameter " + parameterName + ": " + e.getMessage());

        }
    }

    public void registerOutParameter(int position, int sqlType) {
        try {
            comando.registerOutParameter(position, sqlType);
        } catch (SQLException e) {
            System.err.println("Error al registrar parámetro de salida en posición " + position + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
   
}