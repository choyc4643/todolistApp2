package com.todo.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import javax.naming.CompositeName;
import java.io.BufferedReader;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;
import com.todo.service.DbConnect;


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
		
		
		TodoItem t = new TodoItem(title, desc, category, due_date);
		if(list.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[항목삭제]\n"
				+ "삭제할 항복의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		if(l.deleteItem(index)>0)
			System.out.println("삭제되었습니다.");
	}
	
	
//	public static void deleteItem(TodoList l) {
//		
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("\n"
//				+ "<할 일 삭제>\n"
//				+ "삭제할 일의 번호를 입력!\n"
//				+ "\n");
//		
//		int index = sc.nextInt();
//		
//		TodoItem title = l.getTitle(index-1);
//		System.out.println(title +"이 삭제되었습니다.");
//
//		l.deleteItem(index-1);
//		
//	}
	
	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n"
				+ "<할 일 수정>\n"
				+ "수정할 할 일의 번호를 입력하세요.\n"
				+ "\n");
		int index = sc.nextInt();


		String str = sc.nextLine();

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
		
		TodoItem t = new TodoItem(category, new_title, new_description, new_due_date);
				
		t.setId(index);
		if(l.updateItem(t) > 0)
				System.out.println("내용이 수정되었습니다.");

	}


	
	
//	public static void updateItem(TodoList l) {
//		
//		Scanner sc = new Scanner(System.in);
//		
//		System.out.println("\n"
//				+ "<할 일 수정>\n"
//				+ "수정할 할 일의 번호를 입력하세요.\n"
//				+ "\n");
//		int index = sc.nextInt();
//
//
//		String str = sc.nextLine();
//
//		System.out.println("카테고리 입력");
//		String category = sc.nextLine().trim();
//		
//		System.out.println("새로운 제목을 입력");
//		String new_title = sc.nextLine().trim();
//		if (l.isDuplicate(new_title)) {
//			System.out.println("제목이 중복되었습니다.");
//			return;
//		}
//		
//		System.out.println("새로운 내용 입력 ");
//		String new_description = sc.nextLine().trim();
//		
//		System.out.println("새로운 마감날짜 입력 ");
//		String new_due_date = sc.nextLine().trim();
//		
//				l.deleteItem(index-1);
//				TodoItem t = new TodoItem(category, new_title, new_description, new_due_date);
//				l.addItem(t);
//				System.out.println("내용이 수정되었습니다.");
//
//	}

//	public static void listAll(TodoList l) {
//
//		int cnt = 0;
//		System.out.println("[오늘의 할 일 목록]");
//		
//		for (TodoItem item : l.getList()) {
//			cnt++;
//		}
//		System.out.println("모든 할 일, 총 " + cnt + "개" );
//
//		
//		
//		for (TodoItem item : l.getList()) {
//			System.out.println(l.indexOf(item)+1 +". [" + item.getCategory() + "]" +"할 일: " + item.getTitle() + "  내용:  " + item.getDesc() + "  마감날짜:  " + item.getDue_date() );
//			}
//			
//	}
	
	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for(TodoItem item : l.getList()) {
			System.out.println(item.toString());
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
	
	public static void findCate(TodoList l) {
		Scanner sc = new Scanner(System.in);

		String keyword;

		System.out.println("keyword를 입력하세요. >");
		keyword = sc.nextLine();

		for (TodoItem item : l.getList()) {
			if(item.getCategory().contains(keyword)) {

				System.out.println(l.indexOf(item)+1 +". [" + item.getCategory() + "]" +"할 일: " + item.getTitle() + "  내용:  " + item.getDesc() + "  마감날짜:  " + item.getDue_date() );
			}
		}
	}
	
	public static void findCateList (TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count ++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n",count);
	}
	
	
	
	public static void findList(TodoList l, String keyword) {
		int count=0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}
	
	public static void ls_cate(TodoList l) {

		String keyword;
		int cnt=0;

		for (TodoItem k : l.getList()) {
			keyword = k.getCategory();
			cnt=0;
			
			for (TodoItem item : l.getList()) {
				
				if(keyword.equals(item.getCategory())) {
					cnt ++;
					System.out.print(cnt);

				}


		
			}
			if(cnt ==1) {
				System.out.print(k.getCategory() + " ");
			}
		
		}
		
		
		
		
		
//		cate.equals(item.getCategory())
}
	
	public static void listCateAll(TodoList l) {
		int count =0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count ++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
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
	
//	public static void loadList(TodoList list, String filename) {
//		String title = null, desc= null, current_date = null, category = null, due_date = null;
//
//		try {
//			
//			BufferedReader reader = new BufferedReader(new FileReader(filename));
//			String str;
//			while((str = reader.readLine()) != null) {
//				StringTokenizer st = new StringTokenizer(str,"##");
//				category = st.nextToken();
//				title = st.nextToken();
//				desc = st.nextToken();
//				due_date = st.nextToken();
//				current_date = st.nextToken();
//				TodoItem t = new TodoItem(category, title, desc, due_date);
//				list.addItem(t);
//			}
//			reader.close();
//		} catch(FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	//BufferedReader, FileReader, StringTokenizer 사용 
	
	public static void importData(String filename) {
		Connection conn = DbConnect.getConnection();

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list (category, title, memo, current_date, due_date)"
						+  " values (?,?,?,?,?)";
			int records = 0;
			while((line = br.readLine())!= null) {
				StringTokenizer st = new StringTokenizer(line, "##");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, description);
				pstmt.setString(3, category);
				pstmt.setString(4, current_date);
				pstmt.setString(5, due_date);
				int count = pstmt.executeUpdate();
				if(count >0) records ++;
				pstmt.close();
			}
			System.out.println(records + " records read !!");
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	}