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
}