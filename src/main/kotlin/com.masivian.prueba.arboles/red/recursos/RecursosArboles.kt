package com.masivian.prueba.arboles.red.recursos

import com.fasterxml.jackson.databind.JsonMappingException
import com.masivian.prueba.arboles.dao.ErrorBD
import com.masivian.prueba.arboles.dao.RepositorioArboles
import com.masivian.prueba.arboles.red.*
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path(RecursoArboles.RUTA)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface RecursoArboles
{
    companion object
    {
        const val RUTA = "trees"
    }

    @POST
    @Throws(ExcepcionRed::class, JsonMappingException::class)
    fun crearArbol(arbolBinarioDTO: ArbolBinarioDTO): ArbolBinarioConIdDTO

    @Path("{id}")
    fun darRecursoArbolEspecifico(@PathParam("id") id: Long): RecursoArbol
}

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
interface RecursoArbol
{
    val idArbol: Long

    @Path(RecursoAncestroComunMasCercano.RUTA)
    fun darRecursoAncestroComunMasCercano(): RecursoAncestroComunMasCercano
}

class RecursoArbolesConRepositorio(private val repositorioArboles: RepositorioArboles): RecursoArboles
{
    override fun crearArbol(arbolBinarioDTO: ArbolBinarioDTO): ArbolBinarioConIdDTO
    {
        val arbolGuardadoConId = try
        {
            repositorioArboles.crearArbolBinario(arbolBinarioDTO.aArbolBinario())
        }
        catch (e: ErrorBD)
        {
            throw ErrorEnDAONoEsperado(e)
        }
        return ArbolBinarioConIdDTO(arbolGuardadoConId)
    }

    override fun darRecursoArbolEspecifico(id: Long): RecursoArbol
    {
        return RecursoArbolConRepositorio(id, repositorioArboles)
    }
}

class RecursoArbolConRepositorio(
    override val idArbol: Long,
    private val repositorioArboles: RepositorioArboles
): RecursoArbol
{
    override fun darRecursoAncestroComunMasCercano(): RecursoAncestroComunMasCercano
    {
        return RecursoAncestroComunMasCercanoConRepositorio(idArbol, repositorioArboles)
    }

}