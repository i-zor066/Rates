package io.csqn.core.network

import java.io.IOException

data class InternetDownException(val error: String) : IOException(error)