package com.masivian.prueba.arboles.red

import com.masivian.prueba.arboles.dao.ErrorBD
import java.lang.Exception
import javax.ws.rs.core.Response

abstract class ExcepcionRed(val codigoHttp: Response.Status, mensaje: String, causa: Throwable?)
    : Exception(mensaje, causa)

class EntidadInexistente(nombreEntidad: String, idEntidad: String)
    : ExcepcionRed(Response.Status.NOT_FOUND, "The entity $nombreEntidad[$idEntidad] does not exist", null)

class ParametroInvalido(mensaje: String)
    : ExcepcionRed(Response.Status.BAD_REQUEST, mensaje, null)

class ArbolInvalido(mensaje: String)
    : ExcepcionRed(Response.Status.BAD_REQUEST, mensaje, null)

class ErrorEnDAONoEsperado(causa: ErrorBD)
    : ExcepcionRed(Response.Status.INTERNAL_SERVER_ERROR, causa.message!!, causa)