package model.vo;

public class StudentVO {


	
	public String getSTUDENT_ID() {
		return STUDENT_ID;
	}
	public void setSTUDENT_ID(String sTUDENT_ID) {
		STUDENT_ID = sTUDENT_ID;
	}
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public String getMAJOR() {
		return MAJOR;
	}
	public void setMAJOR(String mAJOR) {
		MAJOR = mAJOR;
	}
	public String getBIRTHDAY() {
		return BIRTHDAY;
	}
	public void setBIRTHDAY(String bIRTHDAY) {
		BIRTHDAY = bIRTHDAY;
	}
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getGENDER() {
		return GENDER;
	}
	public void setGENDER(String gENDER) {
		GENDER = gENDER;
	}
	public String getDESCRIPTION() {
		return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}
	public int getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(int sTATUS) {
		STATUS = sTATUS;
	}
	public String STUDENT_ID;   //학번
	public String NAME;         //이름
	public String MAJOR;        //학과
	public String BIRTHDAY;     //생년월일
	public String ADDRESS;      //주소
	public String GENDER;       //성별
	public String DESCRIPTION;  //특이사항
	int STATUS;          //상태 (활동중이면1 아니면0으로 입력하여 검색목록에서 활동중인 사람만 출력하기 위한 변수) 

}
