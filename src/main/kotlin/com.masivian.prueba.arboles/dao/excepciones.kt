package com.masivian.prueba.arboles.dao

import com.fasterxml.jackson.databind.JsonMappingException
import kotlin.Exception

abstract class ErrorBD(mensaje: String, causa: Throwable?)
    : Exception(mensaje, causa)

class ErrorMapeoBD(mensaje: String, causa: JsonMappingException?)
    : ErrorBD(mensaje, causa)

class ErrorDesconocidoBD(mensaje: String, causa: Throwable?)
    : ErrorBD(mensaje, causa)