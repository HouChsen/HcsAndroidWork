package com.hou.hcsandroidwork;

        import android.annotation.SuppressLint;
        import android.app.AlertDialog;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Toast;


public class RegisterActivity extends AppCompatActivity {


    DBHelper dbHelper=null;
    User user =null;
    private String str = "";
    private String name_str = "";
    private String paswd_str = "";
    private String rePaswd_str = "";
    private String phone_str = "";
    private boolean over = true;
    private boolean same = true;

    EditText name_edit, paswd_edit, rePaswd_edit, phone_edit;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_edit = (EditText) findViewById(R.id.name);
        paswd_edit = (EditText) findViewById(R.id.paswd);
        rePaswd_edit = (EditText) findViewById(R.id.rePaswd);
        phone_edit = (EditText) findViewById(R.id.phone);
        btn = (Button) findViewById(R.id.reg_btn);

        btn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressLint("Range")
        @Override
        public void onClick(View view) {
            final DBHelper dbHelper = new DBHelper(getApplicationContext());

            switch (view.getId()) {
                case R.id.reg_btn:
                    new AlertDialog.Builder(RegisterActivity.this).setTitle(R.string.SysTemHelp)
                            .setMessage(R.string.whetherSubmit)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    // TODO Auto-generated method stub
                                    name_str = name_edit.getText().toString();
                                    paswd_str = paswd_edit.getText().toString();
                                    rePaswd_str = rePaswd_edit.getText().toString();
                                    phone_str = phone_edit.getText().toString();
                                    over = true;

                                    if (!(rePaswd_str.equals(paswd_str))) {
                                        same=false;
                                        Toast.makeText(RegisterActivity.this, R.string.pswsDiff, Toast.LENGTH_SHORT).show();
                                        paswd_edit.setText(R.string.kong);
                                        paswd_edit.setHint(R.string.reConfirmPsw);
                                        rePaswd_edit.setText(R.string.kong);
                                        rePaswd_edit.setHint(R.string.inputSamePsw);
                                    }
                                    if (name_str.equals(R.string.kong)) {
                                        over = false;
                                        str += R.string.namee;
                                    }
                                    if (paswd_str.equals(R.string.kong)) {
                                        over = false;
                                        str += R.string.psww;
                                    }
                                    if (rePaswd_str.equals(R.string.kong)) {
                                        over = false;
                                        str += R.string.rePsww;
                                    }
                                    if (phone_str.equals(R.string.kong)) {
                                        over = false;
                                        str += R.string.phonee;
                                    }
                                    if (!over) {
                                        Toast.makeText(RegisterActivity.this, R.string.inputPlease + str + R.string.afterTry, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Cursor cursor = dbHelper.query_user(name_edit.getText().toString());
                                        if (cursor.getCount() != 0) {//cursor.getCount() != 0
                                            Toast.makeText(RegisterActivity.this, R.string.hasRegister, Toast.LENGTH_SHORT).show();
                                        } else if(!same){
                                            same=true;
                                        } else {
                                            user = new User(name_edit.getText().toString(), paswd_edit.getText().toString(), phone_edit.getText().toString());
                                            dbHelper.insert_user(user);//将数据保存到数据库
                                            Toast.makeText(RegisterActivity.this,R.string.RSAndLogin , Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                }

                            }).setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            // TODO Auto-generated method stub

                        }
                    }).show();
                    break;
                default:
                    break;
            }
        }

    };
}

