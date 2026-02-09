package com.example.consoleapp.core

import com.example.consoleapp.Task
import com.example.consoleapp.TaskStatus

class TaskManager() {

    private val tasks = mutableListOf<Task>()

    fun defaultIdProvider(): Int {
        var next = 1
        return  next++
    }

    fun addNewTask(title: String): Task {
        val task = Task(defaultIdProvider(), title)
        tasks.add(task)
        return task
    }

    fun completeTaskById(id: Int): Boolean {
        val task = tasks.find { it.Id == id }
        return if (task != null) {
            task.taskStatus = TaskStatus.DONE
            true
        } else false
    }

    fun deleteTaskById(id: Int): Boolean {
        return tasks.removeIf { it.Id == id }
    }

    fun searchTasks(query: String): List<Task> {
        return tasks.filter { it.title.contains(query, ignoreCase = true) }
    }

    fun listofAllTasks(): List<Task> = tasks.toList()

    fun listCompletedTasks(): List<Task> = tasks.filter { it.taskStatus == TaskStatus.DONE }
}
