package com.masivian.prueba.arboles.dao

import com.j256.ormlite.field.DataType
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import com.masivian.prueba.arboles.ArbolBinario
import com.masivian.prueba.arboles.ArbolBinarioConId
import com.masivian.prueba.arboles.red.ArbolBinarioDTO
import com.masivian.prueba.arboles.red.ConfiguracionJackson

@DatabaseTable(tableName = ArbolBinarioDAO.TABLA)
internal data class ArbolBinarioDAO(
    @DatabaseField(columnName = COLUMNA_ID, generatedId = true)
    val id: Long? = null,
    // Se guarda el arbol como su representación en json por simplicidad. De ser necesario se puede partir en
    // multiples tablas. Pero en ese caso probablemente sea mejor irse por una bd nosql
    @DatabaseField(columnName = COLUMNA_CONTENIDO, dataType = DataType.SERIALIZABLE)
    val arbol: String? = null
)
{
    companion object
    {
        const val TABLA = "arbol"
        const val COLUMNA_ID = "id"
        const val COLUMNA_CONTENIDO = "contenido"
    }

    constructor(arbolBinario: ArbolBinario<Int>):
            this(
                null,
                ConfiguracionJackson.mapper.writeValueAsString(ArbolBinarioDTO(arbolBinario))
            )

    fun aArbolBinarioConId(): ArbolBinarioConId<Int>
    {
        assert(id != null)
        val arbol = ConfiguracionJackson.mapper.readValue(arbol, ArbolBinarioDTO::class.java)
        return ArbolBinarioConId(id, arbol.aArbolBinario())
    }
}