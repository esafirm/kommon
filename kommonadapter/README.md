# KommonAdapter

> A `RecyclerView` adapter for kommoners ~ 

RecyclerView Adapter + Kotlin Android Extension + Lambda = ❤️

# Gradle 

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
    implementation "com.github.esafirm.kommon:kommonadapter$kommonVersion"
}
```
Where `x.y.z` is the version of Kommon. 

# Usage

There's a few components in `kommonadapter`, but i think the most interesting one is `SimpleAdapter`

```kotlin
SimpleAdapter(this).create {
    map(
        typePredicate = { pos, _ -> pos == 0 },
        layout = R.layout.item_header,
        binder = { vh, _ -> vh.item_txt_header.text = "Ini Header" }
    )
    map<String>(R.layout.item_text) { vh, item ->
        vh.item_txt.text = item
        vh.item_img.setImageDrawable(ColorDrawable(Color.BLACK))
    }
    map<Int>(R.layout.item_text) { vh, number ->
        vh.item_txt.text = "Nomor $number"
        vh.item_img.setImageDrawable(ColorDrawable(Color.RED))
    }
}.also { recyclerView.attach(adapter = it ) }
```

You can find more sample in [here](https://github.com/esafirm/kommon/blob/master/kommonsample/src/main/java/com/nolambda/kommonsample/AdapterSampleActivity.kt) and [here](https://github.com/esafirm/kommon/blob/master/kommonsample/src/main/java/com/nolambda/kommonsample/MultiWithSimpleDelegateActivity.kt)