package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class CommentNews {
    private int idComment, idUsers, idTinTuc;
    private String noiDung,ngayComment,tenUser, hinhAnh;

    public CommentNews() {
    }

    public CommentNews(int idComment, int idUsers, int idTinTuc, String noiDung, String ngayComment, String tenUser, String hinhAnh) {
        this.idComment = idComment;
        this.idUsers = idUsers;
        this.idTinTuc = idTinTuc;
        this.noiDung = noiDung;
        this.ngayComment = ngayComment;
        this.tenUser = tenUser;
        this.hinhAnh = hinhAnh;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getTenUser() {
        return tenUser;
    }

    public void setTenUser(String tenUser) {
        this.tenUser = tenUser;
    }

    public int getIdComment() {
        return idComment;
    }

    public void setIdComment(int idComment) {
        this.idComment = idComment;
    }

    public int getIdUsers() {
        return idUsers;
    }

    public void setIdUsers(int idUsers) {
        this.idUsers = idUsers;
    }

    public int getIdTinTuc() {
        return idTinTuc;
    }

    public void setIdTinTuc(int idTinTuc) {
        this.idTinTuc = idTinTuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getNgayComment() {
        return ngayComment;
    }

    public void setNgayComment(String ngayComment) {
        this.ngayComment = ngayComment;
    }
}
