package com.masivian.prueba.arboles.red.recursos

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import com.masivian.prueba.arboles.dao.ProveedorConexionesORMLite
import com.masivian.prueba.arboles.dao.ProveedorConexionesORMLiteH2EnMemoria
import com.masivian.prueba.arboles.dao.RepositorioArboles
import com.masivian.prueba.arboles.dao.RepositorioArbolesSQL
import com.masivian.prueba.arboles.red.ConfiguracionJackson
import org.glassfish.jersey.server.ResourceConfig

object ConfiguracionJersey
{
    private const val NOMBRE_BD = "bdEnMemoria"
    private val proveedorConexiones: ProveedorConexionesORMLite = ProveedorConexionesORMLiteH2EnMemoria(NOMBRE_BD)
    private val repositorioArboles: RepositorioArboles = RepositorioArbolesSQL(proveedorConexiones)
    internal var recursoArboles: RecursoArboles = RecursoArbolesConRepositorio(repositorioArboles)

    fun darAplicacion(): ResourceConfig
    {
        return ResourceConfig()
            .register(recursoArboles)
            .register(JacksonJaxbJsonProvider().apply {
                setMapper(ConfiguracionJackson.mapper)
            })
            .register(MapeadorErroresRed::class.java)
            .register(MapeadorErroresJackson::class.java)
    }
}