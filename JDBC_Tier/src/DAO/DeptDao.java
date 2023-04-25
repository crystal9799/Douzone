package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import DTO.Dept;
import UTILS.SingletonHelper;

/*
 1. DB연결
 2. CRUD 함수
 3. 기본 5가지 + 알파 (조건검색 , 문자열 검색)
 3.1 전체조회 , 조건조회 , INSERT , UPDATE , DELETE 함수
 */

public class DeptDao {
	//1. 전체조회 >> select 결과 >> return multi row (Dept 객체 여러개)
	//select deptno , dname , loc from dept
	public List<Dept> getDeptAllList() {
		
		//여러건의 데이터 (Dept 객체 여러개)
		List<Dept> deptlist = new ArrayList<>();
		//deptlist.add(new Dept());
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		//SELELCT = Connection , PreparedStatement , RresultSet
		//DML = Connection , PreparedStatement
		
		try {
			conn = SingletonHelper.getConnection("oracle", "KOSA", "1004");
			String sql = "select deptno , dname , loc from dept";
			pstmt = conn.prepareStatement(sql);
			rs= pstmt.executeQuery();
			
			while(rs.next()) {
				Dept dept = new Dept(); //하나의 row 를 담을 수 있는 DTO 객체 생성
				dept.setDeptno(rs.getInt("deptno"));
				dept.setDname(rs.getString("dname"));
				dept.setDname(rs.getString("loc"));
				deptlist.add(dept); //ArrayList 객체 담기 .... 5건 >> Dept 객체 5개 add
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			SingletonHelper.close(rs);
			SingletonHelper.close(pstmt);
		}
		
		return deptlist;
	}
	
	//2. 조건조회 >> select 결과(where deptno=?) >> return single row (Dept 객체 한개)
	//select deptno , dname , loc from dept where deptno=?
	public Dept getDeptSelect() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Scanner sc = new Scanner(System.in);
		Dept dept= new Dept();
		
		try {
			String sql = "select deptno , dname , loc from dept where deptno=?";
			conn = SingletonHelper.getConnection("oracle", "KOSA", "1004");
			pstmt = conn.prepareStatement(sql);
			int deptno = Integer.parseInt(sc.nextLine());
			pstmt.setInt(1, deptno);
			rs=pstmt.executeQuery();
			
			
			
			while(rs.next()) {
				//Dept dept = new Dept();
				dept.setDeptno(rs.getInt("deptno"));
				dept.setDname(rs.getString("dname"));
				dept.setLoc(rs.getString("loc"));
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			SingletonHelper.close(rs);
			SingletonHelper.close(pstmt);
		}
		return dept;
	}
	
	//3. 데이터 삽입 >> parameter (Dept 객체) >> return 정수 (반영된 행)
	//insert into dept(deptno,dname,loc) values(?,?,?)
	public int insertdata(Dept dept) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row=0;
		try {
			String sql = "insert into dept(deptno,dname,loc) values(?,?,?)";
			conn = SingletonHelper.getConnection("oracle", "KOSA", "1004");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dept.getDeptno());
			pstmt.setString(2, dept.getDname());
			pstmt.setString(3, dept.getLoc());
			
			row = pstmt.executeUpdate();
			if(row>0) {
				System.out.println("insert row : "+row);
			}else {
				System.out.println("변경사항 없음");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return row;
	}
	
	//4. 데이터 수정 >> parameter (Dept 객체) >> return 정수 (반영된 행)
	//update dept set dname=? , loc=? where deptno=?
	public int updatedata(Dept dept){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row=0;
		try {
			String sql = "update dept set dname=? , loc=? where deptno=?";
			conn = SingletonHelper.getConnection("oracle", "KOSA", "1004");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, "IT");
			pstmt.setString(2, "SEOUL");
			pstmt.setInt(3, dept.getDeptno());
			
			row = pstmt.executeUpdate();
			if(row>0) {
				System.out.println("update row : "+row);
			}else {
				System.out.println("변경사항 없음");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return row;
	}
	
	//5. 데이터 삭제 >> parameter (deptno) >> return 정수 (반영된 행)
	//delete from dept where deptno=?
	public int deletedata(int deptno){
		Connection conn = null;
		PreparedStatement pstmt = null;
		int row=0;
		
		try {
			String sql = "delete from dept where deptno=?";
			conn = SingletonHelper.getConnection("oracle", "KOSA", "1004");
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, deptno);
			
			row = pstmt.executeUpdate();
			if(row>0) {
				System.out.println("delete row : "+row);
			}else {
				System.out.println("변경사항 없음");
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return row;
	}
	
}
