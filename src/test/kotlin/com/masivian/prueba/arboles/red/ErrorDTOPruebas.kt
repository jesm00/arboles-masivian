package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.databind.JsonMappingException
import com.masivian.prueba.arboles.ArbolBinario
import com.masivian.prueba.arboles.ArbolBinarioConId
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.skyscreamer.jsonassert.JSONAssert
import org.skyscreamer.jsonassert.JSONCompareMode

@DisplayName("ErrorDTO")
class ErrorDTOPruebas
{
    @Nested
    inner class Serializacion
    {
        @Test
        fun error_con_mensaje_nulo_genera_json_correcto()
        {
            val error = ErrorDTO(null)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(error)
            val jsonEsperado = """
                {
                    "message": null
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }

        @Test
        fun error_con_mensaje_no_nulo_genera_json_correcto()
        {
            val mensajePrueba = "Un mensaje de error"
            val error = ErrorDTO(mensajePrueba)
            val jsonGenerado = ConfiguracionJackson.mapper.writeValueAsString(error)
            val jsonEsperado = """
                {
                    "message": "$mensajePrueba"
                }
            """.trimMargin()

            JSONAssert.assertEquals(jsonEsperado, jsonGenerado, JSONCompareMode.STRICT)
        }
    }

    @Nested
    inner class Deserializacion
    {
        @Test
        fun lee_error_con_mensaje_nulo_correctamente()
        {
            val json = """
                {
                    "message": null
                }
            """.trimMargin()
            val errorEsperado = ErrorDTO(null)
            val errorLeido = ConfiguracionJackson.mapper.readValue(json, ErrorDTO::class.java)

            Assertions.assertEquals(errorEsperado, errorLeido)
        }

        @Test
        fun lee_error_con_mensaje_no_nulo_correctamente()
        {
            val mensajePrueba = "Un mensaje de error"
            val json = """
                {
                    "message": "$mensajePrueba"
                }
            """.trimMargin()
            val errorEsperado = ErrorDTO(mensajePrueba)
            val errorLeido = ConfiguracionJackson.mapper.readValue(json, ErrorDTO::class.java)

            Assertions.assertEquals(errorEsperado, errorLeido)
        }

        @Test
        fun lee_error_sin_mensaje_correctamente()
        {
            val json = """
                {
                }
            """.trimMargin()
            val errorEsperado = ErrorDTO(null)
            val errorLeido = ConfiguracionJackson.mapper.readValue(json, ErrorDTO::class.java)

            Assertions.assertEquals(errorEsperado, errorLeido)
        }
    }
}