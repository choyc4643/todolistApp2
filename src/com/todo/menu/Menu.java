package com.todo.menu;

import java.util.Scanner;
import com.todo.dao.TodoList;
import com.todo.service.TodoUtil;

public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("1. 오늘의 할 일 추가! ( add )");
        System.out.println("2. 오늘의 할 일 제거! ( del )");
        System.out.println("3. 오늘의 할 일 수정!  ( edit )");
        System.out.println("4. 오늘의 할 일 보기! ( ls )");
        System.out.println("5. 오늘의 할 일 정렬! [이름순] ( ls_name_asc )");
        System.out.println("6. 오늘의 할 일 정렬! [이름역순] ( ls_name_desc )");
        System.out.println("7. 오늘의 할 일 정렬! [날짜순] ( ls_date )");
        System.out.println("8. keyword 찾기! ( find )");
        System.out.println("9. 나가기 (exit or press escape key)");
    }
    
    public static void prompt(TodoList l) {
    	
		Scanner sc = new Scanner(System.in);
//		l = new TodoList();
		boolean isList = false;
		boolean quit = false;
    	do {
	        System.out.println("명령어를 입력하세요. >");

			isList = false;
			String choice = sc.next();

			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;

			case "ls_name_asc":
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				isList = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				break;
			
			case "find":
				TodoUtil.findKeyword(l);
				break;

			default:
				System.out.println("정확한 명령어를 입력해주세요.");
				break;
			}
			
			if(isList) l.listAll();
		} while (!quit);
    }
}
