package com.gmail.moviemaven.data.source.network

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val movieAppDispatchers: MovieAppDispatchers)

enum class MovieAppDispatchers {
    IO
}
