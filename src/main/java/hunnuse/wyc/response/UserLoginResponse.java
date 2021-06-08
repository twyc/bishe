package hunnuse.wyc.response;

public class UserLoginResponse {
    private Integer id;

    private String userName;

    private Long token;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getToken() {
        return token;
    }

    public void setToken(Long token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", token=" + token +
                '}';
    }
}
