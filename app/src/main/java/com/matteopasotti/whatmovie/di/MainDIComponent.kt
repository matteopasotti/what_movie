package com.matteopasotti.whatmovie.di

/**
 * Main dependency component.
 * This will create and provide required dependencies with sub dependencies.
 */
val appComponent = listOf(mainModule, netModule, dbModule, useCasesModule, prefModule, repoModule)