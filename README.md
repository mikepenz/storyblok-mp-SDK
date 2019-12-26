# storyblok-mp-SDK

This is the Storyblok Kotlin Multiplatform client for easy access of the read API.
It uses Kotlin multiplatform and coroutines to offer a convenient API for all supported target architectures.

# More about Storyblok
- **WEBSITE** https://www.storyblok.com/
- **API DOC** https://www.storyblok.com/docs/api/content-delivery

# Include in your project
## Using Maven
```gradle
// NOTE: currently only released to a temp repo.
repositories {
    ...
    maven { url "https://dl.bintray.com/mikepenz/mptest/" }
}

implementation "com.mikepenz:storyblok-mp-sdk:0.0.2"
```

## How to use
### Init client

```kotlin
val client = Storyblok("your-storyblok-token")
```

### General

This Kotlin multi platform library uses Kotlin coroutines for the requests (similar to the ktor API).
All fetch methods are suspending functions, and need to be called respectively.

### Load a story
```kotlin
client.fetchStory("full_slug:id:uuid")
// All storyblock params are supported, see javadoc for more information
```

### Load a list of stories
```kotlin
client.fetchStories()
// All storyblock params are supported, see javadoc for more information
```

### Load current space
```kotlin
client.fetchCurrentSpace()
```

### Load a list of datasources
```kotlin
client.fetchDatasources()
```

### Load a map of datasource entries
```kotlin
client.fetchDatasourceEntries()
```

### Load a list of map of links with their uuid as key
```kotlin
client.fetchLinks()
```

### Load a list of tags
```kotlin
client.fetchTags()
```


## Libs used in android sample app:
Mike Penz:
- FastAdapter https://github.com/mikepenz/FastAdapter
- AboutLibraries https://github.com/mikepenz/AboutLibraries
- Android-Iconics https://github.com/mikepenz/Android-Iconics
- MaterialDrawer https://github.com/mikepenz/MaterialDrawer

# Developed By

* Mike Penz 
  * [mikepenz.com](http://mikepenz.com) - <mikepenz@gmail.com>
  * [paypal.me/mikepenz](http://paypal.me/mikepenz)

# Contributors

This free, open source software was also made possible by a group of volunteers that put many hours of hard work into it. See the [CONTRIBUTORS.md](CONTRIBUTORS.md) file for details.


# Credits

Some parts of the Kotlin Multiplatform configurations are a result of various great resources on the internet. Including:
- https://github.com/joreilly/PeopleInSpace
- https://natanfudge.github.io/fudgedocs/publish-kotlin-mpp-lib.html
- https://github.com/Kotlin/kotlinx.serialization
- https://github.com/Kotlin/dokka
- and the official Kotlin multiplatform docs


# License

    Copyright 2020 Mike Penz

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
