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

    @Nested
    @DisplayName("darAncestroComunMasCercano")
    inner class DarAncestroComunMasCercano
    {
        @Test
        fun con_arbol_vacio_retorna_null()
        {
            val arbol = ArbolBinario.vacio<Int>()
            Assertions.assertNull(arbol.darAncestroComunMasCercano(1, 2))
        }

        @Test
        fun retorna_la_raiz_cuando_primer_nodo_esta_por_la_izquierda_y_el_otro_por_la_derecha()
        {
            val nodoRaiz = 1
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoInicial,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.nodo(
                    nodoFinal,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )
            Assertions.assertEquals(nodoRaiz, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_la_raiz_cuando_primer_nodo_esta_por_la_derecha_y_el_otro_por_la_izquierda()
        {
            val nodoRaiz = 1
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoFinal,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.nodo(
                    nodoInicial,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )
            Assertions.assertEquals(nodoRaiz, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_null_cuando_primer_nodo_no_existe()
        {
            val nodoRaiz = 1
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoInicial,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.nodo(
                    nodoFinal,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )
            Assertions.assertNull(arbol.darAncestroComunMasCercano(4, nodoFinal))
        }

        @Test
        fun retorna_null_cuando_segundo_nodo_no_existe()
        {
            val nodoRaiz = 1
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoInicial,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.nodo(
                    nodoFinal,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )
            Assertions.assertNull(arbol.darAncestroComunMasCercano(nodoInicial, 4))
        }

        @Test
        fun retorna_el_nodo_mas_bajo_posible_cuando_hay_mas_de_un_ancestro_comun_por_izquierda()
        {
            val nodoRaiz = 1
            val nodoComun = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoComun,
                    ArbolBinario.nodo(
                        nodoInicial,
                        ArbolBinario.vacio(),
                        ArbolBinario.vacio()
                    ),
                    ArbolBinario.nodo(
                        nodoFinal,
                        ArbolBinario.vacio(),
                        ArbolBinario.vacio()
                    )
                ),
                ArbolBinario.vacio()
            )
            Assertions.assertEquals(nodoComun, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_el_nodo_mas_bajo_posible_cuando_hay_mas_de_un_ancestro_comun_por_derecha()
        {
            val nodoRaiz = 1
            val nodoComun = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.vacio(),
                ArbolBinario.nodo(
                    nodoComun,
                    ArbolBinario.nodo(
                        nodoInicial,
                        ArbolBinario.vacio(),
                        ArbolBinario.vacio()
                    ),
                    ArbolBinario.nodo(
                        nodoFinal,
                        ArbolBinario.vacio(),
                        ArbolBinario.vacio()
                    )
                )
            )
            Assertions.assertEquals(nodoComun, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_inicial_cuando_inicial_es_padre_de_final_por_izquierda()
        {
            val nodoRaiz = 1
            val nodoComun = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoComun,
                    ArbolBinario.nodo(
                        nodoInicial,
                        ArbolBinario.nodo(
                            nodoFinal,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        ),
                        ArbolBinario.vacio()
                    ),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.vacio()
            )
            Assertions.assertEquals(nodoInicial, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_inicial_cuando_inicial_es_ancestro_de_final_por_izquierda()
        {
            val nodoRaiz = 1
            val otroNodo = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoInicial,
                    ArbolBinario.nodo(
                        otroNodo,
                        ArbolBinario.nodo(
                            nodoFinal,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        ),
                        ArbolBinario.vacio()
                    ),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.vacio()
            )
            Assertions.assertEquals(nodoInicial, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_inicial_cuando_inicial_es_padre_de_final_por_derecha()
        {
            val nodoRaiz = 1
            val nodoComun = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.vacio(),
                ArbolBinario.nodo(
                    nodoComun,
                    ArbolBinario.vacio(),
                    ArbolBinario.nodo(
                        nodoInicial,
                        ArbolBinario.vacio(),
                        ArbolBinario.nodo(
                            nodoFinal,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        )
                    )
                )
            )
            Assertions.assertEquals(nodoInicial, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_inicial_cuando_inicial_es_ancestro_de_final_por_derecha()
        {
            val nodoRaiz = 1
            val otroNodo = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.vacio(),
                ArbolBinario.nodo(
                    nodoInicial,
                    ArbolBinario.vacio(),
                    ArbolBinario.nodo(
                        otroNodo,
                        ArbolBinario.vacio(),
                        ArbolBinario.nodo(
                            nodoFinal,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        )
                    )
                )
            )
            Assertions.assertEquals(nodoInicial, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_final_cuando_final_es_padre_de_inicial_por_izquierda()
        {
            val nodoRaiz = 1
            val nodoComun = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoComun,
                    ArbolBinario.nodo(
                        nodoFinal,
                        ArbolBinario.nodo(
                            nodoInicial,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        ),
                        ArbolBinario.vacio()
                    ),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.vacio()
            )
            Assertions.assertEquals(nodoFinal, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_final_cuando_final_es_ancestro_de_inicial_por_izquierda()
        {
            val nodoRaiz = 1
            val otroNodo = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.nodo(
                    nodoFinal,
                    ArbolBinario.nodo(
                        otroNodo,
                        ArbolBinario.nodo(
                            nodoInicial,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        ),
                        ArbolBinario.vacio()
                    ),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.vacio()
            )
            Assertions.assertEquals(nodoFinal, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_final_cuando_final_es_padre_de_inicial_por_derecha()
        {
            val nodoRaiz = 1
            val nodoComun = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.vacio(),
                ArbolBinario.nodo(
                    nodoComun,
                    ArbolBinario.vacio(),
                    ArbolBinario.nodo(
                        nodoFinal,
                        ArbolBinario.vacio(),
                        ArbolBinario.nodo(
                            nodoInicial,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        )
                    )
                )
            )
            Assertions.assertEquals(nodoFinal, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }

        @Test
        fun retorna_final_cuando_final_es_ancestro_de_inicial_por_derecha()
        {
            val nodoRaiz = 1
            val otroNodo = 4
            val nodoInicial = 2
            val nodoFinal = 3
            val arbol = ArbolBinario.nodo(
                nodoRaiz,
                ArbolBinario.vacio(),
                ArbolBinario.nodo(
                    nodoFinal,
                    ArbolBinario.vacio(),
                    ArbolBinario.nodo(
                        otroNodo,
                        ArbolBinario.vacio(),
                        ArbolBinario.nodo(
                            nodoInicial,
                            ArbolBinario.vacio(),
                            ArbolBinario.vacio()
                        )
                    )
                )
            )
            Assertions.assertEquals(nodoFinal, arbol.darAncestroComunMasCercano(nodoInicial, nodoFinal))
        }
    }
}