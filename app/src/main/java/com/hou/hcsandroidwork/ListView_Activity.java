package com.hou.hcsandroidwork;

import android.Manifest;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.hou.hcsandroidwork.finderr.SearchActivity;

import java.io.InputStream;
import java.util.Arrays;

import static android.R.attr.phoneNumber;
import static com.hou.hcsandroidwork.R.id.name;
import static com.hou.hcsandroidwork.R.id.phone;

public class ListView_Activity extends AppCompatActivity {

    final int FLAG_MSG = 0x001;//定义要发送的消息代码
    private ViewFlipper flipper;
    private Message message;
    private String wwhetherPhone="\n是否将电话";
    private String inPhoneText="添加进通讯录";
    private String detailInfor="的详细信息";
    private int[] images2 = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3,
            R.drawable.img4, R.drawable.img5, R.drawable.img6, R.drawable.img7, R.drawable.img8};
    private Animation[] animation = new Animation[2];//定义动画数组，为ViewFlipper指定切换动画

    private Button homeBtn;
    private Button searchBtn;
    private Button myBtn;
    private	 String[]  texts=new  String[]{"快充","airpods","honor9","alien97","earphone","shujuxian"};
    private	 String[]  phones=new  String[]{"111","222","333","444","555","666"};

    private Integer[] images=new Integer[]{R.mipmap.cdb, R.mipmap.airpods,
            R.mipmap.oil, R.mipmap.skirt, R.mipmap.earphone, R.mipmap.shujuxian
    };

    ListView mListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view_);


        mListView =(ListView) findViewById(R.id.listview);
        homeBtn=(Button)findViewById(R.id.but1);
        searchBtn=(Button)findViewById(R.id.but2);
        myBtn=(Button)findViewById(R.id.but3);
        homeBtn.setOnClickListener(onClickListener);
        searchBtn.setOnClickListener(onClickListener);
        myBtn.setOnClickListener(onClickListener);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ListView_Activity.this, new String[]{
                            Manifest.permission.READ_CONTACTS,
                            Manifest.permission.WRITE_CONTACTS},
                    1);
        }
        else {
            //Toast.makeText(ListView_Activity.this, R.string.hasPermission , Toast.LENGTH_SHORT).show();
        }

        TextImageAdapter adapter = new TextImageAdapter(this, Arrays.asList(texts),
                Arrays.asList(images),Arrays.asList(phones));

        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    final int position, long id) {
                //Toast.makeText(ListView_Activity.this, "您选择了" + texts[position]+"\n电话:"+phones[position], Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(ListView_Activity.this)
                        .setTitle(texts[position]+detailInfor)
                        .setMessage(wwhetherPhone+phones[position]+inPhoneText)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //创建一个空的 ContentValues，用于构造要插入的值
                                ContentValues values = new ContentValues();
                                Uri rawContactUri = getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, values);
                                long rawContactId = ContentUris.parseId(rawContactUri);
                                //加入 name
                                values.clear();
                                values.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
                                values.put(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                                values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, texts[position]);
                                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);
                                //加入 phone
                                values.clear();
                                values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
                                values.put(ContactsContract.Data.MIMETYPE,
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                                values.put(ContactsContract.CommonDataKinds.Phone.NUMBER, phones[position]);
                                values.put(ContactsContract.CommonDataKinds.Phone.TYPE,
                                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

                                Toast.makeText(ListView_Activity.this, R.string.addSuccess , Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(ListView_Activity.this, R.string.abandonAdd , Toast.LENGTH_SHORT).show();
                            }
        }).create().show();
    }
        });


        //下面是平移动画
        flipper =(ViewFlipper)findViewById(R.id.viewFlipper);

        for (int i = 0; i < images2.length; i++) {
            ImageView imageView = new ImageView(ListView_Activity.this);
            imageView.setImageResource(images2[i]);
            flipper.addView(imageView);
        }
        //初始化动画数组
        animation[0] = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        animation[1] = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        flipper.setInAnimation(animation[0]);
        flipper.setOutAnimation(animation[1]);

        message = Message.obtain();//获得消息对象
        message.what = FLAG_MSG;//设置消息代码
        handler.sendMessage(message);//发送消息
    }

    //定义授权操作后的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(ListView_Activity.this, R.string.hasPermission , Toast.LENGTH_SHORT).show();

                } else {
                //未获得授权时，提示信息
                    Toast.makeText(this, R.string.notPermission, Toast.LENGTH_SHORT).show();
                }
                break;default:
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    Handler handler = new Handler() {  //创建android.os.Handler对象
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == FLAG_MSG) {
                flipper.showPrevious();
            }
            message = handler.obtainMessage(FLAG_MSG);
            handler.sendMessageDelayed(message, 3000);
        }
    };

    private View.OnClickListener onClickListener =new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            switch (v.getId()){
                case R.id.but1:
                    Toast.makeText(ListView_Activity.this, R.string.hasHome , Toast.LENGTH_LONG).show();
                    break;
                case R.id.but2:
                    intent.setClass(ListView_Activity.this,SearchActivity.class);
                    startActivity(intent);
                    break;
                case R.id.but3:
                    intent.setClass(ListView_Activity.this,MyActivity.class);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };
}