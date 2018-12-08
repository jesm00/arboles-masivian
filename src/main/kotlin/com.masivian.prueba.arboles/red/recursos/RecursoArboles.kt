package com.masivian.prueba.arboles.red.recursos

import com.fasterxml.jackson.databind.JsonMappingException
import com.masivian.prueba.arboles.dao.ErrorBD
import com.masivian.prueba.arboles.dao.RepositorioArboles
import com.masivian.prueba.arboles.red.ArbolBinarioConIdDTO
import com.masivian.prueba.arboles.red.ArbolBinarioDTO
import com.masivian.prueba.arboles.red.ErrorEnDAONoEsperado
import com.masivian.prueba.arboles.red.ExcepcionRed
import javax.ws.rs.Consumes
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
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
}

class RecursoArbolesConRepositorio(private val repositorioArboles: RepositorioArboles): RecursoArboles
{
    override fun crearArbol(arbolBinarioDTO: ArbolBinarioDTO): ArbolBinarioConIdDTO
    {
        val arbolGuardadoConId = try
        {
            repositorioArboles.crearArbol(arbolBinarioDTO.aArbolBinario())
        }
        catch (e: ErrorBD)
        {
            throw ErrorEnDAONoEsperado(e)
        }
        return ArbolBinarioConIdDTO(arbolGuardadoConId)
    }
}