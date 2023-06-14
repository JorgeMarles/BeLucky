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
public class UsuarioDao implements IUsuario {

    final static String SQL_CONSULTAR = "SELECT * FROM usuario";
    final static String SQL_INSERTAR = "INSERT INTO usuario (correo, nombre, telefono, registro) VALUES (?,?,?,?)";
    final static String SQL_BORRAR = "DELETE FROM usuario WHERE correo = ?";
    final static String SQL_CONSULTARID = "SELECT * FROM usuario WHERE correo = ?";
    final static String SQL_ACTUALIZAR = "UPDATE usuario SET nombre = ?, telefono = ? WHERE correo = ?";

    @Override
    public int insertar(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_INSERTAR, PreparedStatement.RETURN_GENERATED_KEYS);
            sentencia.setString(2, usuario.getNombre());
            sentencia.setString(1, usuario.getCorreo());

            sentencia.setString(3, usuario.getTelefono());
            sentencia.setTimestamp(4, Timestamp.valueOf(usuario.getRegistro()));

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
    public List<Usuario> consultar() {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Usuario> usuarios = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_CONSULTAR);

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                //(id, nombre, correo, usuario, password, telefono, foto, registro)
                String nombre = resultado.getString("nombre");
                String correo = resultado.getString("correo");
                String telefono = resultado.getString("telefono");
                LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

                Usuario usuario = new Usuario(nombre, correo, telefono, registro);
                usuarios.add(usuario);
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
        return usuarios;
    }

    @Override
    public Usuario consultarId(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        Usuario usuarioReturn = null;

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_CONSULTARID, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.TYPE_FORWARD_ONLY);
            sentencia.setString(1, usuario.getCorreo());

            resultado = sentencia.executeQuery();
            resultado.absolute(1);
            String nombre = resultado.getString("nombre");
            String correo = resultado.getString("correo");
            String telefono = resultado.getString("telefono");
            LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

            usuarioReturn = new Usuario(nombre, correo, telefono, registro);

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
        return usuarioReturn;
    }

    @Override
    public int borrar(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_BORRAR);
            sentencia.setString(1, usuario.getCorreo());
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
    public int actualizar(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_ACTUALIZAR);
            sentencia.setString(3, usuario.getCorreo());
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getTelefono());
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

    
}
