package com.example.consoleapp

import com.example.consoleapp.core.TaskManager
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test


private val taskManager = TaskManager()

class TaskManagerTest {
    @BeforeEach
    fun setUp() {
        taskManager.listofAllTasks().forEach { taskManager.deleteTaskById(it.Id) }
    }

    @Test
    fun `addNewTask should add a task to the list`() {
        val task = taskManager.addNewTask("Test Task")
        assertEquals(1, taskManager.listofAllTasks().size)
        assertEquals("Test Task", taskManager.listofAllTasks()[0].title)
    }

    @Test
    fun `completeTaskById should mark a task as done`() {
        val task = taskManager.addNewTask("Test Task")
        taskManager.completeTaskById(task.Id)
        assertTrue(task.taskStatus == TaskStatus.DONE)
    }

    @Test
    fun `deleteTaskById should delete task from the list`() {
        val task = taskManager.addNewTask("Test Task")
        taskManager.deleteTaskById(task.Id)
        assertTrue(taskManager.listofAllTasks().isEmpty())
    }

    @Test
    fun `completed tasks displays only`() {
        val task1 = taskManager.addNewTask("Test Task 1")
        val task2 = taskManager.addNewTask("Test Task 2")
        taskManager.completeTaskById(task1.Id)
        assertTrue (taskManager.listCompletedTasks() == listOf(task1))
        assertFalse (taskManager.listCompletedTasks().contains(task2))
    }

    @Test
    fun `search task should return searched task`() {
        val task = taskManager.addNewTask("Test Task")
        val searchedResult = taskManager.searchTasks(task.title)
        assertTrue(searchedResult.contains(task))
    }

    @Test
    fun `listing all task should return all the tasks`() {
        val task1 = taskManager.addNewTask("Test Task 1")
        val task2 = taskManager.addNewTask("Test Task 2")
        val allTasks = taskManager.listofAllTasks()
        assertTrue(allTasks.contains(task1))
        assertTrue(allTasks.contains(task2))
    }
}