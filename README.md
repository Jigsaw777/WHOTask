# WHOTask
Sample task/test for WHO covid19 tracker

Libraries used : 
1) Shimmer layout : For attractive loading state
2) RxKotlin, RxJava : For reactive programming support
3) Retrofit : For easy working with HTTP or HTTPS client and conversion of the Json responses to the respective POJO classes
4) Logging interceptors : For checking the exact Urls that get hit when retrofit client calls the respective services and also the responses of these URL hits. Note that, logging interceptors should not be used in production environment as it can cause potential leaks. It has been used in this project only as an easy logging and debugging tool. 

Please note that the app's orientation has been disabled for the sake of simplicity.

Structure of the app : 
The app has been made to be scalable with the help of clean architecture (Onion architecture) and MVVM design pattern. The code has been documented properly as and when it was needed. A go through of the code is recommended to have a good idea of the structure and the improvements that can be done.
The structure of the app has been divided into 4 directories : 

The dependencies of the layers in clean architecture can imagined as follows with arrow representing as the leftmost value dependent on the rightmost value :

domain <- data <- UI

1) The domain package (innermost and the independent layer of clean architecture): The domain package consists of the actual business logic. It contains : 
  a) Entities : The actual templates of the core objects of the app.
  b) Repositories : Repositories are the contracts that have methods that need to be defined. Any class that implements these conracts are bound to define the contract's methods/requirements. It can be noticed that almost everywhere in the app, interfaces have been coded to their respective implementations and only the interface references have been used. This is to provide easy decoupling and easy refactoring of code if at a later stage, implementation needs to be changed.
  c) Requests : These are the request templates that are sent to the server. Thesse requests can contain single query parameters or parameter maps. 
  d) Usecases : These are the classes that actually execute the contract methods. Use cases are accessed by the View Model for executing any required queries.
  
2) The data package (The middle layer between UI and domain package) : The data package consists of the implementations and the app data. It contains : 
  a) Constants package : It contains all the global App data strings that may be used by other packages. It contains data like the base url, or some other app constants as required.
  b) RepoImplementation package : This package contains the classes that make use of the Repositories. They contain the actual implementation methods of the Repository contracts. The reason contract implementation has been kept away from the contract declaration is to change the implementation as and when required at a later stage. This allows easy refactor without having to change other parts of the code.
  c) Services package : Services contain the network calls that need to be made and the responses that will be got after each response call.
  
3) The UI package (The outermist layer) : The UI package consists of the Views,ie., the Activies, fragments, ViewModels, etc,. It contains : 
  a) Activities : Contains the activities that are exposed to the user and with which the user interacts.
  b) Adapters : Contains the adapters that the recycler views use.
  c) CustomViews : Contains the custom views that may be used by other views as and when required.
  d) ViewHolders : Contains all the view holders that the recycler view adapters can use.
  e) ViewModels : Contains all the view models that an activity can use. View Models are lifecycle aware and also persist orientation changes. Each screen has its own view model.

Features : 
1) Location is fetched in the splash activity.
2) Clean archhitecture has been used
  
Improvements that can be done : 
1) Dagger can be used to resolve class/method/interface dependencies
2) Right now, network manager has been instantiated naively. Static objects has been used to make singletons(which is not thread safe). These can easily optmised to be thread safe using the traditional singleton pattern or enums or Bill Hugh's Singleton pattern
3) Currently, splash activity doesn't have any View Model. It can have its own View Model to check for App versions and update accordingly or if any network call needs to be made to intialise any application level variables. In this project no such View model is used as splash activity doesn't require any network calls as such.

Todo: 
This app can be scaled to add other features like :
1) Show a list of countries with confirmed and death cases.
2) Let user choose which country's cases he wants to see.
3) As this app uses Fine location, the user can be shown nearby cases with an accuracy of 3-5 metres.

The app screenshots have been shared in app/images/ folder.
