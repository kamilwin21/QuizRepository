package com.example.quizappfirebase.RegistrationAndLoginUser.Classes

data class User(
    var uid: String,
    var email: String,
    var password: String,
    var name: String,
    var surname: String,
    var age: String,

    ) {

   //constructor():this(String(), String(), String(), String(), String(), String()){}
   constructor():this("","","","","",""){}

}
