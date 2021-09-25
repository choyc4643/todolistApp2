package com.todo.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;

import javax.naming.CompositeName;

import java.io.BufferedReader;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "<새로운 할 일 추가>\n"
				+ "카테고리 입력! \n");
		category = sc.nextLine();

		
		System.out.println("\n" + "제목을 입력! \n");
		title = sc.nextLine();
		if (list.isDuplicate(title)) {
			System.out.printf("할 일이 중복되었습니다.");
			return;
		}
		
		System.out.println("내용을 입력!");
		desc = sc.nextLine();
		
		System.out.println("마감 날짜를 입력!");
		due_date = sc.nextLine();
		
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "<할 일 삭제>\n"
				+ "삭제할 일의 번호를 입력!\n"
				+ "\n");
		
		int index = sc.nextInt();
		
		TodoItem title = l.getTitle(index);
		System.out.println(title +"이 삭제되었습니다.");

		l.deleteItem(index);
		
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

		System.out.println("카테고리 입력");
		String category = sc.nextLine().trim();
		
		System.out.println("새로운 제목을 입력");
		String new_title = sc.nextLine().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("제목이 중복되었습니다.");
			return;
		}
		
		System.out.println("새로운 내용 입력 ");
		String new_description = sc.nextLine().trim();
		
		System.out.println("새로운 마감날짜 입력 ");
		String new_due_date = sc.nextLine().trim();
		
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(category, new_title, new_description, new_due_date);
				l.addItem(t);
				System.out.println("내용이 수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {

		int cnt = 0;
		System.out.println("[오늘의 할 일 목록]");
		
		for (TodoItem item : l.getList()) {
			cnt++;
		}
		System.out.println("모든 할 일, 총 " + cnt + "개" );

		
		
		for (TodoItem item : l.getList()) {
			System.out.println(l.indexOf(item)+1 +". [" + item.getCategory() + "]" +"할 일: " + item.getTitle() + "  내용:  " + item.getDesc() + "  마감날짜:  " + item.getDue_date() );
			}
			
	}
	
	public static void findKeyword(TodoList l) {
		Scanner sc = new Scanner(System.in);

		String keyword;

		System.out.println("keyword를 입력하세요. >");
		keyword = sc.nextLine();

		for (TodoItem item : l.getList()) {
			if(item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {

				System.out.println(l.indexOf(item)+1 +". [" + item.getCategory() + "]" +"할 일: " + item.getTitle() + "  내용:  " + item.getDesc() + "  마감날짜:  " + item.getDue_date() );
			}
		}
	}
	
	
	public static void saveList(TodoList list, String filename) {
		
		
		try {
		Writer w = new FileWriter(filename);
		
		for (TodoItem item : list.getList()) {
			w.write(item.toSaveString());
		}
		
//		w.write(i.toString());

		w.close();
		System.out.println("저장 완료");

		}  catch(IOException e) {
			e.printStackTrace();
		}
	}
	//FileWriter 사용
	
	public static void loadList(TodoList list, String filename) {
		String title = null, desc= null, current_date = null, category = null, due_date = null;

		try {
			
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			String str;
			while((str = reader.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(str,"##");
				category = st.nextToken();
				title = st.nextToken();
				desc = st.nextToken();
				due_date = st.nextToken();
				current_date = st.nextToken();
				TodoItem t = new TodoItem(category, title, desc, due_date);
				list.addItem(t);
			}
			reader.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//BufferedReader, FileReader, StringTokenizer 사용 
}
