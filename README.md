## Giphy Search 
An Android app that utilizes the [Giphy search API](https://developers.giphy.com/docs/api/endpoint#search) to search for Gifs. 

![gif-search](https://user-images.githubusercontent.com/831071/120631202-e7871f80-c467-11eb-9bcf-1bde37bb99e7.gif)

## API Key 
Please [request](https://support.giphy.com/hc/en-us/articles/360020283431-Request-A-GIPHY-API-Key) an API key from Giphy and add it to [ApiKeyInterceptor.kt](https://github.com/vshivam/gif-search/blob/b1c5dfe29a08d118adef09755affd4e844471fde/app/src/main/java/com/search/giphy/base/networking/interceptor/ApiKeyInterceptor.kt#L23) 

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
Follows the MVVM architecture with a data -> domain <- presentation setup within a feature.

```
GiphySearch/
├── app
│   └── src/main
│   └── src/test (tests are available here)
```
