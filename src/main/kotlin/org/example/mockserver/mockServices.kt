package org.example.mockserver

import org.koin.core.qualifier.named
import org.koin.dsl.module


interface MockService {

    fun tukTuk(): KtoTam

}

class MockService1 : MockService {

    override fun tukTuk(): KtoTam {
        return KtoTam()
    }
}

class MockService2 : MockService {

    override fun tukTuk(): KtoTam {
        return KtoTam("ничего")
    }
}

class MockService3 : MockService {

    override fun tukTuk(): KtoTam {
        return KtoTam("ничто")
    }
}

data class KtoTam(
    val name: String = "Почтальон Печкин"
)

val mockServiceModule = module {
    single<MockService>(named("mock1")) { MockService1() }
    single<MockService>(named("mock2")) { MockService2() }
    single<MockService>(named("mock3")) { MockService3() }
}
