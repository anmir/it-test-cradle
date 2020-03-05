TEST RUN : [![CircleCI](https://circleci.com/gh/anmir/it-test-cradle/tree/master.svg?style=svg)](https://circleci.com/gh/anmir/it-test-cradle/tree/master)

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
### 
- gradle kotlin mirgation
- jenkins kotlin (like https://github.com/ibook/jenkins-kotlin/blob/master/Jenkinsfile)
###
- add ci

###
- resolve:
	Suppressed: java.lang.UnsatisfiedLinkError: no netty_transport_native_epoll_x86_64 in java.library.path
		at java.lang.ClassLoader.loadLibrary(ClassLoader.java:1867)
		at java.lang.Runtime.loadLibrary0(Runtime.java:870)
		at java.lang.System.loadLibrary(System.java:1122)
		at io.netty.util.internal.NativeLibraryUtil.loadLibrary(NativeLibraryUtil.java:38)
		at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
		at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
		at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
		at java.lang.reflect.Method.invoke(Method.java:498)
		at io.netty.util.internal.NativeLibraryLoader$1.run(NativeLibraryLoader.java:369)
		at java.security.AccessController.doPrivileged(Native Method)
		at io.netty.util.internal.NativeLibraryLoader.loadLibraryByHelper(NativeLibraryLoader.java:361)
		at io.netty.util.internal.NativeLibraryLoader.loadLibrary(NativeLibraryLoader.java:339)
		... 23 common frames omitted