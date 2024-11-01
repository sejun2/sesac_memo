package view

import model.Option

    fun printMessage(message: String) {
        println(message)
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
    return printMessage(formattedOption + otherOptions)
}

fun<T>printMessageAndOptions(message: String, list: List<Option<T>>) {
    val formattedOption = optionFormatter(list)
    return printMessage(message + formattedOption)
}
fun<T>printMessageAndOptions(list: List<Option<T>>) {
    val formattedOption = optionFormatter(list)
    return printMessage(formattedOption)
}

fun printMessageAndOptions(message: String, vararg options: String){
    printMessage(message.format(*options))
}

// input으로 들어오는 값이 null 혹은 빈문자열 혹은 공백인지 확인. Boolean을 리턴함
fun inputChecker(input: String?) = input.isNullOrBlank()

/* input을 받고 inputChecker로 null인지 빈문자열인지 확인함
* true면(null이거나 빈문자열) null리턴
* false면 제대로 입력된 값이므로 input값 리턴
*/

fun input(): String? {
    val input = readln().trim()

    return when(inputChecker(input)) {
        true -> {
            printMessageAndOptions(CONSOLE_MESSAGE_WRONG_INPUT)
            null
        }
        else -> input
    }

}