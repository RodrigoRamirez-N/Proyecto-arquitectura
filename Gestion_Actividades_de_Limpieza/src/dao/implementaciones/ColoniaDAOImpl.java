package dao.implementaciones;

import dao.interfaces.ColoniaDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Colonia;
import util.Conexion;

public class ColoniaDAOImpl implements ColoniaDAO{

	public int create(Colonia colonia) throws SQLException {
        Conexion conexion = new Conexion();
        try {
            // Si usas un SP con parámetro OUT para el ID:
            conexion.prepareCall("sp_CreateColonia", 2); // 2 parámetros (nombre, OUT id)
            conexion.comando.setString(1, colonia.getNombreColonia());
            conexion.registerOutParameter(2, java.sql.Types.INTEGER);
            conexion.comando.execute();
            
            int nuevoId = conexion.comando.getInt(2); // Recuperar el ID generado
            return nuevoId;
        } catch (SQLException e) {
            System.err.println("Error al crear colonia: " + e.getMessage());
            return -1;
        } finally {
            conexion.closeConnection();
        }
    }

	@Override
	public Colonia read(int idColonia) throws SQLException {
		Conexion conn = new Conexion();
        Colonia colonia = null;

        try {
            String call = "{call sp_GetColoniaById(?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idColonia);

            ResultSet rs = conn.comando.executeQuery();

            if (rs.next()) {
                colonia = new Colonia(
                    rs.getInt("cve_Colonia"),
                    rs.getString("NombreColonia")
                );
            } else {
                System.out.println("No se encontró la colonia con ID: " + idColonia);
            }

        } catch (SQLException e) {
            System.out.println("Error al leer colonia: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return colonia;
	}

	@Override
	public void update(Colonia colonia) throws SQLException{
		Conexion conn = new Conexion();

        try {
            String call = "{call sp_UpdateColonia(?, ?)}";
            conn.comando = conn.cnx.prepareCall(call);

            conn.comando.setInt(1, colonia.getCveColonia());
            conn.comando.setString(2, colonia.getNombreColonia());

            int rowsAffected = conn.comando.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Colonia actualizada con ID: " + colonia.getCveColonia());
            } else {
                System.err.println("(ifStmt)Error al actualizar colonia: ID no válido");
            }
            
        } catch (SQLException e) {
            System.err.println("Error al leer colonia: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
	}

	@Override
	public boolean delete(int idColonia) {
		Conexion conn = new Conexion();
        boolean result = false;

        try {
            String call = "{call sp_DeleteColonia(?)}";
            conn.comando = conn.cnx.prepareCall(call);
            conn.comando.setInt(1, idColonia);

            int rowsAffected = conn.comando.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Colonia eliminada con ID: " + idColonia);
                result = true;
            } else {
                result = false;
                System.err.println("(ifStmt)Error al eliminar colonia: ID no válido");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar colonia: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return result;
	}

	@Override
	public List<Colonia> getAll() {
		Conexion conn = new Conexion();
        Colonia colonia = null;
        List<Colonia> colonias = new ArrayList<>();

        try {
            String call = "{call sp_GetAllColonias()}";
            conn.comando = conn.cnx.prepareCall(call);

            ResultSet rs = conn.comando.executeQuery();

            while (rs.next()) {
                colonia = new Colonia(
                    rs.getInt("cveColonia"),
                    rs.getString("nombreColonia")
                ); 
                colonias.add(colonia);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener todas las colonias: ");
            e.printStackTrace();
        } finally {
            conn.closeConnection();
        }
        return colonias;

	}

}
