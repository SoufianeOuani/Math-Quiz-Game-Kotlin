package com.example.problemsolvinglevel01_kotlin

import kotlin.random.Random

enum class EnOperationType(val value: Int)
{
    Add(1),Subtract(2),Multiply(3),Divide(4), Mix(5)
}

enum class EnQuestionLevel(val value: Int)
{
    Easy(1), Medium(2), Hard(3), MixLevel(4)
}

data class StQuestion(
    var number1:Int = 0,
    var number2: Int = 0,
    var operationType: EnOperationType = EnOperationType.Add,
    var questionLevel: EnQuestionLevel = EnQuestionLevel.Easy,
    var playerAnswer: Float = 0f,
    var correctAnswer: Float = 0f,
    var answerResult: Boolean = false
)

data class StExam(
    var questionsList : MutableList< StQuestion> = MutableList(100) { StQuestion() },
    var numberOFQuestions: Int = 0,
    var opType: EnOperationType = EnOperationType.Add,
    var questionLevel: EnQuestionLevel = EnQuestionLevel.Easy,
    var numberOFRightQuestions: Int = 0,
    var numberOfWrongQuestions: Int = 0,
    var isPass: Boolean = false
)

fun randomNumberGenerator(from: Int, to: Int):Int =
    Random.nextInt(from, to+1)

fun readHowManyQuestions(message:String, from:Int, to:Int):Int
{
    var numberOFQuestions = 0

    do {
        print(message)
        numberOFQuestions = readLine()?.toIntOrNull() ?: 3
    }while (numberOFQuestions < from || numberOFQuestions > to)
    return numberOFQuestions
}

fun readQuestionLevel(message:String, from:Int, to:Int): EnQuestionLevel
{
    var questionLevel : Int

    do
    {
        print(message)
        questionLevel = readln()?.toIntOrNull() ?: 0
    }while (questionLevel !in from..to)

    return EnQuestionLevel.entries.find { it.value == questionLevel } ?: EnQuestionLevel.Easy
}

fun readOperationType(message: String, from:Int, to:Int): EnOperationType
{
    var operationType: Int

    do
    {
        print(message)
        operationType = readLine()?.toIntOrNull() ?: 1
    }while (operationType !in from..to)

    return EnOperationType.entries.find { it.value == operationType} ?: EnOperationType.Add
}

fun simpleCalculator(number1: Int, number2: Int, opType: EnOperationType): Float = when(opType)
{
    EnOperationType.Add -> ( number1 + number2).toFloat()
    EnOperationType.Subtract -> (number1 - number2).toFloat()
    EnOperationType.Multiply -> (number1 * number2).toFloat()
    EnOperationType.Divide -> (number1 / number2).toFloat()
    else -> (number1 + number2).toFloat()
}

fun getOperationTypeSymbol(opType: EnOperationType):String = when(opType)
{
    EnOperationType.Add -> "+"
    EnOperationType.Subtract -> "-"
    EnOperationType.Multiply -> "*"
    EnOperationType.Divide -> "/"
    else -> "Mix"

}

fun getQuestionLevelText(questionLevel: EnQuestionLevel): String = when(questionLevel)
{
    EnQuestionLevel.Easy -> "Easy"
    EnQuestionLevel.Medium -> "Medium"
    EnQuestionLevel.Hard -> "Hard"
    EnQuestionLevel.MixLevel -> "Mix"
}

fun getFinalResultsText(isPass: Boolean):String =
    if (isPass) "PASS :-)" else "FAIL :-("

fun getRandomOperationType(): EnOperationType
{
    return EnOperationType.entries[randomNumberGenerator(0, 3)]
}

