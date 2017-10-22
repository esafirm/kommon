[![](https://jitpack.io/v/esafirm/kommon.svg)](https://jitpack.io/#esafirm/kommon)

## Kommon

Set of utilities and helpers functions for Android in form of Kotlin extension function or top level function. Grab only what you need 👌

## Gradle 

In your root `build.gradle`

```groovy
allprojects {
    repositories {
	    ...
	    maven { url 'https://jitpack.io' }
	}
}
```    

In your module `build.gradle` 

```groovy
dependencies {
    final kommonVersion = 'X.X.X'
    implementation "com.github.esafirm.kommon:kommonviews:$kommonVersion"
    implementation "com.github.esafirm.kommon:kommoncompoundview:$kommonVersion"
    implementation "com.github.esafirm.kommon:kommon:$kommonVersion"
}
```

Version can be seen in [Release Page](https://github.com/esafirm/kommon/releases)

## TODO

- Add **TEST!!** 
- Add RecylcerView module
- Get what usable and useful from the [old repo](https://github.com/esafirm/androidcommon)

## License

Apache 2.0 