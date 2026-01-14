package com.example.consoleapp

data class Task(
    val taskId: Int,
    val title: String,
)

enum class SelectOptions(val option: String) {
    ADD_TASK("1"),
    COMPLETE_TASK("2"),
    LIST_ALL_COMPLETED_TASKS("3"),
    SEARCH_TASK ("4"),
    LIST_ALL_TASKS("5"),
    EXIT ("6")
}

fun printWelcome() {
    println("\n========================")
    println("   KOTLIN TASK MANAGER  ")
    println("========================")
}

fun main() {
    val taskList = mutableListOf<Task>()
    val finishedTaskList = mutableListOf<Task>()
    val totalTasks = mutableListOf<Task>()
    var isRunning = true
    var nextIdForTaskList = 1
    var nextIdForWholeList = 1

    while (isRunning) {

        if (totalTasks.isEmpty()) {
            printWelcome()
        }

        println("\nOptions: (1) Add  (2) Complete  (3) List All Completed Tasks   (4) Search Task By Name   (5) List All the Tasks   (6) Exit")
        print("Choose an option: ")

        when (readLine()) {

            SelectOptions.ADD_TASK.option -> {
                print("Enter title: ")
                val title = readLine().toString()
                taskList.add(Task(nextIdForTaskList++, title))
                totalTasks.add(Task(nextIdForWholeList++, title))
                println("Task added")
                for (task in taskList) {
                    println("${task.taskId}.[ ] ${task.title}")
                }
            }

            SelectOptions.COMPLETE_TASK.option -> {
                print("Choose task by ID to complete: ")
                var idInput = readLine()?.toIntOrNull()
                var task = taskList.find { it.taskId == idInput }
                taskList.remove(task)
                if (task != null) {
                    finishedTaskList.add(task)
                    println("${task.taskId}. [✓] ${task.title}")
                } else println("Task is not exist")
            }

            SelectOptions.LIST_ALL_COMPLETED_TASKS.option -> {
                println("Listing all finished tasks")
                if (finishedTaskList.isEmpty()) {
                    println("\nNo completed tasks.")
                } else {
                    for (task in finishedTaskList) {
                        println("${task.taskId}.[✓] ${task.title}")
                    }
                }

            }

            SelectOptions.SEARCH_TASK.option -> {
                print("Enter title: ")
                val searchedInput = readLine()
                var result = (totalTasks).find { it.title == searchedInput.toString() }
                if (result in (totalTasks)) {
                    if (result in taskList) {
                        println("${result?.taskId}. [ ] ${result?.title}")
                    } else if (result in finishedTaskList) {
                        println("${result?.taskId}. [✓] ${result?.title}")
                    } else {
                        println("There is no task with given name..")
                    }
                }
            }

            SelectOptions.LIST_ALL_TASKS.option -> {
                println("Listing all the tasks")
                for (task in totalTasks) {
                    if (task in taskList) {
                        println("${task.taskId} [ ] ${task.title}")
                    } else if (task in finishedTaskList) {
                        println("${task.taskId} [✓] ${task.title}")
                    }
                }
            }

            SelectOptions.EXIT.option -> {
                println("Exiting..")
                isRunning = false
            }
        }
    }
}

