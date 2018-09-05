package cat.iam.bocatas.app.model;

import java.io.Serializable;
import java.util.List;

import cat.iam.bocatas.app.Constants;

/**
 * Classe que conté la infomacio del usuari
 */

public class User implements Serializable {

    private int id;
    private String googleId;
    private String name;
    private String mail;
    private String urlPhoto;
    private String qrPhotoPath;
    private String registerDate;
    private float money;
    private boolean googleLogin;

    public User() {

    }

    public User(int id, String googleId, String name, String mail, String urlPhoto, String qrPhotoPath,
                String registerDate, float money, boolean googleLogin) {
        this.id = id;
        this.googleId = googleId;
        this.name = name;
        this.mail = mail;
        this.urlPhoto = urlPhoto;
        this.qrPhotoPath = qrPhotoPath;
        this.registerDate = registerDate;
        this.money = money;
        this.googleLogin = googleLogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public boolean isGoogleLogin() {
        return googleLogin;
    }

    public void setGoogleLogin(boolean googleLogin) {
        this.googleLogin = googleLogin;
    }

    public String getGoogleId() {
        return googleId;
    }

    public String getQrPhotoPath() {
        return qrPhotoPath;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setQrPhotoPath(String qrPhotoPath) {
        this.qrPhotoPath = qrPhotoPath;
    }

    public String getFullQrUrl() {
        return Constants.URL_SERVER + qrPhotoPath;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }
}

