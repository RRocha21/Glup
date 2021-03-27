package com.example.glup

class UserHelperClass {

    val name: String
    val username: String
    val email: String
    val phone: String
    val password: String

    constructor()

    constructor(name: String, username: String, email: String, phone: String, password: String) {
        this.name = name
        this.username = username
        this.email = email
        this.phone = phone
        this.password = password
    }

}