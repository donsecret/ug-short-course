package tutorials

import java.util.*


// Global variable
var age = 5

fun main() {
    // Conditional statements
    // we use if..else..
    simpleCondition()
    complexCondition()
}

// A more complex if..else if.. else statement
fun complexCondition() {
    println("Hey buddy! How young are you?")    // Show prompt message for user
    age = Scanner(System.`in`).nextInt()    // Save user's input into the age variable

    val youngAge = 20
    val oldAge = 65

    /*if (age > youngAge) {
        println("So you are $age years old!") // Display user's age to the console
    } else if (age < youngAge) {
        println("So you are too young for this app. Call daddy first") // Display user's age to the console
    } else if (age > oldAge) {
        println("This app is for kids ,old man!") // Display user's age to the console
    } else {
        println("It's like we don't know what to tell you anymore. Please try again with a different age") // Display user's age to the console
    }*/

    if (age >= youngAge && age < oldAge) {
        println("You are a young adult")
    } else if (age < youngAge) {
        println("You are still a teenager")
    } else if (age >= oldAge) {
        println("you are an old person")
    } else {
        println("We cannot find your age range: $age")
    }

}

fun simpleCondition() {
    if (age > 9) {
        println("$age is actually greater than 9")
    } else {
        println("What were you thinking! How can $age be greater than 9")
    }
}
