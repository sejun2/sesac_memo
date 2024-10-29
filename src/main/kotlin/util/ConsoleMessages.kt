package util

import model.Option


object stringPrinter {
    fun printMessage(message: String) {
        println(message)
    }
}


/*
* @params: message: String, list: List<Option>
* 우선 파라미터로 받은 메세지를 출력하고
* 옵션들이 담긴 리스트를 mapIndexed로 순회하면서
* 1(index + 1). option.optionName 2. option.optionName 3. option.optionName으로 출력하게 한다
* 이 때 joinToString을 사용해 쉼표로 나누어주고 출력함
* */
fun<T>printMessageAndOptions(message: String, list: List<Option<T>>) {
    stringPrinter.printMessage(message)
    val formattedOption = list.mapIndexed { index, option ->
        "${index + 1}. ${option.name}"
    }.joinToString(", ")

   return stringPrinter.printMessage(formattedOption)
}

fun printMessageAndOptions(message: String, vararg options: String){
    println(String.format(message, *options))
}

