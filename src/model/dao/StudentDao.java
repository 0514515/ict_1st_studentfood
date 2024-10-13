package model.dao;

import java.util.ArrayList;

import model.vo.StudentVO;

public interface StudentDao {

	//학생정보 입력
	void insertStudent(StudentVO vo) throws Exception;

	//학생정보 수정
	
	StudentVO modifyStudent(StudentVO vo) throws Exception;
	
	//학생정보 삭제 (활동중인 사람 1, 안하는 사람 0)
	
	void deleteStudent(StudentVO vo) throws Exception;
	
	//키워드로 학생 검색
	
	ArrayList selectStudentByKeyword(int idx , String word) throws Exception;
	
	//학생정보 클릭시 학생 정보 출력
	public StudentVO selectByPK (int sNum) throws Exception;
	
	
}
