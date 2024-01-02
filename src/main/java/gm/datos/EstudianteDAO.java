package gm.datos;

import gm.dominio.Estudiante;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static gm.conexion.Conexion.getConexion;

//DAO - Data Acces Object (patron de disenio)
public class EstudianteDAO {

    public List<Estudiante> listarEstudiantes(){
        List<Estudiante> estudiantes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante ORDER BY id_estudiante";
        try{
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                var estudiante = new Estudiante();
                estudiante.setIdEstudiante(rs.getInt("id_estudiante"));
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                estudiantes.add(estudiante);
            }
        } catch(Exception e){
            System.out.println("Ocurrio un error al seleccionar datos: " + e.getMessage());
        }
        finally {
            cerrarConexion(con);
        }
        return estudiantes;
    }
    //findById
    public boolean buscarEstudiantePorId(Estudiante estudiante){
        boolean encontrado = false;
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM estudiante WHERE id_estudiante = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            rs = ps.executeQuery();
            if(rs.next()){
                estudiante.setNombre(rs.getString("nombre"));
                estudiante.setApellido(rs.getString("apellido"));
                estudiante.setTelefono(rs.getString("telefono"));
                estudiante.setEmail(rs.getString("email"));
                encontrado =  true;
            }
        } catch(Exception e){
            System.out.println("Ocurrio un error al buscar estudiante: " + e.getMessage());
        }
        finally {
            cerrarConexion(con);
        }
        return encontrado;
    }

    public boolean agregarEstudiante(Estudiante estudiante){
        boolean agregado = false;
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO estudiante(nombre, apellido, telefono, email) " +
                " VALUES(?, ?, ?, ?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.execute();
            agregado = true;
        } catch (Exception e){
            System.out.println("Error al agregar estudiante: " + e.getMessage());
        }
        finally {
            cerrarConexion(con);
        }
        return agregado;
    }

    public boolean modificarEstudiante(Estudiante estudiante){
        boolean modificado = false;
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "UPDATE estudiante SET nombre=?, apellido=?, telefono=?, " +
                " email=? WHERE id_estudiante = ?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, estudiante.getNombre());
            ps.setString(2, estudiante.getApellido());
            ps.setString(3, estudiante.getTelefono());
            ps.setString(4, estudiante.getEmail());
            ps.setInt(5, estudiante.getIdEstudiante());
            ps.execute();
            modificado = true;
        } catch(Exception e){
            System.out.println("Error al modificar estudiante: " + e.getMessage());
        }
        finally {
            cerrarConexion(con);
        }
        return modificado;
    }

    public boolean eliminarEstudiante(Estudiante estudiante){
        boolean eliminado = false;
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM estudiante WHERE id_estudiante = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, estudiante.getIdEstudiante());
            ps.execute();
            eliminado = true;
        } catch (Exception e){
            System.out.println("Error al eliminar estudiante: " + e.getMessage());
        } finally {
            cerrarConexion(con);
        }
        return eliminado;
    }

    private static void cerrarConexion(Connection con){
        try{
            con.close();
        } catch (Exception e){
            System.out.println("Error al cerrar conexion: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        var estudianteDao = new EstudianteDAO();

        // Agregar estudiante
//    var nuevoEstudiante =
//            new Estudiante("Carlos", "Lara", "55117788", "carlos@mail.com");
//    var agregado = estudianteDao.agregarEstudiante(nuevoEstudiante);
//    if(agregado)
//      System.out.println("Estudiante agregado: " + nuevoEstudiante);
//    else
//      System.out.println("No se agregaro el estudiante: " + nuevoEstudiante);

        /*
        // Modificamos un estudiante existente (1)
        var estudianteModificar = new Estudiante(1, "Juan Carlos",
                "Juarez", "55443322", "juan@mail.com");
        var modificado = estudianteDao.modificarEstudiante(estudianteModificar);
        if(modificado)
            System.out.println("Estudiante modificado: " + estudianteModificar);
        else
            System.out.println("No se modifico estudiante: " + estudianteModificar);

         */

        // eliminar estudiante (3)
        var estudianteEliminar = new Estudiante(3);
        var eliminado = estudianteDao.eliminarEstudiante(estudianteEliminar);
        if (eliminado)
            System.out.println("Estudiante eliminado: " + estudianteEliminar);
        else
            System.out.println("no se elimino estudiante: " + estudianteEliminar);

        // Listar los estudiantes
        System.out.println("Listado Estudiantes: ");
        List<Estudiante> estudiantes = estudianteDao.listarEstudiantes();
        estudiantes.forEach(System.out::println);

        //Buscar por Id
//    var estudiante1 = new Estudiante(3);
//    System.out.println("Estudiante antes de la busqueda: " + estudiante1);
//    var encontrado = estudianteDao.buscarEstudiantePorId(estudiante1);
//    if(encontrado)
//      System.out.println("Estudiante encontrado: " + estudiante1);
//    else
//      System.out.println("No se encontro estudiante: "
//              + estudiante1.getIdEstudiante());
    }
}
