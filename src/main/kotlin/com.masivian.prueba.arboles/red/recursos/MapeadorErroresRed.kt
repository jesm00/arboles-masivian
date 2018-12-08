package com.masivian.prueba.arboles.red.recursos

import com.masivian.prueba.arboles.red.ErrorDTO
import com.masivian.prueba.arboles.red.ExcepcionRed
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.ext.ExceptionMapper
import javax.ws.rs.ext.Provider

@Provider
internal class MapeadorErroresRed : ExceptionMapper<ExcepcionRed>
{
    override fun toResponse(exception: ExcepcionRed): Response
    {
        return Response
            .status(exception.codigoHttp)
            .type(MediaType.APPLICATION_JSON_TYPE)
            .entity(ErrorDTO(exception.message))
            .build()
    }
}