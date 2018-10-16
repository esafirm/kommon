[![](https://jitpack.io/v/esafirm/kommon.svg)](https://jitpack.io/#esafirm/kommon)
[![CircleCI](https://circleci.com/gh/esafirm/kommon/tree/master.svg?style=svg)](https://circleci.com/gh/esafirm/kommon/tree/master)

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
    final kommonVersion = 'x.y.z'
    implementation "com.github.esafirm.kommon:kommon:$kommonVersion"
    implementation "com.github.esafirm.kommon:kommonviews:$kommonVersion"
    implementation "com.github.esafirm.kommon:kommoncompoundview:$kommonVersion"
    implementation "com.github.esafirm.kommon:kommonadapter$;kommonVersion"
}
```
Where `x.y.z` is the version of Kommon. 

Versions can be seen in [Release Page](https://github.com/esafirm/kommon/releases)

## Usage

For now, the only usage example is exist in the `sample` app.

## Readme

Module specific read me files:

- [Kommon Adapter](https://github.com/esafirm/kommon/blob/master/kommonadapter/README.md)

## TODO

- [ ] Add **TEST!!** 
- [ ] Depends on android KTX? 
- [x] Add RecylcerView module **DONE**
- [x] Get what usable and useful from the [old repo](https://github.com/esafirm/androidcommon) **DON'T DO THIS**

## License

Apache 2.0 
