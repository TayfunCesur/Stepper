<p align="center"><a href="https://github.com/TayfunCesur/Stepper" target="_blank"><img width="150"src="art/snail.png"></a></p>
<h1 align="center">Stepper</h1>
<p align="center">Fancy Step Wizard 😎</p>
<p align="center">
  <a href="https://github.com/TayfunCesur/Stepper"><img src="https://badges.frapsoft.com/os/v1/open-source.svg?v=103" ></a>
  <a href="https://circleci.com/gh/TayfunCesur/Stepper"><img src="https://circleci.com/gh/TayfunCesur/Stepper.svg?style=svg" alt="Build Status"></a>
    <a href="https://android-arsenal.com/api?level=16"><img src="https://img.shields.io/badge/API-16%2B-orange.svg?style=flat" alt="api"></a>
    <a href="https://jitpack.io/#TayfunCesur/Stepper"><img src="https://jitpack.io/v/TayfunCesur/Stepper.svg" alt="jitpack"></a>
</p>

### Summary  
Stepper, helps you to show beautiful step animation especially registration steps or some process which contains lots of steps. In order to notify to user and show the current situation and prevent them to be bored.

### Download

This library is available in **jitpack**, so you need to add this repository to your root build.gradle at the end of repositories:
   
```groovy  
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency:

```groovy 
dependencies {
    implementation 'com.github.TayfunCesur:Stepper:1.0'
}
``` 

## Sample Usage  

A sample project in Kotlin that demonstrates the lib usage [here](https://github.com/TayfunCesur/Stepper/tree/master/app).
<img height="500" align="right" src="/art/stepper.gif"></img>
```
    <com.tayfuncesur.stepper.Stepper
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            android:id="@+id/stepper"
    >

        //You can use any view inside
        //Freedom!!!
        <View android:layout_width="wrap_content"
              android:layout_height="10dp"
              android:id="@+id/StepperView"
              android:background="@drawable/gradient"/>

    </com.tayfuncesur.stepper.Stepper>
```
### In your Activity/Fragment
#### To go forward
```
stepper.forward()
```
#### To go back
```
stepper.back()
```
#### To progress
```
stepper.progress(loopsize = 3) // Default value is 0
```
#### Bonus(Complete Listener)
```
stepper.progress(loopsize = 3).addOnCompleteListener {
    // Here some magic stuffs
}
```


### Customizations
Property | Type | Description
--- | --- | ---
stepCount | integer | The default step count is 5. Each count will be calculated with respect to your screen width.
duration | integer | The default duration is 500 milliseconds. This value effects your each step animation

### Coloring
You can use the backgroundColor attribute for coloring whole background of stepper. And use the inner views background for coloring animation side.

## Project Maintained By

### [Tayfun Cesur](https://twitter.com/CesurTayfun35)

Open-Source Enthusiast | Android Engineer

<a href="https://www.linkedin.com/in/tayfun-cesur-353958157/"><img src="https://seeklogo.com/images/L/linkedin-in-icon-logo-2E34704F04-seeklogo.com.png" width="40" style="margin-right:8px"></a>
<a href="https://twitter.com/CesurTayfun35"><img src="https://seeklogo.com/images/T/twitter-2012-positive-logo-916EDF1309-seeklogo.com.png" width="40" style="margin-right:8px"></a>

### Greetings
If you have any questions, hit me on [Twitter](https://twitter.com/CesurTayfun35)

## Licence
```
Copyright 2019 Tayfun CESUR

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


