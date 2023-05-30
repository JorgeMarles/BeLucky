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
    final static String SQL_INSERTAR = "INSERT INTO usuario (id, nombre, correo, usuario, password, telefono, foto, registro) VALUES (NULL,?,?,?,?,?,?,?)";
    final static String SQL_BORRAR = "DELETE FROM usuario WHERE id = ?";
    final static String SQL_CONSULTARID = "SELECT * FROM usuario WHERE id = ?";
    final static String SQL_ACTUALIZAR = "UPDATE usuario SET id = ?, nombre = ?, correo = ?, usuario = ?, telefono = ?, foto = ?, registro = ? WHERE id = ?";
    final static String SQL_GET_RIFAS_CREADAS = "SELECT * FROM usuario u, rifa r WHERE r.id_usuario = u.id AND u.id = ?";
    final static String SQL_GET_RIFAS_INSCRITAS = "SELECT * FROM puesto p, usuario u, rifa r WHERE p.id_usuario = u.id AND u.id = ? AND p.id_rifa = r.id";

    @Override
    public int insertar(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        int resultado = 0;
        try {
            connection = BaseDeDatos.getConnection();
            sentencia = connection.prepareStatement(SQL_INSERTAR);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getCorreo());
            sentencia.setString(3   , usuario.getUsuario());
            String pw = usuario.getPassword();
            sentencia.setString(4, pw);
            sentencia.setString(5, usuario.getTelefono());
            sentencia.setString(6, usuario.getFoto());
            sentencia.setTimestamp(7, Timestamp.valueOf(usuario.getRegistro()));

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
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String correo = resultado.getString("correo");
                String user = resultado.getString("usuario");
                String foto = resultado.getString("foto");
                String telefono = resultado.getString("telefono");
                LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

                Usuario usuario = new Usuario(id, nombre, correo, user, telefono, foto, registro);
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
            sentencia.setInt(1, usuario.getId());

            resultado = sentencia.executeQuery();
            resultado.absolute(1);
            int id = resultado.getInt("id");
            String nombre = resultado.getString("nombre");
            String correo = resultado.getString("correo");
            String user = resultado.getString("usuario");
            String foto = resultado.getString("foto");
            String telefono = resultado.getString("telefono");
            LocalDateTime registro = resultado.getTimestamp("registro").toLocalDateTime();

            usuarioReturn = new Usuario(id, nombre, correo, user, telefono, foto, registro);

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
            sentencia.setInt(1, usuario.getId());
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
            sentencia.setInt(6, usuario.getId());
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getCorreo());
            sentencia.setString(3, usuario.getUsuario());
            sentencia.setString(4, usuario.getTelefono());
            sentencia.setString(5, usuario.getFoto());
            sentencia.setTimestamp(6, Timestamp.valueOf(usuario.getRegistro()));
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
            sentencia.setInt(1, usuario.getId());

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                /*
            id nombre correo usuario telefono foto registro
                 */
                int idUser = resultado.getInt("u.id");
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String userUser = resultado.getString("u.usuario");
                String telfUser = resultado.getString("u.telefono");
                String fotoUser = resultado.getString("u.foto");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(idUser, nomUser, emailUser, userUser, telfUser, fotoUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto id_usuario
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

    @Override
    public List<Puesto> getPuestosInscritos(Usuario usuario) {
        Connection connection = null;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Puesto> puestos = new ArrayList<>();

        try {
            connection = BaseDeDatos.getConnection();

            sentencia = connection.prepareStatement(SQL_GET_RIFAS_INSCRITAS);
            sentencia.setInt(1, usuario.getId());

            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                /*
                user : id nombre correo usuario password telefono foto registro
                rifa : id nombre descripcion premio inicio fin puestos valor_puesto id_usuario
                puesto : id id_usuario id_rifa num_puesto
                 */

                int idUser = resultado.getInt("u.id");
                String nomUser = resultado.getString("u.nombre");
                String emailUser = resultado.getString("u.correo");
                String userUser = resultado.getString("u.usuario");
                String telfUser = resultado.getString("u.telefono");
                String fotoUser = resultado.getString("u.foto");
                LocalDateTime regUser = resultado.getTimestamp("u.registro").toLocalDateTime();

                Usuario user = new Usuario(idUser, nomUser, emailUser, userUser, telfUser, fotoUser, regUser);

                //            id nombre descripcion premio inicio fin puestos valor_puesto id_usuario
                int idRifa = resultado.getInt("r.id");
                String nomRifa = resultado.getString("r.nombre");
                String descRifa = resultado.getString("r.descripcion");
                String premioRifa = resultado.getString("r.premio");
                LocalDateTime inicioRifa = resultado.getTimestamp("r.inicio").toLocalDateTime();
                LocalDateTime finRifa = resultado.getTimestamp("r.fin").toLocalDateTime();
                int numPuestos = resultado.getInt("r.puestos");
                int valorPuesto = resultado.getInt("r.valor_puesto");

                Rifa rifa = new Rifa(idRifa, nomRifa, descRifa, premioRifa, inicioRifa, finRifa, numPuestos, valorPuesto, user);

                int idPuesto = resultado.getInt("p.id");
                int puesto = resultado.getInt("p.num_puesto");

                Puesto p = new Puesto(idPuesto, user, rifa, puesto);

                puestos.add(p);

                //System.out.println(numero);
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
        return puestos;
    }

}
