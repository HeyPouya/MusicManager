# MusicManager
An Android application for wise ;)

## Description

### How app works

This application is designed to find singers and their top albums. I used the api from last.fm website (you can find the details in api section). There are 4 main pages :

1.  Saved albums : shows albums saved by user. works also in offline mode
2.  Search artist
3.  Top albums : shows top albums of the selected artist.
4.  Album details : shows details of the selected album

### Technologies and Architecture

Technologies that have been used in this projects are :

*   MVVM
*   Room
*   ViewModel
*   Dark/Light Mode
*   DI via Hilt
*   Retrofit
*   Kotlin Coroutines
*   Kotlin
*   LiveData
*   Navigation
*   Single Activity
*   Safe Args
*   DiffUtils
*   Binding
*   Paging3

### API

All the api's has provided by the last.fm website. for more information please visit their website.

### Design Note
Unfortunately the API that I used is not reliable enough. there is no integer unique identifier for albums,
or artists. The mbid field is a string id and as we know its not as good as integers for being used as a Primary Key.
And also you can find lots of cases that even this field is null!
That's why I have used names(Artists, Albums, Tracks) to retrieve and save data in the room database.
Also I loved to write more tests, but considering other projects I'm working on,
I didn't have enough time to complete my test cases.
I hope you enjoy reading my codes and they don't cause you a headache ;)
