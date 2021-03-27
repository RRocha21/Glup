package com.example.glup

class UserHelperClass {

    var name: String
    var username: String
    var email: String
    var phone: String
    var password: String

    constructor()

    constructor(name: String, username: String, email: String, phone: String, password: String) {
        this.name = name
        this.username = username
        this.email = email
        this.phone = phone
        this.password = password
    }

}