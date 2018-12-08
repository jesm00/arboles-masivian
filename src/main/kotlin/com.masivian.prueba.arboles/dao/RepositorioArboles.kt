package com.masivian.prueba.arboles.dao

import com.masivian.prueba.arboles.ArbolBinario
import com.masivian.prueba.arboles.ArbolBinarioConId

interface RepositorioArboles
{
    @Throws(ErrorBD::class)
    fun crearArbolBinario(arbolBinario: ArbolBinario<Int>): ArbolBinarioConId<Int>

    @Throws(ErrorBD::class)
    fun buscarArbolBinarioPorId(id: Long): ArbolBinarioConId<Int>?

    fun liberarRecursos()
}