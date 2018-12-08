package com.masivian.prueba.arboles

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("ArbolBinario")
class ArbolBinarioPruebas
{
    @Nested
    @DisplayName("Usando Constructor vacio")
    inner class UsandoConstructorVacio
    {
        @Test
        fun retorna_true_en_esVacio()
        {
            val arbol = ArbolBinario.vacio<Int>()
            Assertions.assertTrue(arbol.esVacio)
        }

        @Test
        fun asigna_nodo_nulo()
        {
            val arbol = ArbolBinario.vacio<Int>()
            Assertions.assertNull(arbol.nodo)
        }

        @Test
        fun asigna_hijo_izquierdo_nulo()
        {
            val arbol = ArbolBinario.vacio<Int>()
            Assertions.assertNull(arbol.hijoIzquierdo)
        }

        @Test
        fun asigna_hijo_derecho_nulo()
        {
            val arbol = ArbolBinario.vacio<Int>()
            Assertions.assertNull(arbol.hijoIzquierdo)
        }
    }


    @Nested
    @DisplayName("Usando Constructor nodo")
    inner class UsandoConstructorNodo
    {
        @Test
        fun retorna_false_en_esVacio()
        {
            val valorNodo = 123
            val arbol = ArbolBinario.nodo(
                valorNodo,
                ArbolBinario.vacio(),
                ArbolBinario.vacio()
            )

            Assertions.assertFalse(arbol.esVacio)
        }

        @Test
        fun asigna_nodo_correcto()
        {
            val valorNodo = 123
            val arbol = ArbolBinario.nodo(
                valorNodo,
                ArbolBinario.vacio(),
                ArbolBinario.vacio()
            )

            Assertions.assertEquals(valorNodo, arbol.nodo)
        }

        @Test
        fun asigna_hijo_izquierdo_correcto_cuando_hijo_es_vacio()
        {
            val valorNodo = 123
            val arbol = ArbolBinario.nodo(
                valorNodo,
                ArbolBinario.vacio(),
                ArbolBinario.vacio()
            )

            Assertions.assertNotNull(arbol.hijoIzquierdo)
            Assertions.assertTrue(arbol.hijoIzquierdo!!.esVacio)
        }

        @Test
        fun asigna_hijo_izquierdo_correcto_cuando_hijo_no_es_vacio()
        {
            val valorNodo = 123
            val valorNodoHijo = 456
            val arbol = ArbolBinario.nodo(
                valorNodo,
                ArbolBinario.nodo(
                    valorNodoHijo,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.vacio()
            )

            Assertions.assertNotNull(arbol.hijoIzquierdo)
            Assertions.assertFalse(arbol.hijoIzquierdo!!.esVacio)
            Assertions.assertEquals(valorNodoHijo, arbol.hijoIzquierdo!!.nodo)
            Assertions.assertTrue(arbol.hijoIzquierdo!!.hijoIzquierdo!!.esVacio)
            Assertions.assertTrue(arbol.hijoIzquierdo!!.hijoDerecho!!.esVacio)
        }

        @Test
        fun asigna_hijo_derecho_correcto_cuando_hijo_es_vacio()
        {
            val valorNodo = 123
            val arbol = ArbolBinario.nodo(
                valorNodo,
                ArbolBinario.vacio(),
                ArbolBinario.vacio()
            )

            Assertions.assertNotNull(arbol.hijoDerecho)
            Assertions.assertTrue(arbol.hijoDerecho!!.esVacio)
        }

        @Test
        fun asigna_hijo_derecho_correcto_cuando_hijo_no_es_vacio()
        {
            val valorNodo = 123
            val valorNodoHijo = 456
            val arbol = ArbolBinario.nodo(
                valorNodo,
                ArbolBinario.vacio(),
                ArbolBinario.nodo(
                    valorNodoHijo,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )

            Assertions.assertNotNull(arbol.hijoDerecho)
            Assertions.assertFalse(arbol.hijoDerecho!!.esVacio)
            Assertions.assertEquals(valorNodoHijo, arbol.hijoDerecho!!.nodo)
            Assertions.assertTrue(arbol.hijoDerecho!!.hijoIzquierdo!!.esVacio)
            Assertions.assertTrue(arbol.hijoDerecho!!.hijoDerecho!!.esVacio)
        }
    }
}