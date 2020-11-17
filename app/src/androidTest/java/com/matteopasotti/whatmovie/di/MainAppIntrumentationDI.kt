package com.matteopasotti.whatmovie.di

/**
 * Main Koin DI component for Instrumentation Testing
 */
fun generateTestAppComponent(baseApi: String)
        = listOf(
    configureNetworkForInstrumentationTest(baseApi),
    mainModule,
    useCasesModule,
    prefModule,
    roomTestModule,
    MockWebServerInstrumentationTest,
    repoModule
)
