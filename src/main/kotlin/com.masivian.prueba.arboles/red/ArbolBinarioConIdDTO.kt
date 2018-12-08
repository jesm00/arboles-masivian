package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.masivian.prueba.arboles.ArbolBinarioConId

data class ArbolBinarioConIdDTO @JsonCreator constructor(
    @JsonProperty(NOMBRE_PROPIEDAD_ID)
    @get:JsonProperty(NOMBRE_PROPIEDAD_ID)
    val id: Long?,
    @JsonProperty(NOMBRE_PROPIEDAD_ARBOL, required = true)
    @get:JsonProperty(NOMBRE_PROPIEDAD_ARBOL, required = true)
    val arbol: ArbolBinarioDTO
)
{
    companion object
    {
        private const val NOMBRE_PROPIEDAD_ID = "id"
        private const val NOMBRE_PROPIEDAD_ARBOL = "tree"
    }

    constructor(arbolConId: ArbolBinarioConId<Int>)
            :this(
        arbolConId.id,
        ArbolBinarioDTO(arbolConId.arbol)
    )

    fun aArbolBinarioConId(): ArbolBinarioConId<Int>
    {
        return ArbolBinarioConId(id, arbol.aArbolBinario())
    }
}