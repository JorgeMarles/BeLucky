/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model.Dao;

import Model.Entity.Puesto;
import Model.Entity.Rifa;
import Model.Entity.Usuario;
import Red.BaseDeDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge Marles
 */
public class RifaDao implements IRifa {

    final static String SQL_INSERTAR = "INSERT INTO rifa (id, nombre, descripcion, premio, inicio, fin, puestos, valor_puesto, correo_usuario) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?)";
    final static String SQL_CONSULTAR = "SELECT * FROM rifa r, usuario u WHERE r.correo_usuario = u.correo";
    final static String SQL_BORRAR = "DELETE FROM rifa WHERE id = ?";
    final static String SQL_CONSULTARID = "SELECT * FROM rifa r, usuario u WHERE r.id = ? AND r.correo_usuario = u.correo";
    final static String SQL_ACTUALIZAR = "UPDATE rifa SET nombre = ?, descripcion = ?, premio = ?, inicio = ?, fin = ?, puestos = ?, valor_puesto = ? WHERE rifa.id = ?";
    final static String SQL_GET_RIFAS_CREADAS = "SELECT * FROM usuario u, rifa r WHERE r.correo_usuario = u.correo AND u.correo = ?";

    @Override
    public int insertar(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_INSERTAR, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(1, rifa.getNombre());
            sentencia.setString(2, rifa.getDescripcion());
            sentencia.setString(3, rifa.getPremio());
            sentencia.setTimestamp(4, Timestamp.valueOf(rifa.getInicio()));
            sentencia.setTimestamp(5, Timestamp.valueOf(rifa.getFin()));
            sentencia.setInt(6, rifa.getPuestos());
            sentencia.setInt(7, rifa.getValorPuesto());
            sentencia.setString(8, rifa.getUsuario().getCorreo());
            resultado = sentencia.executeUpdate();

            ResultSet rs = sentencia.getGeneratedKeys();
            if (rs.next()) {
                resultado = rs.getInt(1);
            }

            rifa.setId(resultado);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public List<Rifa> consultar() {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Rifa> rifas = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_CONSULTAR);

            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                //id nombre descripcion premio inicio fin puestos valor_puesto correo_usuario
                int id = resultado.getInt("r.id");
                String nombre = resultado.getString("r.nombre");
                String descripcion = resultado.getString("descripcion");
                String premio = resultado.getString("premio");
                LocalDateTime inicio = resultado.getTimestamp("inicio").toLocalDateTime();
                LocalDateTime fin = resultado.getTimestamp("fin").toLocalDateTime();
                int puestos = resultado.getInt("puestos");
                int valorPuesto = resultado.getInt("valor_puesto");

                //id nombre correo usuario telefono foto registro
                String nomUsuario = resultado.getString("u.nombre");
                String correo = resultado.getString("u.correo");
                String telefono = resultado.getString("telefono");
                LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

                Usuario u = new Usuario(nomUsuario, correo, telefono, registro);

                Rifa r = new Rifa(id, nombre, descripcion, premio, inicio, fin, puestos, valorPuesto, u);

                rifas.add(r);

            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BaseDeDatos.close(resultado);
                BaseDeDatos.close(sentencia);
                BaseDeDatos.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rifas;
    }

    @Override
    public Rifa consultarId(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Rifa rifaReturn = null;

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_CONSULTARID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
            sentencia.setInt(1, rifa.getId());
            resultado = sentencia.executeQuery();
            resultado.absolute(1);

            //id nombre descripcion premio inicio fin puestos valor_puesto correo_usuario
            int id = resultado.getInt("r.id");
            String nombre = resultado.getString("r.nombre");
            String descripcion = resultado.getString("descripcion");
            String premio = resultado.getString("premio");
            LocalDateTime inicio = resultado.getTimestamp("inicio").toLocalDateTime();
            LocalDateTime fin = resultado.getTimestamp("fin").toLocalDateTime();
            int puestos = resultado.getInt("puestos");
            int valorPuesto = resultado.getInt("valor_puesto");

            //id nombre correo usuario telefono foto registro
            String nomUsuario = resultado.getString("u.nombre");
            String correo = resultado.getString("u.correo");
            String telefono = resultado.getString("telefono");
            LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

            Usuario u = new Usuario(nomUsuario, correo, telefono, registro);

            rifaReturn = new Rifa(id, nombre, descripcion, premio, inicio, fin, puestos, valorPuesto, u);

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BaseDeDatos.close(resultado);
                BaseDeDatos.close(sentencia);
                BaseDeDatos.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rifaReturn;
    }

    @Override
    public int borrar(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_BORRAR);
            sentencia.setInt(1, rifa.getId());
            resultado = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public int actualizar(Rifa rifa) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_ACTUALIZAR);
            sentencia.setInt(8, rifa.getId());
            sentencia.setString(1, rifa.getNombre());
            sentencia.setString(2, rifa.getDescripcion());
            sentencia.setString(3, rifa.getPremio());
            sentencia.setTimestamp(4, Timestamp.valueOf(rifa.getInicio()));
            sentencia.setTimestamp(5, Timestamp.valueOf(rifa.getFin()));
            sentencia.setInt(6, rifa.getPuestos());
            sentencia.setInt(7, rifa.getValorPuesto());
            resultado = sentencia.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return resultado;
    }

    @Override
    public List<Rifa> getRifasCreadas(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Rifa> rifas = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_GET_RIFAS_CREADAS);
            sentencia.setString(1, usuario.getCorreo());

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                /*
            id nombre correo usuario telefono foto registro
                 */
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String telfUser = resultado.getString("u.telefono");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(nomUser, emailUser, telfUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto correo_usuario
                int idRifa = resultado.getInt("r.id");
                String nomRifa = resultado.getString("r.nombre");
                String descRifa = resultado.getString("r.descripcion");
                String premioRifa = resultado.getString("r.premio");
                LocalDateTime inicioRifa = resultado.getTimestamp("r.inicio").toLocalDateTime();
                LocalDateTime finRifa = resultado.getTimestamp("r.fin").toLocalDateTime();
                int numPuestos = resultado.getInt("r.puestos");
                int valorPuesto = resultado.getInt("r.valor_puesto");

                Rifa rifa = new Rifa(idRifa, nomRifa, descRifa, premioRifa, inicioRifa, finRifa, numPuestos, valorPuesto, user);
                //int numPuesto = resultado.getInt("p.num_puesto");

                rifas.add(rifa);
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                BaseDeDatos.close(resultado);
                BaseDeDatos.close(sentencia);
                BaseDeDatos.close(connection);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rifas;
    }
}
