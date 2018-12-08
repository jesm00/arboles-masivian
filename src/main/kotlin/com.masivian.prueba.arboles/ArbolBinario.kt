package com.masivian.prueba.arboles

class ArbolBinario<T: Any> private constructor(val nodo: T?, val hijoIzquierdo: ArbolBinario<T>?, val hijoDerecho: ArbolBinario<T>?)
{
    companion object
    {
        fun <T: Any> vacio(): ArbolBinario<T>
        {
            return ArbolBinario(null, null, null)
        }

        fun <T: Any> nodo(nodo: T, hijoIzquierdo: ArbolBinario<T>, hijoDerecho: ArbolBinario<T>): ArbolBinario<T>
        {
            return ArbolBinario(nodo, hijoIzquierdo, hijoDerecho)
        }
    }

    val esVacio = nodo == null

    fun darAncestroComunMasCercano(nodoInicial: T, nodoFinal: T): T?
    {
        return darInformacionAncestroComunMasCercano(nodoInicial, nodoFinal).ancestroEncontrado
    }

    private fun darInformacionAncestroComunMasCercano(nodoInicial: T, nodoFinal: T): InformacionAncestroComunMasCercano<T>
    {
        // En un árbol vacio no existe ancestro común
        if(esVacio)
        {
            return InformacionAncestroComunMasCercano(null, false, false)
        }

        val informacionIzquierda = hijoIzquierdo!!
            .darInformacionAncestroComunMasCercano(nodoInicial, nodoFinal)

        // Encontro un mejor ancestro por la izquierda, terminar busqueda
        if(informacionIzquierda.ancestroEncontrado != null)
        {
            return informacionIzquierda
        }

        // Este es final y es ancestro de inicial. LCA es este nodo
        if(informacionIzquierda.encontroNodoInicial && nodo == nodoFinal)
        {
            return InformacionAncestroComunMasCercano(nodo, true, true)
        }

        // Este es inicial y es ancestro de final. LCA es este nodo
        if(informacionIzquierda.encontroNodoFinal && nodo == nodoInicial)
        {
            return InformacionAncestroComunMasCercano(nodo, true, true)
        }

        val informacionDerecha = hijoDerecho!!
            .darInformacionAncestroComunMasCercano(nodoInicial, nodoFinal)

        // Encontro un ancestro por la derecha, terminar busqueda
        if(informacionDerecha.ancestroEncontrado != null)
        {
            return informacionDerecha
        }

        // Este es final y es ancestro de inicial. LCA es este nodo
        if(informacionDerecha.encontroNodoInicial && nodo == nodoFinal)
        {
            return InformacionAncestroComunMasCercano(nodo, true, true)
        }

        // Este es inicial y es ancestro de final. LCA es este nodo
        if(informacionDerecha.encontroNodoFinal && nodo == nodoInicial)
        {
            return InformacionAncestroComunMasCercano(nodo, true, true)
        }

        // Inicial es hijo por izquierda y final por derecha. Este es el LCA
        if(informacionIzquierda.encontroNodoInicial && informacionDerecha.encontroNodoFinal)
        {
            return InformacionAncestroComunMasCercano(nodo, true, true)
        }

        // Final es hijo por izquierda e inicial por derecha. Este es el LCA
        if(informacionIzquierda.encontroNodoFinal && informacionDerecha.encontroNodoInicial)
        {
            return InformacionAncestroComunMasCercano(nodo, true, true)
        }
        return InformacionAncestroComunMasCercano(
            null,
            informacionIzquierda.encontroNodoInicial || informacionDerecha.encontroNodoInicial || nodo == nodoInicial,
            informacionIzquierda.encontroNodoFinal || informacionDerecha.encontroNodoFinal || nodo == nodoFinal
        )
    }

    private data class InformacionAncestroComunMasCercano<T: Any>(
        val ancestroEncontrado: T?,
        val encontroNodoInicial: Boolean,
        val encontroNodoFinal: Boolean
    )

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ArbolBinario<*>

        if (nodo != other.nodo) return false
        if (hijoIzquierdo != other.hijoIzquierdo) return false
        if (hijoDerecho != other.hijoDerecho) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = nodo?.hashCode() ?: 0
        result = 31 * result + (hijoIzquierdo?.hashCode() ?: 0)
        result = 31 * result + (hijoDerecho?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String
    {
        return "ArbolBinario(nodo=$nodo, hijoIzquierdo=$hijoIzquierdo, hijoDerecho=$hijoDerecho)"
    }
}