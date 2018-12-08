package com.masivian.prueba.arboles.red.recursos

import com.fasterxml.jackson.databind.JsonMappingException
import com.masivian.prueba.arboles.dao.ErrorBD
import com.masivian.prueba.arboles.dao.RepositorioArboles
import com.masivian.prueba.arboles.red.*
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.Produces
import javax.ws.rs.QueryParam
import javax.ws.rs.core.MediaType

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface RecursoAncestroComunMasCercano
{
    companion object
    {
        const val RUTA = "lowest-common-ancestor"
        const val NOMBRE_PARAMETRO_NODO_INICIAL = "initial-node"
        const val NOMBRE_PARAMETRO_NODO_FINAL = "final-node"
    }

    val idArbol: Long

    @GET
    @Throws(ExcepcionRed::class, JsonMappingException::class)
    fun darAncestroComunMasCercano(
        @QueryParam(NOMBRE_PARAMETRO_NODO_INICIAL)
        nodoInicial: Int?,
        @QueryParam(NOMBRE_PARAMETRO_NODO_FINAL)
        nodoFinal: Int?
    ): AncestroComunMasCercanoDTO
}

class RecursoAncestroComunMasCercanoConRepositorio(
    override val idArbol: Long,
    private val repositorioArboles: RepositorioArboles
): RecursoAncestroComunMasCercano
{
    override fun darAncestroComunMasCercano(nodoInicial: Int?, nodoFinal: Int?): AncestroComunMasCercanoDTO
    {
        if(nodoInicial == null)
        {
            throw ParametroInvalido("The parameter ${RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_INICIAL} is mandatory")
        }
        if(nodoFinal == null)
        {
            throw ParametroInvalido("The parameter ${RecursoAncestroComunMasCercano.NOMBRE_PARAMETRO_NODO_FINAL} is mandatory")
        }
        val arbol = try
        {
            repositorioArboles.buscarArbolBinarioPorId(idArbol) ?: throw EntidadInexistente("Tree", idArbol.toString())
        }
        catch (e: ErrorBD)
        {
            throw ErrorEnDAONoEsperado(e)
        }

        return AncestroComunMasCercanoDTO(arbol.arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))

    }
}