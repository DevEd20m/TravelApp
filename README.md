# Android Clean Architecture - Share Your Experience

_Esta aplicación es un ejemplo donde podemos registrarnos con una cuenta propia, y ya logueado, poder compartir nuestras experiencias de viajes, por nuestro "Perú"._

Bienvenido 👋, espero que este repositorio te sea de mucha ayuda para comprender acerca de la Arquitectura Limpia y el uso de Principios Sólidos.

## ¡Capturas de Pantalla!
<img src="https://firebasestorage.googleapis.com/v0/b/myexpinperu.appspot.com/o/ImagenesGithub%2FlogIn.png?alt=media&token=49a90f78-c9d5-44c3-a4ab-ee02f680d3c5" width="300" height="500" /> <img src="https://firebasestorage.googleapis.com/v0/b/myexpinperu.appspot.com/o/ImagenesGithub%2FregisterUser.png?alt=media&token=cae04eda-b52e-4b2b-91e9-26ec5061e9c3" width="300" height="500" /> <img src="https://firebasestorage.googleapis.com/v0/b/myexpinperu.appspot.com/o/ImagenesGithub%2Fhome.png?alt=media&token=555c5f6e-915b-414c-9a83-d61ed71eb22a" width="300" height="500" />  <img src="https://firebasestorage.googleapis.com/v0/b/myexpinperu.appspot.com/o/ImagenesGithub%2FdetailPlace.png?alt=media&token=14ddb71f-cc71-4360-a2ce-0efe1279680d" width="300" height="500" />  <img src="https://firebasestorage.googleapis.com/v0/b/myexpinperu.appspot.com/o/ImagenesGithub%2Fshare.png?alt=media&token=dcaf9ce7-e0e4-4eeb-bb40-78ceff7416ad" width="300" height="500" />



## ¡Razones por las que deberías de darte un tiempo en revisarlo!

1. Modularización de Aplicaciones
2. Uso de componentes de Jetpack Android
3. Uso de Firebase  🔥
4. Uso de una base de datos interna 
5. Uso de coroutines para gestionar los procesos 
6. Uso de MVVM como patrón de arquitectura de presentación
4. Uso de Work Manager
5. Uso de un inyector de dependencias
5. Y no está más decir que esta hecho el 100% en Kotlin <3 


## Dependencies

* [Lifecycle version: '2.0.0'](https://square.github.io/retrofit/)
* [ViewModel version: '2.0.0'](https://square.github.io/retrofit/)
* [Coroutines version: '1.3.5'](https://github.com/Kotlin/kotlinx.coroutines/)
* [Coroutines Play Service version: '1.1.1'](https://square.github.io/okhttp/)
* [Coil version: '0.9.5'](https://square.github.io/retrofit/)
* [Firebase Storage version: '19.1.0'](https://kotlinlang.org/)
* [Firebase Auth version: '19.1.0'](https://kotlinlang.org/)
* [Firebase Firestore version: '21.4.0'](https://kotlinlang.org/)
* [Room version: '2.2.5'](https://kotlinlang.org/)
* [Work Manager version: '2.3.4'](https://kotlinlang.org/)
* [Koin version: '2.1.6'](https://kotlinlang.org/)
* [Koin ViewModel version: '2.1.6'](https://kotlinlang.org/)
* [Dexter version: '6.0.2'](https://kotlinlang.org/)


## Arquitectura

La arquitectura del proyecto sigue los principios de la Arquitectura Limpia y así como los Principios Sólidos.
Las capas están modularizadas de la siguiente manera:

### App - Presentación - Framework
En esta capa estamos uniendo 2 capas que bien podrían ir separadas, la de Datos (Retrofit, Firebase, Room) y la capa de Presentación.

La sub capa interna de Presentación, hace uso del Framework de Android, este tiene la responsabilidad de gestionar los eventos y mostrar la UI, así mismo hacemos uso del patrón de presentación MVVM, dicho patrón está incluido como una biblioteca de Architecture Components,  que permite gestionar el ciclo de vida de las Actividades y recuperando los datos al comunicarse con los UseCases (Interactors)

La sub capa de Framework o Data, tiene la responsabilidad de recuperar los datos de fuentes externas o internas como Firebase, un Servicio de Backend, Room, u otros.

### Interactors

La capa de Interactors tiene la responsabilidad de contener los casos de uso de la Aplicación, estos se comunicarán con los distintos repositorios para obtener los datos.

### Data

La capa de Datos es nuestro punto de comunicación entre las capas de Interactors y la capa de Datos externos (Firebase, Room, etc).
Dentro se contienen los repositorios que implementan las distintas fuentes de datos ya mencionados anteriormente, obteniendo de esta manera los datos de origen externo u interno.

### Domain
La capa de Domain tiene la responsabilidad de modelar la aplicación, dentro están los modelos que regirán a los casos de uso y la UI.

## Conclusión
El uso de una Arquitectura Limpia da grandes ventajas, como hacer del proyecto escalable y mantenible en el tiempo.

Pero tener en cuenta que antes de comenzar un proyecto debemos de evaluar la mejor opción, ya que a una mayor arquitectura, mayor la inversión del tiempo en el desarrollo.

Podrías optar por usar MVVM o MVP para modelar la capa de presentación y en un futuro, implementar Clean Architecture.


## Gracias
Un especial agradecimiento a los autores de estos repositorios, ya que me apoyaron durante mi aprendizaje.

* [https://github.com/antoniolg/architect-coders](https://github.com/antoniolg/architect-coders)
* [https://github.com/bufferapp/android-clean-architecture-boilerplate](https://github.com/bufferapp/android-clean-architecture-boilerplate)

## Autor
* **Edmundo Prado** - [DevEd20m](https://github.com/DevEd20m)

