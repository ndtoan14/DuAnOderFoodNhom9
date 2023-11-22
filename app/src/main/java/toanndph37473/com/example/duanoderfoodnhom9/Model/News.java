package toanndph37473.com.example.duanoderfoodnhom9.Model;

public class News {
    private int idTinTuc;
    private String  tieuDe, noiDung, hinhAnh, ngayDangTin;
    private int likeCount,commentCount,shareCount;
    private boolean isliked = false;
    public News() {
    }


    public News(int idTinTuc, String tieuDe, String noiDung, String hinhAnh, String ngayDangTin, int likeCount, int commentCount, int shareCount) {
        this.idTinTuc = idTinTuc;
        this.tieuDe = tieuDe;
        this.noiDung = noiDung;
        this.hinhAnh = hinhAnh;
        this.ngayDangTin = ngayDangTin;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
    }

    public boolean isIsliked() {
        return isliked;
    }

    public void setIsliked(boolean isliked) {
        this.isliked = isliked;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public void setShareCount(int shareCount) {
        this.shareCount = shareCount;
    }

    public int getIdTinTuc() {
        return idTinTuc;
    }

    public void setIdTinTuc(int idTinTuc) {
        this.idTinTuc = idTinTuc;
    }

    public String getTieuDe() {
        return tieuDe;
    }

    public void setTieuDe(String tieuDe) {
        this.tieuDe = tieuDe;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNgayDangTin() {
        return ngayDangTin;
    }

    public void setNgayDangTin(String ngayDangTin) {
        this.ngayDangTin = ngayDangTin;
    }
}
