import java.sql.Connection;
import java.sql.PreparedStatement;

import kr.or.kosa.utils.SingletonHelper;

public class Ex10_Oracle_Transaction {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		
		conn = SingletonHelper.getConnection("oracle", "kosa", "1004");
		
		String  sql="insert into trans_C(num,name) values(100,'C')";
		String sql2="insert into trans_B(num,name) values(100,'B')";
		
		try {
			//업무상 (둘다 성공 아니면 둘다 실패) => 하나의 논리적인 단위
			conn.setAutoCommit(false); //JDBC 처리 개발자가 하겠다 (commit , rollback)
			//begin tran
				pstmt=conn.prepareStatement(sql);
				pstmt2=conn.prepareStatement(sql2);
				
				pstmt.executeUpdate();  //insert
				pstmt2.executeUpdate(); //insert
			//예외가 발생하지 않으면
				conn.commit(); //둘다 정상적으로 실행되고 예외가 발생되지 않으면 : commit
			//end tran
		} catch (Exception e) {
			//예외가 발생하면 : rollback
			System.out.println("문제발생 : " + e.getMessage());
			try {
				conn.rollback();//둘다 취소
			} catch (Exception e2) {
				System.out.println(e2.getMessage());
			}finally {
				SingletonHelper.close(pstmt);
				SingletonHelper.close(pstmt2);
			}
		}
	}

}
