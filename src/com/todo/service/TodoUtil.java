package com.todo.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.io.BufferedReader;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "<새로운 할 일 추가>\n"
				+ "제목을 입력! \n");
		
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("할 일이 중복되었습니다.");
			return;
		}
		
		System.out.println("내용을 입력!");
		desc = sc.nextLine();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "<할 일 삭제>\n"
				+ "삭제할 일의 제목을 입력!\n"
				+ "\n");
		String title = sc.next();

		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}

	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "<할 일 수정>\n"
				+ "수정할 할 일의 제목을 입력하세요.\n"
				+ "\n");
		String title = sc.nextLine().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("해당하는 제목이 존재하지 않습니다.");
			return;
		}

		System.out.println("새로운 제목을 입력");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복되었습니다.");
			return;
		}
		
		System.out.println("새로운 내용 입력 ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("내용이 수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[오늘의 할 일 목록]");
		for (TodoItem item : l.getList()) {
			System.out.println("할 일: " + item.getTitle() + "  내용:  " + item.getDesc());
		}
	}
	
	
	public static void saveList(TodoList list, String filename) {
		
//		String str="aaasdfaa", aaa= "bbasdfbb";
		
		try {
		Writer w = new FileWriter(filename);
		
//		TodoItem i = new TodoItem(str,aaa);
//		TodoItem i = new TodoItem(str,aaa);
//		w.write(i.toString());
	


		for (TodoItem item : list.getList()) {
			System.out.println("할 일: 조영찬 바보만들기 ");
			w.write(item.toString());
			System.out.println("할 일: " + item.getTitle() + "  내용:  " + item.getDesc());
		}
		
//		w.write(i.toString());

		w.close();
		
		
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	//FileWriter 사용
	
	public static void loadList(TodoList list, String filename) {
		String title = null, desc= null, current_date = null;

		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			String str;
			while((str = reader.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(str,"##");
				title = st.nextToken();
				System.out.print(title);
				desc = st.nextToken();
				System.out.print(desc);
				current_date = st.nextToken();
				System.out.println(current_date);
				TodoItem t = new TodoItem(title, desc);
				list.addItem(t);
				System.out.println(t);
				System.out.println(list.getList());
				listAll(list);
				
			}
			reader.close();
			System.out.println("가져오기 완료");
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//BufferedReader, FileReader, StringTokenizer 사용 
}
