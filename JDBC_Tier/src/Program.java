import DAO.DeptDao;
import DTO.*;

public class Program {

	public static void main(String[] args) {
		
		
//		Dept dept = new Dept();
//		dept.setDeptno(100);
//		dept.setDname("IT");
//		dept.setLoc("SEOUL");
//		System.out.println(dept.toString());
//		
//		Emp emp = new Emp(200, "김유신");
//		System.out.println(emp.toString());
		
		DeptDao dd = new DeptDao();
		//System.out.println(dd.getDeptAllList().toString());
//		System.out.println(dd.getDeptSelect().toString());;
		
//		dd.insertdata(new Dept(200,"IT","BUSAN"));
//		dd.insertdata(new Dept(300,"IT","BUSAN"));
		dd.insertdata(new Dept(400,"IT","BUSAN"));
//		dd.updatedata(new Dept(200,"SELLER","SEOUL"));
		dd.deletedata(400);

	}
}
