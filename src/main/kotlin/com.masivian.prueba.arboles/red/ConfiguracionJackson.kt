package com.masivian.prueba.arboles.red

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.afterburner.AfterburnerModule

object ConfiguracionJackson
{

    @JvmField
    val mapper = ObjectMapper().apply {
        disable(
            MapperFeature.AUTO_DETECT_CREATORS,
            MapperFeature.AUTO_DETECT_FIELDS,
            MapperFeature.AUTO_DETECT_GETTERS,
            MapperFeature.AUTO_DETECT_IS_GETTERS
        )
        disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        registerModule(AfterburnerModule())

        enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
    }
}