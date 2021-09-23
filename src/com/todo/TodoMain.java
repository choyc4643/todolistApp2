package com.todo;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {

		TodoList l = new TodoList();
		TodoList x = new TodoList();

		TodoUtil.loadList(x,"todolist.txt");

		Menu.displaymenu();
		Menu.prompt(x);
		TodoUtil.listAll(x);
		TodoUtil.saveList(x, "todolist.txt");
	}
}
