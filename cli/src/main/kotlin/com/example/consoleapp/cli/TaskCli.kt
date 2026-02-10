package com.example.consoleapp.cli

import com.example.consoleapp.SelectOptions
import com.example.consoleapp.core.TaskManager
import com.example.consoleapp.TaskStatus
import java.io.File
import java.sql.Time
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val taskManager = TaskManager()
private var isRunning = true

fun printWelcome() {
    println("\n========================")
    println("   KOTLIN TASK MANAGER  ")
    println("========================")
}

fun main() {
    while (isRunning) {
        if (taskManager.listofAllTasks().isEmpty()) {
            printWelcome()
        }

        println("\nOptions: (1) Add  (2) Complete  (3) List All Completed Tasks  (4) List All the Tasks  (5) Search Task By Name  (6) Delete  (7) Save All Tasks  (8) Exit")
        print("Choose an option: ")

        when (readLine()) {
            SelectOptions.ADD_TASK.option -> addTask()
            SelectOptions.COMPLETE_TASK.option -> completingSelectedTask()
            SelectOptions.LIST_ALL_COMPLETED_TASKS.option -> {
                filterAndPrintCompletedTasks()
            }
            SelectOptions.LIST_ALL_TASKS.option -> {
                println("Listing all the tasks")
                printTasks()
            }
            SelectOptions.SEARCH_TASK.option -> searchTask()
            SelectOptions.DELETE_TASK.option -> deleteTask()
            SelectOptions.SAVE_TASKS.option -> saveFile()
            SelectOptions.EXIT.option -> {
                println("Exiting..")
                isRunning = false
            }
        }
    }
}

private fun addTask() {
    print("Enter title: ")
    val title = readLine() ?: ""
    taskManager.addNewTask(title)
    println("Task added")
    printTasks()
}

private fun completingSelectedTask() {
    print("Choose task by ID to complete: ")
    val idInput = readLine()?.toIntOrNull()
    if (idInput == null) {
        println("Invalid id")
        return
    }

    val success = taskManager.completeTaskById(idInput)
    if (success) {
        println("${idInput}.[✓] marked as complete.")
    } else {
        println("Error: Task with ID '$idInput' not found.")
    }
}

private fun printTasks() {
    val tasks = taskManager.listofAllTasks()
    for (task in tasks) {
        val check = if (task.taskStatus == TaskStatus.DONE) "✓" else " "
        println("${task.Id}.[$check] ${task.title}")
    }
}

private fun searchTask() {
    print("Enter title: ")
    val searchedInput = readLine().toString()
    val found = taskManager.searchTasks(searchedInput)
    if (found.isNotEmpty()) {
        for (task in found) {
            val check = if (task.taskStatus == TaskStatus.DONE) "✓" else " "
            println("${task.Id}.[$check] ${task.title}")
        }
    } else {
        println("There is no task with searched title..")
    }
}

private fun filterAndPrintCompletedTasks() {
    val completed = taskManager.listCompletedTasks()
    if (completed.isEmpty()) {
        println("There is no completed task..")
    } else {
        for (task in completed) {
            println("Listing all the completed tasks:")
            println("${task.Id}.[✓] ${task.title}")
        }
    }
}

private fun deleteTask() {
    print("Pls enter task Id to delete: ")
    val enteredId = readLine()?.toIntOrNull()
    if (enteredId == null) {
        println("Invalid ID")
        return
    }
    val removed = taskManager.deleteTaskById(enteredId)
    if (removed) {
        println("$enteredId. task deleted successfully")
        printTasks()
    } else {
        println("Task with ID $enteredId not found.")
    }
}

private fun saveFile() {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val currentDateTime = LocalDateTime.now().format(formatter)
    val file = File("task_data_{$currentDateTime}.txt")
    val content = StringBuilder()
    for (task in taskManager.listofAllTasks()) {
        content.appendLine("${task.Id}. ${task.title} | ${task.taskStatus}")
    }
    file.writeText(content.toString())
    println("${taskManager.listofAllTasks().size} number of tasks saved to ${file.name}")
}
