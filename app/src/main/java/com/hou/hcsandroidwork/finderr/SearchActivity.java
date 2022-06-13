package com.hou.hcsandroidwork.finderr;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.hou.hcsandroidwork.ListView_Activity;
import com.hou.hcsandroidwork.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity implements SearchView.SearchViewListener {
    private ListView lvResults;
    private SearchView searchView;
    private String wwhetherPhone="\n是否将电话";
    private String inPhoneText="添加进通讯录";
    private String detailInfor="的详细信息";
    private	 String[]  texts=new  String[]{"快充","airpods","honor9","alien97","earphone","shujuxian"};
    private	 String[]  phones=new  String[]{"111","222","333","444","555","666"};

    private Integer[] images=new Integer[]{R.mipmap.cdb, R.mipmap.airpods,
            R.mipmap.oil, R.mipmap.skirt, R.mipmap.earphone, R.mipmap.shujuxian
    };

    private ArrayAdapter<String> autoCompleteAdapter;
    private SearchAdapter resultAdapter;

    private List<Bean> dbData;

    private List<String> autoCompleteData;
    private List<Bean> resultData;
    private static int DEFAULT_HINT_SIZE = 4;
    private static int hintSize = DEFAULT_HINT_SIZE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search);
        initData();
        initViews();
    }

    /*
     *初始化
     */
    private void initViews() {
        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
        searchView = (SearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter

        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        //列表监听
        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long l) {
                new AlertDialog.Builder(SearchActivity.this)
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

                                Toast.makeText(SearchActivity.this, R.string.addSuccess , Toast.LENGTH_SHORT).show();
                            }})
                        .setNegativeButton(R.string.cancel,new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Toast.makeText(SearchActivity.this, R.string.abandonAdd , Toast.LENGTH_SHORT).show();
                            }
                        }).create().show();
            }

        });
    }

    /*
     初始化
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }
    /**
     * 获取db 数据，模拟向书记库中添加数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<> (size);

        dbData.add(new Bean(R.drawable.cdb,
                "kuaichong",
                "111"
                ));
        dbData.add(new Bean(R.drawable.airpods,
                "airpods",
                "222"));

        dbData.add(new Bean(R.drawable.oil,
                "honor9",
                "333"));
        dbData.add(new Bean(R.drawable.skirt,
                "alien97",
                "444"));
        dbData.add(new Bean(R.drawable.earphone,
                "earphone",
                "555"));
        dbData.add(new Bean(R.drawable.shujuxian,
                "shujuxian",
                "666"));


    }

    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            //定义一个SearchAdapter的对象
            //
            //
            //
            resultAdapter = new SearchAdapter(this, resultData, R.layout.item_bean_list);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }


    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜索", Toast.LENGTH_SHORT).show();
    }
}

