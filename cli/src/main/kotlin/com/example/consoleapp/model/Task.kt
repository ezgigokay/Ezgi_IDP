package com.example.consoleapp

data class Task(
    val Id: Int,
    var title: String,
    var taskStatus: TaskStatus = TaskStatus.TODO
)

enum class TaskStatus {
    TODO,
    DONE
}

enum class SelectOptions(val option: String) {
    ADD_TASK("1"),
    COMPLETE_TASK("2"),
    LIST_ALL_COMPLETED_TASKS("3"),
    LIST_ALL_TASKS("4"),
    SEARCH_TASK("5"),
    DELETE_TASK("6"),
    SAVE_TASKS("7"),
    EXIT("8")
}
