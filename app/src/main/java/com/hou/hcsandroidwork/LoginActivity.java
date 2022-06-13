package com.hou.hcsandroidwork;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends Activity {

    private SharedPreferences sp;
    private EditText user_name, user_password;
    private CheckBox rem_psw;
    private Editor editor = null;
    private Button btn;
    private TextView news;
    private String name_query = null;
    private String psw_query = null;
    private String tmp = null;

    private String RM="user_mes";
    private String RMFlag="flag";
    private String RMUser="user";
    private String RMPsw="psw";
    private String kong="";

    private String DBPsw="user_password";
    private String DBPhone="user_phone";
    private String DBName="user_name";

    private String Exit="exit";

    Cursor cursor = null;
    UserName userName = null;

    DBHelper dbHelper = null;
    //SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user_name = (EditText) findViewById(R.id.names);
        user_password = (EditText) findViewById(R.id.password);
        rem_psw = (CheckBox) findViewById(R.id.jizhu);
        btn = (Button) findViewById(R.id.login_button);
        news = (TextView) findViewById(R.id.news);

        btn.setOnClickListener(onClickListener);
        news.setOnClickListener(onClickListener);

        userName = (UserName) getApplication();

        sp = getSharedPreferences(RM, MODE_PRIVATE);
        editor = sp.edit();
        if (sp.getBoolean(RMFlag, true)) {//getBoolean()中的第二个参数好像没有实际意义
            String user_read = sp.getString(RMUser, kong);
            String psw_read = sp.getString(RMPsw, kong);
            user_name.setText(user_read);
            user_password.setText(psw_read);
            rem_psw.setChecked(true);
        } else {
            rem_psw.setChecked(false);
        }
    }


    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            final DBHelper dbHelper = new DBHelper(getApplicationContext());

            // TODO Auto-generated method stub
            switch (view.getId()) {
                case R.id.login_button:
                    String name = user_name.getText().toString();
                    String password = user_password.getText().toString();

                    if (name.equals(kong) || password.equals(kong)) {
                        Toast.makeText(LoginActivity.this, R.string.NameOrPswNotNull, Toast.LENGTH_SHORT).show();
                    } else {
                        cursor = dbHelper.query_user(user_name.getText().toString());
                        int count = cursor.getCount();
                        if (count != 0) {//cursor.getCount() != 0
                            while (cursor.moveToNext()) {
                                User user = new User();
                                user.setUser_password(cursor.getString(cursor.getColumnIndex(DBPsw)));
                                tmp = user.toString();
                            }
                            if (password.equals(tmp)) {
                                //记住密码
                                if (rem_psw.isChecked()) {
                                    editor.putBoolean(RMFlag, true);
                                    editor.putString(RMUser, name);
                                    editor.putString(RMPsw, password);
                                    editor.commit();
                                    //Toast.makeText(LoginActivity.this, "成功记住密码", Toast.LENGTH_LONG).show();
                                } else {
                                    editor.clear();
                                    editor.commit();
                                }
                                userName.setUserNAME(name);
                                Intent intent = new Intent(LoginActivity.this, ListView_Activity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                                startActivity(intent);
                            } else {
                                Toast.makeText(LoginActivity.this, R.string.NameOrPswIsWrong, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, R.string.RegisterPlease, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                case R.id.news:
                    Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            // 是否退出App的标识
            boolean isExitApp = intent.getBooleanExtra(Exit, false);
            if (isExitApp) {
                // 关闭自身
                this.finish();
            }
        }
    }

}
