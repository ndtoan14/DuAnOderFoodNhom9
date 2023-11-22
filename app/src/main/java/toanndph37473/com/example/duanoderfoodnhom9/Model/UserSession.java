package toanndph37473.com.example.duanoderfoodnhom9.Model;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class UserSession {
    private static final String KEY_USERNAME = "KEY_USERNAME";
    private static final String PREFS_NAME = "UserSessionPrefs";
    private static SharedPreferences prefs;

    public static void saveUser(Context context, Users user) {
        //lưu thông tin người dùng đăng nhập vào sharedPreference
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String userJson = new Gson().toJson(user);//sử dụng thư viện Gson để chuyển đổi từ đối tượng sang Json
        editor.putString(KEY_USERNAME, userJson);
        editor.apply();
    }
    public static Users getCurrentUser(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userJson = prefs.getString(KEY_USERNAME, null);
        if (userJson == null) {
            return null;
        }
        Users user = new Gson().fromJson(userJson, Users.class);
        return user;
    }


    public static void clearUser(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove(KEY_USERNAME);
        editor.apply();
}
}

//    public static void setCurrentUser(Users user) {
//        currentUser = user;
//    }
//
//    public static Users getCurrentUser() {
//        return currentUser;
//    }




