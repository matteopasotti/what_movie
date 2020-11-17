package com.matteopasotti.whatmovie.app

import com.matteopasotti.whatmovie.MyApplication
import org.koin.core.module.Module

class TestMainApp : MyApplication() {
    override fun provideDependency(): List<Module> = listOf<Module>()
}