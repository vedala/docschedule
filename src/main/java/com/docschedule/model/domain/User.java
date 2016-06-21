
package com.docschedule.model.domain;

public class User {
    private int userid;
    private String username;
    private String password;
    private int verified;
    private String email;
    private String token;

    public void User(int userid, String username, String password,
                            int verified, String email, String token) {

        this.userid = userid;
        this.username = username;
        this.password = password;
        this.verified = verified;
        this.email = email;
        this.token = token;
    }

    public int getUserid() { return userid; }
    public void setUserid(int userid) { this.userid = userid; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String userid) { this.password = password; }

    public int getVerified() { return verified; }
    public void setVerified(int verified) { this.verified = verified; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

}
