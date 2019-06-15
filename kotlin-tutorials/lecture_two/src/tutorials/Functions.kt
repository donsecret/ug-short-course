package tutorials

fun main() {
    show("Short Course")
    val numberReturned = doSomeProcess()
    show(numberReturned)

    val result = add(3, 6)
    show(result)
    show(subtract(5, 6))

    add(9,8)
    show(subtract(4,6))

    show(6.isGreaterThan(14))
    show(34543535.convertToString())
}


/*
This is a function that has one parameter
This functions takes in a message and displays it to the console
This function has no return type
 */
fun show(message: Any) {
    println(message)
}

/*
This is a function that has a return type
 */
fun doSomeProcess(): Double {
    return (3 - 5 / 6).toDouble()
//    return ((3.minus(5)).div(6)).toDouble()
}

// Add two numbers
fun add(numberOne: Int, numberTwo: Int) {
    val result = numberOne.plus(numberTwo)
    show(result)
}

fun subtract(numberOne: Int, numberTwo: Int): Int = numberOne.minus(numberTwo)

/*
 * Extension functions
 * These type of functions are used to add additional properties to an existing variable
 * or function
 */

// Compare two number and see which one is greater
fun Int.isGreaterThan(other: Int): Boolean {
    return this > other
}

// Convert any number to a string with quotation around that number
fun Int.convertToString() = "\"$this\""