fun generateQuestion(opType: EnOperationType, qLevel: EnQuestionLevel): StQuestion
{
    var questionLevel = qLevel
    var operationType = opType
    val question = StQuestion()


    when(questionLevel)
    {
        EnQuestionLevel.Easy -> {
            question.number1 = randomNumberGenerator(1,10)
            question.number2 = randomNumberGenerator(1,10)
        }

        EnQuestionLevel.Medium -> {
            question.number1 = randomNumberGenerator(10,50)
            question.number2 = randomNumberGenerator(10,50)
        }
        EnQuestionLevel.Hard -> {
            question.number1 = randomNumberGenerator(50,100)
            question.number2 = randomNumberGenerator(50,100)
        }
        EnQuestionLevel.MixLevel -> {
            questionLevel = EnQuestionLevel.entries[randomNumberGenerator(0,2)]
            operationType = getRandomOperationType()

            question.number1 = randomNumberGenerator(1,100)
            question.number2 = randomNumberGenerator(1,100)
        }
    }

    question.correctAnswer = simpleCalculator(question.number1, question.number2, question.operationType)
    return question
}

fun generateQuestionList(exam: StExam)
{
    for (questionNumber in 0 until exam.numberOFQuestions)
    {
        exam.questionsList[questionNumber] = generateQuestion(exam.opType, exam.questionLevel)
    }
}

fun printQuestion(exam: StExam, questionNumber: Int)
{
    print("Question [${questionNumber + 1}/${exam.numberOFQuestions}]\n\n")
    val question = exam.questionsList[questionNumber]
    println("${question.number1} ${getOperationTypeSymbol(question.operationType)} ${question.number2}")
    println("____________\n")
    println("= \n")
}

fun readPlayerAnswer(): Float = readln().toFloatOrNull() ?: 0f

fun correctAnswer(exam: StExam, questionNumber: Int)
{
    val question = exam.questionsList[questionNumber]
    if (question.playerAnswer != question.correctAnswer)
    {
        exam.numberOfWrongQuestions++
        question.answerResult = false

        println("\nWrong answer :-(")
        print("The correct answer is: ")
        print(question.correctAnswer)
    }
    else
    {
        exam.numberOFRightQuestions++
        question.answerResult = true

        println("\nRight answer")
    }
    println()
}

fun askAndCorrectQuestionAnswer(exam: StExam)
{
    for (questionNumber in 0 until exam.numberOFQuestions)
    {
        printQuestion(exam, questionNumber)
        exam.questionsList[questionNumber].playerAnswer = readPlayerAnswer()
        correctAnswer(exam, questionNumber)
    }

    exam.isPass = (exam.numberOFRightQuestions >= exam.numberOfWrongQuestions)
}

fun printFinalResults(exam: StExam)
{
    println("\n____________________________________\n")
    println("\tResult is : ${getFinalResultsText(exam.isPass)}")
    println("____________________________________\n")
    println("  Number of Questions     : ${exam.numberOFQuestions}")
    println("  Question Level          : ${getQuestionLevelText(exam.questionLevel)}")
    println("  Operation Type          : ${getOperationTypeSymbol(exam.opType)}")
    println("  Number of right answers : ${exam.numberOFRightQuestions}")
    println("  Number of wrong answers : ${exam.numberOfWrongQuestions}")
    println("____________________________________\n")
}

fun playMathGame()
{
    val exam = StExam()

    exam.numberOFQuestions = readHowManyQuestions("Please enter how many questions? ", 3, 10)
    exam.questionLevel = readQuestionLevel("Enter question level [1]:EASY, [2]:MEDIUM, [3]:HARD, [4]:Mix ? ", 1, 4)
    exam.opType = readOperationType("Enter an operation type [1]:Add, [2]:Subtract, [3]:Multiply, [4]:Divide, [5]:Mix ? ",1,5)

    generateQuestionList(exam)
    askAndCorrectQuestionAnswer(exam)
    printFinalResults(exam)
}

fun startMathGame()
{
    var playAgain = 'Y'

    do
    {
        playMathGame()

        print("Do you want to play again (Y/N)? ")
        playAgain = readln()?.uppercase()?.firstOrNull() ?: 'n'

    }while (playAgain == 'Y')
}

fun main()
{
    startMathGame()
}
