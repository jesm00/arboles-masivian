package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class AncestroComunMasCercanoDTO @JsonCreator constructor(
    @JsonProperty(NOMBRE_PROPIEDAD_ANCESTRO_COMUN, required = true)
    @get:JsonProperty(NOMBRE_PROPIEDAD_ANCESTRO_COMUN, required = true)
    val ancestroComun: Int?
)
{
    companion object
    {
        private const val NOMBRE_PROPIEDAD_ANCESTRO_COMUN = "lowest-common-ancestor"
    }
}