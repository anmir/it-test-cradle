###USAGE
- run mockserver/app.kt (Ktor)
- run books/booksInteraction.kt (Fuel)
- run authors/authorsInteraction.kt (Ktor)
- run mockserver/asynctest.kt (Fuel) 


###todo:
#### api&domain
- think about session. Maybe it should be smth like this:
```
AuthorsApi.session {
    getAuthor(1)
    getAllAuthors()
}
```
- handle different types of exceptions - ApiException с JacksonSubTypes, где errorCode->JacksonType!! 
- db access
- kotlinx serialization
#### tests
- preconditions
- flow separation
- allure