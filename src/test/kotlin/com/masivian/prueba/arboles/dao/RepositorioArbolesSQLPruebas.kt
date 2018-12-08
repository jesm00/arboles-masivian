package com.masivian.prueba.arboles.dao

import com.masivian.prueba.arboles.ArbolBinario
import org.junit.jupiter.api.*

@DisplayName("RepositorioArbolesSQL")
class RepositorioArbolesSQLPruebas
{
    private val proveedorConexionesORMLite: ProveedorConexionesORMLite = ProveedorConexionesORMLiteH2EnMemoria("bdPruebas")
    private val repositorio: RepositorioArboles = RepositorioArbolesSQL(proveedorConexionesORMLite)

    @AfterEach
    fun limpiarBD()
    {
        repositorio.liberarRecursos()
    }

    @Nested
    inner class AlCrear
    {
        @Test
        fun arbol_vacio_retorna_arbol_correcto_con_id_no_nulo()
        {
            val arbolACrear = ArbolBinario.vacio<Int>()
            val arbolConIdCreado = repositorio.crearArbolBinario(arbolACrear)
            Assertions.assertNotNull(arbolConIdCreado.id)
            Assertions.assertTrue(arbolConIdCreado.arbol.esVacio)
        }

        @Test
        fun arbol_no_vacio_retorna_arbol_correcto_con_id_no_nulo()
        {
            val nodo = 2
            val nodoIzquierdo = 3
            val nodoDerecho = 4

            val arbol = ArbolBinario.nodo(
                nodo,
                ArbolBinario.nodo(
                    nodoIzquierdo,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.nodo(
                    nodoDerecho,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )

            val arbolConIdCreado = repositorio.crearArbolBinario(arbol)
            Assertions.assertNotNull(arbolConIdCreado.id)
            Assertions.assertEquals(arbol, arbolConIdCreado.arbol)
        }

        @Test
        fun al_crear_dos_arboles_retorna_ids_diferentes()
        {
            val arbolACrear = ArbolBinario.vacio<Int>()
            val arbolConIdCreado1 = repositorio.crearArbolBinario(arbolACrear)
            val arbolConIdCreado2 = repositorio.crearArbolBinario(arbolACrear)
            Assertions.assertNotEquals(arbolConIdCreado1.id, arbolConIdCreado2.id)
        }
    }

    @Nested
    inner class AlBuscar
    {
        @Test
        fun arbol_en_bd_vacia_retorna_null()
        {
            Assertions.assertNull(repositorio.buscarArbolBinarioPorId(1))
        }

        @Test
        fun arbol_inexistente_retorna_null()
        {
            val arbolACrear = ArbolBinario.vacio<Int>()
            val arbolConIdCreado = repositorio.crearArbolBinario(arbolACrear)
            Assertions.assertNull(repositorio.buscarArbolBinarioPorId(arbolConIdCreado.id!! - 1))
        }

        @Test
        fun arbol_vacio_retorna_arbol_correcto_con_id_correcto()
        {
            val arbolACrear = ArbolBinario.vacio<Int>()
            val arbolConIdCreado = repositorio.crearArbolBinario(arbolACrear)
            val arbolBuscado = repositorio.buscarArbolBinarioPorId(arbolConIdCreado.id!!)

            Assertions.assertEquals(arbolConIdCreado, arbolBuscado)
        }

        @Test
        fun arbol_no_vacio_retorna_arbol_correcto_con_id_correcto()
        {
            val nodo = 2
            val nodoIzquierdo = 3
            val nodoDerecho = 4

            val arbol = ArbolBinario.nodo(
                nodo,
                ArbolBinario.nodo(
                    nodoIzquierdo,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                ),
                ArbolBinario.nodo(
                    nodoDerecho,
                    ArbolBinario.vacio(),
                    ArbolBinario.vacio()
                )
            )

            val arbolConIdCreado = repositorio.crearArbolBinario(arbol)
            val arbolBuscado = repositorio.buscarArbolBinarioPorId(arbolConIdCreado.id!!)

            Assertions.assertEquals(arbolConIdCreado, arbolBuscado)
        }
    }
}