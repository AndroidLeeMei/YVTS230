package com.example.auser.yvts23;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcel;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener{
    ListView listView;
    public EditText edtName,edtTel,edtEmail;
    Button btnAdd,btnedit,btnDel;

    private DB mDbHelper;
    private Cursor mNotesCursor;
    String[] from;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName=(EditText)findViewById(R.id.editName);
        edtTel=(EditText)findViewById(R.id.editTel);
        edtEmail=(EditText)findViewById(R.id.editEmail);

        btnAdd=(Button)findViewById(R.id.buttonAdd);
        btnedit=(Button)findViewById(R.id.buttonEdit);
        btnDel=(Button)findViewById(R.id.buttonDel);

        listView = (ListView) findViewById(R.id.listView);
//        listView.setEmptyView(findViewById(R.id.empty));
        listView.setOnItemClickListener(this);
        mDbHelper = new DB(this).open();
        setAdapter();
    }





    private void setAdapter() {
        mNotesCursor = mDbHelper.getAll();
        if (mNotesCursor != null)
            mNotesCursor.moveToFirst();
        startManagingCursor(mNotesCursor);
         from = new String[]{"note", "created","email"};
        int[] to = new int[]{R.id.textdName, R.id.textdTel, R.id.textdEmail};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
                R.layout.simple_list_item_1, mNotesCursor, from, to, SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        listView.setAdapter(adapter);

        if (mNotesCursor != null) {
            mNotesCursor.moveToFirst();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 1, 0, "新增").setIcon(android.R.drawable.ic_menu_add)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 2, 0, "刪除").setIcon(android.R.drawable.ic_menu_delete)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        menu.add(0, 3, 0, "修改").setIcon(android.R.drawable.ic_menu_edit)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    int count;
    long rowId;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                count++;
//                mDbHelper.create(count + ". Note");

                mDbHelper.createUserData(edtName.getText().toString()
                        ,edtTel.getText().toString(),edtEmail.getText().toString());
                setAdapter();
                break;
            case 2:
                mDbHelper.delete(rowId);
                setAdapter();
                break;

            case 3:
//                View root;
//                final AlertDialog dialog; //讓自定Layout可有關閉功能
////                AlertDialog dialog;
//                LayoutInflater inflater = (LayoutInflater) this
//                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                root = inflater.inflate(R.layout.dialog_add_house, null);//找出根源樹,
//                EditText edtPassword = root.findViewById(R.id.etPassword);
//                Button confirmDelete = root.findViewById(R.id.btnConfirm);
//                Button cancelDelete = root.findViewById(R.id.btnCancel);
////                txDeleteDescription.setText("請問是否要將資料刪除");
//                cancelDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        dialog.dismiss();
//                    }
//                });
//                confirmDelete.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                    }
//                });
//                AlertDialog.Builder abc = new AlertDialog.Builder(this);
//                abc.setView(root);
//                dialog = abc.show();
                Intent it=new Intent(MainActivity.this,noteBookActivity.class);
//                it.setData(Uri.parse("sms:" + edtTel.getText().toString() + "?body=您好!"));
                startActivity(it);


        }

        return super.onOptionsItemSelected(item);
    }


    public void addData(View target){
        mDbHelper.createUserData(edtName.getText().toString()
                ,edtTel.getText().toString(),edtEmail.getText().toString());

        edtName.setText("");
        edtTel.setText("");
        edtEmail.setText("");
        setAdapter();

    }

    public void editData(View target){

//        mNotesCursor = mDbHelper.getAll();
        String sql="update twzipcode " +
                "set note='" + edtName.getText() + "' " +
                ", created='" + edtTel.getText() + "' " +
                ", email='" + edtEmail.getText() + "' " +
                "where _id=" + rowId+";";
        Log.d("YVTSsql=",sql);
        mDbHelper.editExcute(sql);
        setAdapter();
        edtName.setText("");
        edtTel.setText("");
        edtEmail.setText("");
//         INTEGER PRIMARY KEY, note TEXT , created TEXT,email TEXT
    }

    public void delData(View target){
        mDbHelper.delete(rowId);
        setAdapter();
        edtName.setText("");
        edtTel.setText("");
        edtEmail.setText("");
    }


    public void phone(View target){
        Log.d("tel=",edtTel.getText().toString());
         Intent it=new Intent(Intent.ACTION_VIEW);
        Uri callUri = Uri.parse("tel:" + edtTel.getText().toString()  );
        it = new Intent(Intent.ACTION_CALL, callUri);
                startActivity(it);

    }

    public void message(View target){
        Intent it=new Intent(Intent.ACTION_VIEW);
        it.setData(Uri.parse("sms:" + edtTel.getText().toString() + "?body=您好!"));
        startActivity(it);
//        Intent it=new Intent(Intent.ACTION_VIEW);
//        it.setData(Uri.parse("mailto:service@google.com.tw"));
//        it.putExtra(Intent.EXTRA_CC,new String[]{"test@google.com.tw"});//設定副本收件人
//        it.putExtra(Intent.EXTRA_SUBJECT,"您好");//設定主
//        it.putExtra(Intent.EXTRA_TEXT,"謝謝");//設定內容
//        startActivity(it);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rowId = id;
//        System.out.println("rowId = " + rowId);
//        Toast.makeText(this, "第" + position + "項", Toast.LENGTH_SHORT).show();

        mNotesCursor=mDbHelper.editSelect(rowId);
        if (mNotesCursor != null)
            mNotesCursor.moveToFirst();

        edtName.setText(mNotesCursor.getString(1));
        edtTel.setText(mNotesCursor.getString(2));
        edtEmail.setText(mNotesCursor.getString(3));

    }
}
