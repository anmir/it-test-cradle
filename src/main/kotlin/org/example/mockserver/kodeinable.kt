package org.example.mockserver

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


