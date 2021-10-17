package com.todo.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

		String title, desc, category, due_date, withWhom;
		int importance;
		Scanner sc = new Scanner(System.in);

		System.out.println("\n" + "<새로운 할 일 추가>\n" + "카테고리 입력! \n");
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

		System.out.println("새로운 일정 공유 인원 입력 ");
		withWhom = sc.nextLine();
		
		System.out.println("중요도를 입력!");
		importance = sc.nextInt();
		
		TodoItem t = new TodoItem(category, title, desc, due_date, withWhom, importance, 0);
		if (list.addItem(t) > 0)
			System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);

		System.out.print("[항목삭제]\n" + "삭제할 항목의 번호들을 쉼표(,)로 구분하여 입력하시오 (최대 10개) > ");
		String str = sc.nextLine();
		String[] tokens = str.split(",");
		int[] index = new int[10];
		for (int i = 0; i < tokens.length; i++) {
			index[i] = Integer.parseInt(tokens[i]);
			if (l.deleteItem(index[i]) > 0)
				System.out.println("삭제되었습니다.");
		}

	}

	public static void updateItem(TodoList l) {

		Scanner sc = new Scanner(System.in);

		System.out.println("\n" + "<할 일 수정>\n" + "수정할 할 일의 번호를 입력하세요.\n" + "\n");
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

		System.out.println("새로운 일정 공유 인원 입력 ");
		String new_withWhom = sc.nextLine().trim();

		System.out.println("중요도를 입력!");
		int new_importance = sc.nextInt();
		
		TodoItem t = new TodoItem(category, new_title, new_description, new_due_date, new_withWhom, new_importance, 0);

		t.setId(index);
		if (l.updateItem(t) > 0)
			System.out.println("내용이 수정되었습니다.");

	}

	public static void listAll(TodoList l) {
		System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[전체 목록, 총 %d개\n", l.getCount());
		System.out.println("아이디 - 카테고리 - 제목 - 내용 - 함께하는 사람 - 마감일 - 추가시각 - 중요도 - 완료여부(1/0)");
		for (TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}

	public static void findKeyword(TodoList l) {
		Scanner sc = new Scanner(System.in);

		String keyword;

		System.out.println("keyword를 입력하세요. >");
		keyword = sc.nextLine();

		for (TodoItem item : l.getList()) {
			if (item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {

				System.out.println(l.indexOf(item) + 1 + ". [" + item.getCategory() + "]" + "할 일: " + item.getTitle()
						+ "  내용:  " + item.getDesc() + "  마감날짜:  " + item.getDue_date());
			}
		}
	}

	public static void findCate(TodoList l) {
		Scanner sc = new Scanner(System.in);

		String keyword;

		System.out.println("keyword를 입력하세요. >");
		keyword = sc.nextLine();

		for (TodoItem item : l.getList()) {
			if (item.getCategory().contains(keyword)) {
				System.out.println(l.indexOf(item) + 1 + ". [" + item.getCategory() + "]" + "할 일: " + item.getTitle()
						+ "  내용:  " + item.getDesc() + "  마감날짜:  " + item.getDue_date());
			}
		}
	}

	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public static void ls_cate(TodoList l) {

		String keyword;
		int cnt = 0;

		for (TodoItem k : l.getList()) {
			keyword = k.getCategory();
			cnt = 0;

			for (TodoItem item : l.getList()) {
				if (keyword.equals(item.getCategory())) {
					cnt++;
					System.out.print(cnt);
				}
			}

			if (cnt == 1) {
				System.out.print(k.getCategory() + " ");
			}
		}
	}

	public static void listCateAll(TodoList l) {
		int count = 0;

		for (String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}


	
	public static void completeItem(TodoList l) {
			System.out.printf("[전체 목록, 총 %d개]\n", l.getCount());
			System.out.println("아이디 - 카테고리 - 제목 - 내용 - 함께하는 사람 - 마감일 - 추가시각 - 중요도 - 완료여부(1/0)");
			for (TodoItem item : l.getList()) {
				if(item.getIs_completed()==1)
				System.out.println(item.toString());
			}
	}
	
	public static void complete(TodoList l) {
		Scanner sc = new Scanner(System.in);

		System.out.println("완료된 할 일의 번호를 쉼표(,)로 구분하여 입력하세요. (최대 10개) >");
		String str = sc.nextLine();
		String[] tokens = str.split(",");
		int[] index = new int[10];
		for (int i = 0; i < tokens.length; i++) {
			index[i] = Integer.parseInt(tokens[i]);
			if (l.toCompleted(index[i]) > 0)
				System.out.println(index[i] + "번의 내용이 수정되었습니다.");
		}
		
	}
	
	public static void orderedList(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.println("정렬 기준을 입력해주세요.\n(id, category, title, memo, due_date, current_date, withWhom, importance, is_completed)");
		String str = sc.nextLine();
		System.out.println("정렬 순서를 입력해주세요. (1/0)");
		int ordering = sc.nextInt();
		listAll(l, str, ordering);
	}
	// 정렬기준과 순서를 입력받아 listAll 을 실행시키는 함수
	
	
	public static void importData(String filename) {
		Connection conn = DbConnect.getConnection();

		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			String line;
			String sql = "insert into list2 (category, title, memo, due_date, current_date, withWhom, importance, is_completed)" + " values (?,?,?,?,?,?,?,?)";

			int records = 0;
			while ((line = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(line, ",");
				String category = st.nextToken();
				String title = st.nextToken();
				String description = st.nextToken();
				String due_date = st.nextToken();
				String current_date = st.nextToken();
				String withWhom = st.nextToken();
				int importance = Integer.parseInt(st.nextToken());
				int is_completed = Integer.parseInt(st.nextToken());


				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, category);
				pstmt.setString(2, title);
				pstmt.setString(3, description);
				pstmt.setString(4, due_date);
				pstmt.setString(5, current_date);
				pstmt.setString(6, withWhom);
				pstmt.setInt(7, importance);
				pstmt.setInt(8, is_completed);
				
				int count = pstmt.executeUpdate();
				if (count > 0)
					records++;
				pstmt.close();
			}
			System.out.println(records + " records read !!");
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

}