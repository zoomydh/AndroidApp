package com.example.part11_33

class PropertyTest {
    var greeting: String="Hello"
        set(value) {
            field = field + value
        }
        get() = field.uppercase()
    val name: String = "Yoon"
        get () = field.uppercase()
}