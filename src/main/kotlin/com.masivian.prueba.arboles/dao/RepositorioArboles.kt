package com.masivian.prueba.arboles.dao

import com.masivian.prueba.arboles.ArbolBinario
import com.masivian.prueba.arboles.ArbolBinarioConId

interface RepositorioArboles
{
    fun crearArbol(arbolBinario: ArbolBinario<Int>): ArbolBinarioConId<Int>

    fun liberarRecursos()
}