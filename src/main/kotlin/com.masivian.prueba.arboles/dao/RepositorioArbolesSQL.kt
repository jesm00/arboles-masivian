package com.masivian.prueba.arboles.dao

import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.table.TableUtils
import com.masivian.prueba.arboles.ArbolBinario
import com.masivian.prueba.arboles.ArbolBinarioConId

/*
NOTA: Este repositorio usa los objetos DTO para persistir y recuperar los árboles como strings. Por lo tanto, si se
modifican los DTOs y/o el mapper se debe mantener compatibilidad hacia atras. De lo contrario los valores guardados
con anterioridad dejaran de ser legibles. En ese caso sería necesario migrar los datos previos a una nueva versión.
Para evitar ese problema podria valer la pena crear mappers y DTOs internos de esta capa, pero por ahora es overkill
 */
class RepositorioArbolesSQL(private val proveedorConexionesORMLite: ProveedorConexionesORMLite): RepositorioArboles
{
    private val daoArbol = DaoManager.createDao(proveedorConexionesORMLite.fuenteConexiones, ArbolBinarioDAO::class.java)

    init
    {
        TableUtils.createTableIfNotExists(daoArbol.connectionSource, daoArbol.dataClass)
    }

    override fun crearArbol(arbolBinario: ArbolBinario<Int>): ArbolBinarioConId<Int>
    {
        val arbolDao = ArbolBinarioDAO(arbolBinario)
        daoArbol.create(arbolDao)
        return arbolDao.aArbolBinarioConId()
    }


    override fun liberarRecursos()
    {
        proveedorConexionesORMLite.liberarRecursos()
    }
}