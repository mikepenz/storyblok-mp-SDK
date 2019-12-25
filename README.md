# storyblok-mp-SDK

This is the Storyblok Kotlin Multiplatform client for easy access of the read API.
It uses Kotlin multiplatform and coroutines to offer a convenient API for all supported target architectures.

# More about Storyblok
- **WEBSITE** https://www.storyblok.com/
- **API DOC** https://www.storyblok.com/docs/api/content-delivery

# Include in your project
## Using Maven

```gradle
// NOTE: currently unreleased
implementation "com.mikepenz:storyblok-mp-sdk:0.0.1"
```

## How to use
### Init client

```kotlin
val client = Storyblok("your-storyblok-token")
```

### Load a story
```kotlin
client.fetchStory("full-slug")
```

### Load a list of stories
```kotlin
client.fetchStories()
```

### Load a list of tags
```kotlin

```

### Load a map of links
```kotlin

```

### Load a list of datasources
```kotlin

```


## Libs used in sample app:
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
