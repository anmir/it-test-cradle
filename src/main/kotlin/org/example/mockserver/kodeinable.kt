package org.example.mockserver

import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

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
// kodein has problems (todo: update idea, kotlin)
//val kodein = Kodein {
//    bind<MockService>(tag = "local") with provider { MockService1() }
//}

