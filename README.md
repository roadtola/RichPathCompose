<img src="/screenshots/header.gif" width="400">
<img src="/screenshots/samples.gif" align="right" width="120">

[![](https://jitpack.io/v/roadtola/RichPathCompose.svg)](https://jitpack.io/#roadtolaRichPathCompose)

**Compose Support** 💪 Rich Android Path. 🤡 Draw as you want. 🎉 Animate much as you can.

### Features

- **Full Animation Control on Paths and VectorDrawables:**
  Animate any attribute in a specific path in the VectorDrawable
- **Perfect for animating complex vectors**
  <img src="/screenshots/preview.gif" width="300">

`fillColor`, `strokeColor`, `strokeAlpha`, `fillAlpha`, `size`, `width`, `height`, `scale`, `scaleX`, `scaleY`, `rotation`, `translationX`, `translationY`, `trimPathStart`, `trimPathEnd`, `trimPathOffset`, `clearFillColor`, `clearFillAlpha`, `clearAll`.

- **Path morphing:**

<img src="/screenshots/animal_path_morphing.gif" width="250">

```Kotlin
RichPathAnimator.animate(richPath)
       .pathData(pathData1, pathData2, ...)
       .start();
```

## Add dependency

Add it in your root build.gradle at the end of repositories:

```gradle
    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

```

Then Add the dependency

```gradle
    dependencies {
	        implementation 'com.github.roadtola:RichPathCompose:1.1.1'
	}
```

## Jeptack Compose Usage

```kotlin
    RichPathCompose(
        modifier = Modifier
            .height(400.dp)
            .width(200.dp),
        vectorId = R.drawable.ic_front_muscles,
        scaleType = ImageView.ScaleType.FIT_CENTER,
        onLoad = {
            // invoke after vector is loaded
            allPaths = it.allPaths()
        },
        onPathClick = { group, path ->
            // invoke after path is clicked
            // return clicked path and group if exists
            text = "Group: ${group?.name}.${"\n"}Path: ${path.name}"
            selectedGroup = group?.paths?.toTypedArray() ?: arrayOf()
        }
    )
```

## Non Compose

#### 1. In your layout.

```xml
    <com.richpath.RichPathView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:vector="@drawable/vector_drawable" />
```

#### 2. Find your richPath.

```java
// by path name
RichPath richPath = richPathView.findRichPathByName("path_name");
// or if it contains one path
RichPath richPath = richPathView.findFirstRichPath();
// or by index
RichPath richPath = richPathView.findRichPathByIndex(0);
```

#### 3. Use the RichPathAnimator to animate your richPath.

```java
RichPathAnimator.animate(richPath)
       .trimPathEnd(value1, value2, ...)
       .fillColor(value1, value2, ...)
       .start();
```

## Example

#### notification icon vector drawable

<img src="/screenshots/ic_notifications.png" align="right" width="120">

```xml
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="32dp"
    android:height="32dp"
    android:viewportHeight="32.0"
    android:viewportWidth="32.0">

    <group
        android:pivotX="16"
        android:pivotY="6.25">
        <path
            android:name="top"
            android:fillColor="#FFF7F7F7"
            android:pathData="M22,19.8v-5c0-3.1-1.6-5.6-4.5-6.3V7.8c0-0.8-0.7-1.5-1.5-1.5s-1.5,0.7-1.5,1.5v0.7c-2.9,0.7-4.5,3.2-4.5,6.3v5l-2,2v1h16v-1L22,19.8z" />

        <path
            android:name="bottom"
            android:fillColor="#FFF7F7F7"
            android:pathData="M16,25.8c1.1,0,2-0.9,2-2h-4C14,24.9,14.9,25.8,16,25.8z" />
    </group>
</vector>
```

#### XML

```xml
    <com.richpath.RichPathView
        android:id="@+id/ic_notifications"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:vector="@drawable/ic_notifications" />
```

#### Java

<img src="/screenshots/ic_notifications.gif" align="right" width="120">

```java
RichPath top = notificationsRichPathView.findRichPathByName("top");
RichPath bottom = notificationsRichPathView.findRichPathByName("bottom");

RichPathAnimator.animate(top)
        .interpolator(new DecelerateInterpolator())
        .rotation(0, 20, -20, 10, -10, 5, -5, 2, -2, 0)
        .duration(4000)
        .andAnimate(bottom)
        .interpolator(new DecelerateInterpolator())
        .rotation(0, 10, -10, 5, -5, 2, -2, 0)
        .startDelay(50)
        .duration(4000)
        .start();
```

### More Control by the RichPathAnimator

- **Group your vectors for more easy and convenient control**

```kotlin
    richPathView.findRichGroupByName(value1)
```

- **Animate multiple paths sequentially or at the same time**

```java
RichPathAnimator
       .animate(richPath1, richPath2)
       .rotation(value1, value2, ...)

       //Animate the same path or another with different animated attributes.
       .andAnimate(richPath3)
       .scale(value1, value2, ...)

       //Animate after the end of the last animation.
       .thenAnimate(richPath4)
       .strokeColor(value1, value2, ...)

       // start your animation 🎉
       .start();
```

- **Which one of the paths is clicked?**

```kotlin
richPathView.setOnPathClickListener(new RichPath.OnPathClickListener() {
    public void onClick(richGroup: Group?, richPath: RichPath) {
	    if (richPath.getName().equals("path_name")) {
	        //TODO do an action when a specific path or group is clicked.
	    }
    }
});
```

## Credits

- [florent37](https://github.com/florent37) He is the creator of [ViewAnimator](https://github.com/florent37/ViewAnimator) which gave me the idea of this project. Some core concepts and ideas were reused, but everything is written from scratch.
- [Android](https://android.com/) Some code is reused form the android source code and the VectorDrawableCompat support library.
- [Alex Lockwood](https://github.com/alexjlockwood) The paths of the morphing sample is extracted from the [Shape Shifter](https://github.com/alexjlockwood/ShapeShifter) demo.

## Developed By

- Ahmed Tarek
- [tarek360.github.io](http://tarek360.github.io/)

## License

> Copyright 2017 Tarek360

> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at

> http://www.apache.org/licenses/LICENSE-2.0

> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
