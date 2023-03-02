# RecipeApp

- I have built fully functional recipe app.It is developed 
last updated technologies in android mobile app world.


# Screenshots

![RECİPE APP](https://user-images.githubusercontent.com/64928807/207524527-86863a83-29e4-4da6-bb61-a538523862f2.PNG)

# Demo

 ![recipe app v1](https://user-images.githubusercontent.com/64928807/207524555-983cb53b-9a82-4b38-beb7-ba2f3aebca47.gif)

# Architecture


This app implements MVVM architecture. App consist of different fragments and 1 root activity. Activity holds a container layout in order to manage fragments which will be controlled by navigation component. Here is the package structure:

![image](https://user-images.githubusercontent.com/64928807/219080594-d41f9c5c-6621-44dd-be6a-7fb2424b9150.png)

Data

Data package should include response models, data source. It shouldn't know any logic.

UI

UI like a feature. It contains Fragments,  and feature related classes like a domains, mappers and ui models. Make sure that all classes here are specific to the this feature. If it is a class that is also used in other features, it should be moved to the common package.

Di

This package may actually be inside the common module. But I prefer to carry outside of core package to be more visible.

Viewmodel

This package includes viewmodels.

Util

This package include utility class that has glide image loading etc.

**Tools that I used** :

- Navigation Component
- Hilt for dependency injection
- Retrofit 
- Room
- Data store preferences
- Coroutines for database
- Livedata
- View Binding
- Glide for image loading
- RESTApi
- LottieAnimationView

**Arhictecture:**

- MVVM

                                         

