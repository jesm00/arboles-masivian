package com.masivian.prueba.arboles.dao

import com.masivian.prueba.arboles.ArbolBinario
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@DisplayName("RepositorioArbolesSQL")
class RepositorioArbolesSQLPruebas
{
    private val proveedorConexionesORMLite: ProveedorConexionesORMLite = ProveedorConexionesORMLiteH2EnMemoria("bdPruebas")
    private val repositorio: RepositorioArboles = RepositorioArbolesSQL(proveedorConexionesORMLite)

    @Nested
    inner class AlCrear
    {
        @Test
        fun arbol_vacio_retorna_arbol_correcto_con_id_no_nulo()
        {
            val arbolACrear = ArbolBinario.vacio<Int>()
            val arbolConIdCreado = repositorio.crearArbol(arbolACrear)
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

            val arbolConIdCreado = repositorio.crearArbol(arbol)
            Assertions.assertNotNull(arbolConIdCreado.id)
            Assertions.assertEquals(arbol, arbolConIdCreado.arbol)
        }

        @Test
        fun al_crear_dos_arboles_retorna_ids_diferentes()
        {
            val arbolACrear = ArbolBinario.vacio<Int>()
            val arbolConIdCreado1 = repositorio.crearArbol(arbolACrear)
            val arbolConIdCreado2 = repositorio.crearArbol(arbolACrear)
            Assertions.assertNotEquals(arbolConIdCreado1.id, arbolConIdCreado2.id)
        }
    }
}