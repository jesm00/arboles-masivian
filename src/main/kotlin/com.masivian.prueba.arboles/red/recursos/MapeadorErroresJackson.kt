package com.masivian.prueba.arboles.red.recursos

import com.fasterxml.jackson.databind.JsonMappingException
import com.masivian.prueba.arboles.red.ErrorDTO
import com.masivian.prueba.arboles.red.ExcepcionRed
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
internal class MapeadorErroresJackson : ExceptionMapper<JsonMappingException>
{
    override fun toResponse(exception: JsonMappingException): Response
    {
        return Response
            .status(Response.Status.BAD_REQUEST)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(ErrorDTO(exception.message))
            .build()
    }
}