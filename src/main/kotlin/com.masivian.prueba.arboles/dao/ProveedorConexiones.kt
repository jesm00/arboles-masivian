package com.masivian.prueba.arboles.dao

import com.j256.ormlite.jdbc.DataSourceConnectionSource
import com.j256.ormlite.support.ConnectionSource
import com.zaxxer.hikari.HikariDataSource
import java.sql.SQLException

interface ProveedorConexionesORMLite
{
    val fuenteConexiones: ConnectionSource
    fun liberarRecursos()
}

class ProveedorConexionesORMLiteH2EnMemoria(nombreBD: String) : ProveedorConexionesORMLite
{
    private val poolConexiones: HikariDataSource by lazy {
        val dataSource = HikariDataSource()
        dataSource.jdbcUrl = "jdbc:h2:mem:$nombreBD"
        dataSource.driverClassName = "org.h2.Driver"
        dataSource.maximumPoolSize = 10
        dataSource
    }

    override val fuenteConexiones: ConnectionSource by lazy {
        DataSourceConnectionSource(poolConexiones, poolConexiones.jdbcUrl)
    }

    override fun liberarRecursos()
    {
        try
        {
            poolConexiones.close()
        }
        catch (e: SQLException)
        {
            // Se ignoran los errores, estamos saliendo
        }
    }
}