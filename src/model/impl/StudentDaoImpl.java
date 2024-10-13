package model.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.dao.StudentDao;
import model.vo.StudentVO;

public class StudentDaoImpl implements StudentDao{

	private static final String DBDRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DBURL	 = "jdbc:oracle:thin:@192.168.0.14:1521:xe";
	private static final String DBUSER	 = "jinuk";
	private static final String DBPASS	 = "park";
	
	public StudentDaoImpl() throws Exception{
		//1.드라이버 로딩
		Class.forName(DBDRIVER);
	}
	
	//학생정보 입력
	
	public void insertStudent(StudentVO vo) throws Exception{
		Connection con = null;
		PreparedStatement ps = null;
		
		//sql 작성 (학생 정보 입력)
		String sql = "   INSERT INTO STUDENT(STUDENT_ID, NAME, MAJOR, BIRTHDAY,  "
				   + "     ADDRESS, GENDER, DESCRIPTION, STATUS  )               "
				   + "   VALUES (?,?,?,?,?,?,?,1)    ";
		
		try {
			//연결객체
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			//전송객체
			ps = con.prepareStatement(sql);
			ps.setString(1, vo.STUDENT_ID);  //학번
			ps.setString(2, vo.NAME);        //학생이름
			ps.setString(3, vo.MAJOR);       //학과
			ps.setString(4, vo.BIRTHDAY);    //생년월일
			ps.setString(5, vo.ADDRESS);     //주소
			ps.setString(6, vo.GENDER);      //성별
			ps.setString(7, vo.DESCRIPTION); // 특이사항
			
			//전송
			ps.executeUpdate();
			
		}finally {
			//닫기
			con.close();
			ps.close();
		}

		
	}

	//학생정보수정
	@Override
	public StudentVO modifyStudent(StudentVO vo) throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		//학번을 제외한 학생정보를 수정하는 sql  
		String sql = " UPDATE STUDENT  "
				   + " SET NAME= ?, MAJOR = ?, BIRTHDAY = TO_DATE(?, 'YYYY-MM-DD HH24:MI:SS'),  ADDRESS = ?,  "
				   + "     GENDER = ?, DESCRIPTION = ? "
				   + " WHERE STUDENT_ID = ? ";
		
		try {
			//연결객체 
		con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			//전송객체
		ps = con.prepareStatement(sql);
		ps.setString(1, vo.getNAME());        //이름
		ps.setString(2, vo.getMAJOR());       //학과
		ps.setString(3, vo.getBIRTHDAY());    //생년월일
		ps.setString(4, vo.getADDRESS());     //주소
		ps.setString(5, vo.getGENDER());      //성별
		ps.setString(6, vo.getDESCRIPTION()); //특이사항
		ps.setString(7, vo.getSTUDENT_ID());  //학번
			//전송
		ps.executeQuery();
		}finally {
			con.close();
			ps.close();
			
		}
		
		return vo;
	}

	//학생정보 삭제
	@Override
	public void deleteStudent(StudentVO vo) throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		
		//STATUS를 0으로 수정하여 검색창에는 출력되지 않게 하는 sql
		String sql = " UPDATE STUDENT  "
				   + " SET STATUS = 0    "
				   + " WHERE STUDENT_ID = ?  ";
		try {
			//연결객체
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			//전송객체
			ps = con.prepareStatement(sql);
			ps.setString(1,vo.STUDENT_ID);
			//전송
			ps.executeUpdate();
		}finally {
			//닫기
			con.close();
			ps.close();
			
		}
		
	}

	//학생정보검색
	@Override
	public ArrayList selectStudentByKeyword(int idx, String word) throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		ArrayList list = new ArrayList();
		
		String [] cate = {"STUDENT_ID", "NAME", "MAJOR" };
		
		//STATUS가 1인 학생정보 검새하는 sql
		String sql = " SELECT STUDENT_ID, NAME, MAJOR, BIRTHDAY, ADDRESS, GENDER  "
				   + " FROM   STUDENT                  "
				   + " WHERE " + cate[idx] + " LIKE '%" + word + "%' AND STATUS = 1 "  ;
		
		
		try {
			//연결객체
			con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
			
			//전송객체
			ps = con.prepareStatement(sql);
			
			//전송
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ArrayList temp = new ArrayList();
				temp.add(rs.getString("STUDENT_ID")); //학번
				temp.add(rs.getString("NAME"));       //이름
				temp.add(rs.getString("MAJOR"));      //학과
				temp.add(rs.getString("BIRTHDAY"));   //생년월일
				temp.add(rs.getString("ADDRESS"));    //주소
				temp.add(rs.getString("GENDER"));    //성별
				
				list.add(temp);
				
			}
		}finally{
			//닫기
			con.close();
			ps.close();
		}
		
		
		return list;
	}
	
	
	//학생정보 클릭시 학생 정보 출력
	@Override
	public StudentVO selectByPK (int sNum) throws Exception{
		
		Connection con = null;
		PreparedStatement ps = null;
		
		StudentVO result = new StudentVO();
		//sql
		String sql = "SELECT * FROM STUDENT  "
				+ " WHERE STUDENT_ID = ? ";
		
		//연결객체 얻어오기
		con = DriverManager.getConnection(DBURL, DBUSER, DBPASS);
		//전송객체 얻어오기
		ps = con.prepareStatement(sql);
		ps.setInt(1, sNum);
		//전송
		ResultSet rs = ps.executeQuery();
		if(rs.next()) {
			result.setSTUDENT_ID(rs.getString("STUDENT_ID"));  //학번
			result.setNAME(rs.getString("NAME"));              //이름
			result.setMAJOR(rs.getString("MAJOR"));            //학과
			result.setBIRTHDAY(rs.getString("BIRTHDAY"));      //생년월일
			result.setADDRESS(rs.getString("ADDRESS"));        //주소
			result.setGENDER(rs.getString("GENDER"));          //성별
			result.setDESCRIPTION(rs.getString("DESCRIPTION"));//특이사항
		}
		//닫기
		con.close();
		ps.close();
		
		return result;
		
	}
	
}
