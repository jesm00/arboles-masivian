package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ErrorDTO @JsonCreator constructor(
    @JsonProperty(NOMBRE_PROPIEDAD_MENSAJE)
    @get:JsonProperty(NOMBRE_PROPIEDAD_MENSAJE)
    val mensaje: String?
)
{
    companion object
    {
        private const val NOMBRE_PROPIEDAD_MENSAJE = "message"
    }
}