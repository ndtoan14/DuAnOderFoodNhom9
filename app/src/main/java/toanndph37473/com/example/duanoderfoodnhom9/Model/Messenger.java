package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class Messenger {
    private int idUser;
    private String noiDung;

    public Messenger() {
    }

    public Messenger(int idUser, String noiDung) {
        this.idUser = idUser;
        this.noiDung = noiDung;
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
}
