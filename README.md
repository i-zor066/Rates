## Rates - currency converter

The app uses Revolut's hiring API to get currency rates, restcountries API to get country data and Flagopedia to get country flags. It utilises modular architecture with Rates module divided on data, domain and presentation. With data handling all the network requests, domain specifying all the use cases and presentation dealing with presenting the data in turn using the MVVM architecture with livedata and rxJava.