## Developed on Android Studio
* Android Studio 4.2.1
* Build #AI-202.7660.26.42.7351085, built on May 10, 2021
* Runtime version: 11.0.8+10-b944.6916264 x86_64
* VM: OpenJDK 64-Bit Server VM

## Tech Stack
* Kotlin + coroutines + flow
* Android architecture components
* Hilt for Dependency Injection
* OkHttp as HTTP Client
* Retrofit as type safe REST Api client
* Moshi for serialization
* Coil for gif loading
* Junit5, Mockito for testing

## Architecture
Follows the MVVM architecture with a data -> domain <- presentation setup.

```
GiphySearch/
├── app
│   └── src/main
│   └── src/test (tests are available here)
```