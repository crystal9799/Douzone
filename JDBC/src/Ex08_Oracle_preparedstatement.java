import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import kr.or.kosa.utils.SingletonHelper;

public class Ex08_Oracle_preparedstatement {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = SingletonHelper.getConnection("oracle", "kosa", "1004");
			String sql="select empno, ename from emp where deptno=?"; //where deptno=10
			//where id=? and name=? and job=? >> where id=100 and name='김유신' and job='it'
			// ? 의 의미는 parameter
			
			/*
			Statement stmt = conn.createStatement();
			String sql="select empno , empno , sal , comm from emp";
			ResultSet rs = stmt.executeQuery(sql); //명령실행 >>쿼리문장 >> DB서버에 보내서 실행 
			*/
			
			pstmt = conn.prepareStatement(sql); //미리 컴파일 쿼리를 ....
			//실행 하려는 쿼리는 DB서버가 공유풀(Shared Pool)
			
			Scanner sc = new Scanner(System.in);
			int deptno = Integer.parseInt(sc.nextLine());
			
			//where deptno=?
			pstmt.setInt(1, deptno); // where deptno = 30
			
			rs = pstmt.executeQuery(); //문장이 빠졌다 // parameter 값만 서버에 전달.
			
			//공식같은 로직
			//데이터 1건 or 1건 이상 or 없는 경우
			if(rs.next()) {
				//1건 또는 그 이상
				do {
					System.out.println(rs.getInt(1) + "/" + rs.getString(2));
					
				}while(rs.next());
			}else {
				System.out.println("조회된 데이터가 없습니다.");
			}
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}finally {
			SingletonHelper.close(rs);
			SingletonHelper.close(pstmt);
			//싱글톤은 close() 하지 않아요 (APP 살아 있는 동안 같은 운명)
		}
		
	}

}