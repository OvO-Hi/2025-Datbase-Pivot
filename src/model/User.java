package model;

public class User {
    private int userId;
    private String loginId;
    private String pw;
    private String name;
    private String phoneNumber;
    private int isAdmin; // 관리자 여부(0: 일반, 1: 관리자)

    // 로그인 후 조회 시 사용
    public User(int userId, String loginId, String pw, String name, String phoneNumber, int isAdmin) {
        this.userId = userId;
        this.loginId = loginId;
        this.pw = pw;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.isAdmin = isAdmin;
    }

    // 회원가입용 생성자 (isAdmin 기본값 0)
    public User(String loginId, String pw, String name, String phoneNumber) {
        this(0, loginId, pw, name, phoneNumber, 0);
    }

    public int getUserId() { return userId; }
    public String getLoginId() { return loginId; }
    public String getPw() { return pw; }
    public String getName() { return name; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getIsAdmin() { return isAdmin; }
}
