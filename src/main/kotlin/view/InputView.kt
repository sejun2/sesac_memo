package view

import model.Option

object ConsoleIo {
    fun printMessage(message: String) {
        println(message)
    }
    fun input() = readLine()
}


/*
* @params: message: String, list: List<Option>
* 우선 파라미터로 받은 메세지를 출력하고
* 옵션들이 담긴 리스트를 mapIndexed로 순회하면서
* 1(index + 1). option.name 2. option.name 3. option.name으로 출력하게 한다
* 이 때 joinToString을 사용해 쉼표로 나누어주고 출력함
* */
fun<T> optionFormatter (list: List<Option<T>>): String {
    val formattedOption = list.mapIndexed { index, option ->
        "${index + 1}. ${option.name}"
    }.joinToString(", ")
    return formattedOption
}

fun<T>printMessageAndOptions(list: List<Option<T>>, otherOptions: String) {
    val formattedOption = optionFormatter(list)
    return ConsoleIo.printMessage(formattedOption + otherOptions)
}

fun<T>printMessageAndOptions(message: String, list: List<Option<T>>) {
    val formattedOption = optionFormatter(list)
    return ConsoleIo.printMessage(message + formattedOption)
}
fun<T>printMessageAndOptions(list: List<Option<T>>) {
    val formattedOption = optionFormatter(list)
    return ConsoleIo.printMessage(formattedOption)
}

fun printMessageAndOptions(message: String, vararg options: String){
    ConsoleIo.printMessage(message.format(*options))
}