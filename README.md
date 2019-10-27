# Hopper
[![](https://jitpack.io/v/efebudak/hopper.svg)](https://jitpack.io/#efebudak/hopper) [![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)
Hopper is a hopping animation specific android library. 

<img src="./readmeassets/Hopper.gif" width="250" />

## Usage
**Simple usage:**

    Hopper.HopperBuilder(buttonLogin)  
        .build()  
        .start()
**Hop direction:**
Hop direction can be changed by setting the direction value of the HopperBuilder. It is `BOTTOM_TO_TOP` by default.

    Hopper.HopperBuilder(buttonLogin)  
        .hopDirection(HopDirection.TOP_TO_BOTTOM)  
        .build()  
        .start()
**Distance:**
It can be defined as **pixel** or **dp**.
As pixel:

    Hopper.HopperBuilder(buttonLogin)  
        .hopDirection(HopDirection.TOP_TO_BOTTOM)  
        .distance(60f)  
        .build()  
        .start()
As dp:

    Hopper.HopperBuilder(buttonLogin)  
        .hopDirection(HopDirection.TOP_TO_BOTTOM)  
        .distanceInDp(16f)  
        .build()  
        .start()

> *Note: Check min values for these parameters.*

**Duration:**
Total animation duration can be set by duration value. It is in ms. Minimum animation duration is 500ms.

    Hopper.HopperBuilder(buttonLogin)  
        .hopDirection(HopDirection.TOP_TO_BOTTOM)  
        .distanceInDp(16f)  
        .duration(750)  
        .build()  
        .start()

**Times:**
Defines how many times the view will hop. By default it is 0 which means that it will hop forever.

    Hopper.HopperBuilder(buttonLogin)  
        .hopDirection(HopDirection.TOP_TO_BOTTOM)  
        .distanceInDp(16f)  
        .duration(750)  
        .times(5)  
        .build()  
        .start()

**HopperFinishedListener:**

    Hopper.HopperBuilder(buttonLogin)  
        .hopDirection(HopDirection.TOP_TO_BOTTOM)  
        .distanceInDp(16f)  
        .duration(750)  
        .times(5)  
        .addHopperFinishedListener {   
           //Do what you want to do  
        }  
        .build()  
        .start()
> *Note: Times value **MUST NOT** be 0. This method will throw an exception if the hopper is infinite and have a finished listener.*

**End:**
Hopper can also be stopped whenever it is necessary.

    val hopper = Hopper.HopperBuilder(textView)
        .build()
    hopper.start()
    hopper.end()

## Dependency
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    compile 'com.github.efebudak:hopper:1.0.0'
}
```