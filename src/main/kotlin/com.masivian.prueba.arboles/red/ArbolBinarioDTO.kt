package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.masivian.prueba.arboles.ArbolBinario

data class ArbolBinarioDTO @JsonCreator constructor(
    @JsonProperty(NOMBRE_PROPIEDAD_NODO)
    @get:JsonProperty(NOMBRE_PROPIEDAD_NODO)
    val nodo: Int?,
    @JsonProperty(NOMBRE_PROPIEDAD_IZQUIERDO)
    @get:JsonProperty(NOMBRE_PROPIEDAD_IZQUIERDO)
    val izquierdo: ArbolBinarioDTO?,
    @JsonProperty(NOMBRE_PROPIEDAD_DERECHO)
    @get:JsonProperty(NOMBRE_PROPIEDAD_DERECHO)
    val derecho: ArbolBinarioDTO?
)
{
    companion object
    {
        private const val NOMBRE_PROPIEDAD_NODO = "node"
        private const val NOMBRE_PROPIEDAD_IZQUIERDO = "left"
        private const val NOMBRE_PROPIEDAD_DERECHO = "right"
    }

    constructor(arbolBinario: ArbolBinario<Int>)
            :this(
        arbolBinario.nodo,
        if(arbolBinario.esVacio) null else ArbolBinarioDTO(arbolBinario.hijoIzquierdo!!),
        if(arbolBinario.esVacio) null else ArbolBinarioDTO(arbolBinario.hijoDerecho!!)
    )

    init
    {
        if(nodo == null && (izquierdo != null || derecho != null))
        {
            throw ArbolInvalido("Only the empty tree can have a null $NOMBRE_PROPIEDAD_NODO")
        }
    }

    fun aArbolBinario(): ArbolBinario<Int>
    {
        return if(nodo == null)
        {
            ArbolBinario.vacio()
        }
        else
        {
            ArbolBinario.nodo(
                nodo,
                izquierdo?.aArbolBinario() ?: ArbolBinario.vacio(),
                derecho?.aArbolBinario() ?: ArbolBinario.vacio()
            )
        }

    }
}