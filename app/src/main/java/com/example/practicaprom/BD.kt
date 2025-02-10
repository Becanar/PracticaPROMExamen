package com.example.practicaprom

import android.content.Context
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.util.Properties

class BD (context: Context) {
    private val dbUrl:String
    private val dbUser:String
    private val dbPassword:String

    init {
        val properties = Properties()

        try {
            val inputStream = context.assets.open("config.properties")
            properties.load(inputStream)
            inputStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        dbUrl = properties.getProperty("db_url") ?: throw IllegalArgumentException("dburl no definido")
        dbUser = properties.getProperty("db_user") ?: throw IllegalArgumentException("dbuser no definido")
        dbPassword = properties.getProperty("db_password") ?: throw IllegalArgumentException("dbpassword no definido")
    }

    fun obtenerConexion(): Connection? {
        return try {
            DriverManager.getConnection(dbUrl, dbUser, dbPassword)
        } catch (e: SQLException) {
            null
        }
    }

    fun guardarAlumno(usuario: String, contrasena: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                // 1️⃣ Verificar si el usuario ya existe
                val checkQuery = "SELECT COUNT(*) FROM alumno WHERE usuario = ?;"
                val checkStmt: PreparedStatement = conexion.prepareStatement(checkQuery)
                checkStmt.setString(1, usuario)
                val resultSet: ResultSet = checkStmt.executeQuery()
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return false // El usuario ya existe
                }

                // 2️⃣ Insertar el nuevo alumno
                val query = "INSERT INTO alumno (usuario, contrasena, puntos) VALUES(?, ?, 0);"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setString(2, contrasena)
                statement.executeUpdate()
                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }

    fun probarConexion(): Boolean {
        val conexion = obtenerConexion()
        return if (conexion != null) {
            println("✅ Conexión exitosa a la base de datos")
            conexion.close()
            true
        } else {
            val error = try {
                DriverManager.getConnection(dbUrl, dbUser, dbPassword)
                null  // Si conecta bien, no hay error
            } catch (e: SQLException) {
                e.message  // Captura el mensaje de error exacto
            }

            println("❌ No se pudo conectar a la base de datos. Error: $error")
            false
        }
    }


    fun guardarGrupo(codigo: Int): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query =
                    "INSERT INTO grupo (codigo) VALUES(?);"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setInt(1, codigo)
                statement.executeUpdate()
                return true
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }

    fun conseguirAlumnosSinGrupo(): List<String> {
        val alumnosSinGrupo = mutableListOf<String>()
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "SELECT usuario FROM alumno WHERE grupo IS NULL;"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                val resultSet: ResultSet = statement.executeQuery()

                while (resultSet.next()) {
                    alumnosSinGrupo.add(resultSet.getString("usuario"))
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return alumnosSinGrupo
    }

    fun conseguirTodosGrupos(): List<Int> {
        val grupos = mutableListOf<Int>()
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "SELECT codigo FROM grupo;"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                val resultSet: ResultSet = statement.executeQuery()

                while (resultSet.next()) {
                    grupos.add(resultSet.getInt("codigo"))
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return grupos
    }

    fun asignarGrupo(usuario: String, grupo: Int): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "UPDATE alumno SET grupo = ? WHERE usuario = ?;"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setInt(1, grupo)
                statement.setString(2, usuario)
                val filasAfectadas = statement.executeUpdate()
                return filasAfectadas > 0
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }

    fun conseguirSumaPuntosPorGrupo(): Map<Int, Int> {
        val sumaPuntosPorGrupo = mutableMapOf<Int, Int>()
        val conexion = obtenerConexion()

        if (conexion != null) {
            try {
                val query = "SELECT grupo, SUM(puntos) as total_puntos FROM alumno WHERE grupo IS NOT NULL GROUP BY grupo;"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                val resultSet: ResultSet = statement.executeQuery()

                while (resultSet.next()) {
                    val grupo = resultSet.getInt("grupo")
                    val totalPuntos = resultSet.getInt("total_puntos")
                    sumaPuntosPorGrupo[grupo] = totalPuntos
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }

        return sumaPuntosPorGrupo
    }

    fun existeUsuario(usuario: String, contrasena: String): Boolean {
        val conexion = obtenerConexion()

        if (conexion != null) {
            try {
                val query = "SELECT COUNT(*) FROM alumno WHERE usuario = ? AND contrasena = ?;"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)
                statement.setString(2, contrasena)

                val resultSet: ResultSet = statement.executeQuery()
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return false
    }

    fun sumarPuntosUsuario(usuario: String): Boolean {
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                val query = "UPDATE alumno SET puntos = puntos + 10 WHERE usuario = ?;"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)

                val filasAfectadas = statement.executeUpdate()
                return filasAfectadas > 0
            } catch (e: SQLException) {
                e.printStackTrace()
                return false
            } finally {
                conexion.close()
            }
        }
        return false
    }


    fun conseguirPuntosDelGrupo(usuario: String): Map<String, Int> {
        val puntosPorUsuario = mutableMapOf<String, Int>()
        val conexion = obtenerConexion()
        if (conexion != null) {
            try {
                // Primero obtenemos el grupo del usuario
                val grupoQuery = "SELECT grupo FROM alumno WHERE usuario = ?;"
                val grupoStatement: PreparedStatement = conexion.prepareStatement(grupoQuery)
                grupoStatement.setString(1, usuario)
                val grupoResultSet: ResultSet = grupoStatement.executeQuery()
                if (grupoResultSet.next()) {
                    val grupo = grupoResultSet.getInt("grupo")
                    // Si el usuario tiene un grupo asignado, obtenemos los puntos de todos en su grupo
                    val query = "SELECT usuario, puntos FROM alumno WHERE grupo = ?;"
                    val statement: PreparedStatement = conexion.prepareStatement(query)
                    statement.setInt(1, grupo)
                    val resultSet: ResultSet = statement.executeQuery()
                    while (resultSet.next()) {
                        puntosPorUsuario[resultSet.getString("usuario")] = resultSet.getInt("puntos")
                    }
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return puntosPorUsuario
    }

    fun obtenerGrupoDeUsuario(usuario: String): Int? {
        val conexion = obtenerConexion()

        if (conexion != null) {
            try {
                val query = "SELECT grupo FROM alumno WHERE usuario = ?;"
                val statement: PreparedStatement = conexion.prepareStatement(query)
                statement.setString(1, usuario)

                val resultSet: ResultSet = statement.executeQuery()
                if (resultSet.next()) {
                    return resultSet.getInt("grupo")
                }
            } catch (e: SQLException) {
                e.printStackTrace()
            } finally {
                conexion.close()
            }
        }
        return null
    }



}