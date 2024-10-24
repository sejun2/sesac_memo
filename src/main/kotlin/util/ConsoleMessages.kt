package util

import model.Option

val CONSOLE_MESSAGE_HOME_1 = "📝 MEMO MANAGEMENT CONSOLE PROGRAM 📝"
val CONSOLE_MESSAGE_HOME_2 = "원하는 번호를 입력 후 엔터를 눌러주세요"

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
fun printMessageAndOptions(message: String, list: List<Option>) {
    stringPrinter.printMessage(message)
    val formattedOption = list.mapIndexed { index, option ->
        "${index + 1}. ${option.optionName}"
    }.joinToString(", ")

   return stringPrinter.printMessage(formattedOption)
}

fun printMessageAndOptions(message: String, vararg options: String){
    println(String.format(message, *options))
}

