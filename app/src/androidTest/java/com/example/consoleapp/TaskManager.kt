package com.example.consoleapp

//Defining task data class
data class Task(
    val Id: Int,
    val title: String,
    var taskStatus: TaskStatus = TaskStatus.TODO
)

//Defining status options of tasks
enum class TaskStatus {
    TODO,
    DONE
}

//Defining user interaction options
enum class SelectOptions(val option: String) {
    ADD_TASK("1"),
    COMPLETE_TASK("2"),
    LIST_ALL_COMPLETED_TASKS("3"),
    LIST_ALL_TASKS("4"),
    SEARCH_TASK("5"),
    DELETE_TASK("6"),
    EXIT("7")
}

fun printWelcome() {
    println("\n========================")
    println("   KOTLIN TASK MANAGER  ")
    println("========================")
}

//Defining main source of truth
val taskList = mutableListOf<Task>()

var isRunning = true
var nextIdForTaskList = 1

fun main() {

//Starting the loop for execution
    while (isRunning) {

        if (taskList.isEmpty()) {
            printWelcome()
        }

        println("\nOptions: (1) Add  (2) Complete  (3) List All Completed Tasks  (4) List All the Tasks  (5) Search Task By Name  (6) Delete  (7) Exit")
        print("Choose an option: ")

        when (readLine()) {
            //Adding task
            SelectOptions.ADD_TASK.option -> {
                addTask()
                printTasks()
            }
            //Completing selected task by id
            SelectOptions.COMPLETE_TASK.option -> {
                print("Choose task by ID to complete: ")
                val idInput = readLine()?.toIntOrNull()
                val task = taskList.find { it.Id == idInput }
                if (task != null) {
                    task.taskStatus = TaskStatus.DONE
                    println("${task.Id}.[✓] ${task.title} marked as complete.")
                } else {
                    println("Error: Task with ID '$idInput' not found.")
                }
            }
            //Listing all completed tasks
            SelectOptions.LIST_ALL_COMPLETED_TASKS.option -> {
                println("Listing all finished tasks")
                filterAndPrintCompletedTasks()
            }
            //Listing all tasks with their statuses
            SelectOptions.LIST_ALL_TASKS.option -> {
                println("Listing all the tasks")
                printTasks()
            }
            //Searching task by name
            SelectOptions.SEARCH_TASK.option -> {
                searchTask()
            }
            //Deleting task by Id
            SelectOptions.DELETE_TASK.option -> {
                deleteTask()
            }
            //Exiting
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
    taskList.add(Task(getNewId(), title))
    println("Task added")
}

private fun getNewId(): Int {
    return nextIdForTaskList++
}


private fun printTasks(specificTask: List<Task>? = taskList) {
    if (specificTask != null) {
        for (task in specificTask) {
            val check = if (task.taskStatus == TaskStatus.DONE) "✓" else " "
            println("${task.Id}.[$check] ${task.title}")
        }
    }
}

private fun searchTask() {
    print("Enter title: ")
    val searchedInput = readLine().toString()
    taskList.filter { it.title.contains(searchedInput, ignoreCase = true) }.let {
        if (it.isNotEmpty()) {
            printTasks(it)
        } else (println("There is no task with searched title.."))
    }
}

private fun filterAndPrintCompletedTasks() {
    taskList.filter { it.taskStatus == TaskStatus.DONE }
        .let { completedTasks ->
            if (completedTasks.isEmpty()) {
                println("There is no completed task..")
            } else {
                for (task in completedTasks) {
                    println("${task.Id}.[✓] ${task.title}")
                }
            }
        }
}

private fun deleteTask() {
    print("Pls enter task Id to delete: ")
    val enteredId = readln().toIntOrNull()
    val removed = taskList.removeIf { it.Id == enteredId }
    if (removed) {
        println("$enteredId. task deleted successfully")
        printTasks()
    } else {
        println("Task with ID $enteredId not found.")
    }
}