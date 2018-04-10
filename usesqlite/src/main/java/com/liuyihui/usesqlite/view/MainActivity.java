package com.liuyihui.usesqlite.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.liuyihui.usesqlite.R;
import com.liuyihui.usesqlite.repository.Book;
import com.liuyihui.usesqlite.repository.CommonDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private CommonDAO<Book> bookCommonDAO;
    private Button createDBButton;
    private EditText idEditText;
    private TextView showResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化DAO
        bookCommonDAO = new CommonDAO<>(Book.class);
        //在获取数据库的时候,执行建库操作, 并返回库
        createDBButton = findViewById(R.id.create_db);
        createDBButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        idEditText = findViewById(R.id.row_id);
        showResultTextView = findViewById(R.id.show_result);
    }

    /**
     * 查询一条数据按钮
     *
     * @param v
     */
    public void selectOneByCondition(View v) {
        Book book = new Book();
        book.setId(5);
        //获取输入的id
        String idstr = idEditText.getText().toString();
        if (!TextUtils.isEmpty(idstr)) {
            book.setId(Integer.valueOf(idstr));
        }
        //查询结果
        Book resultBook = bookCommonDAO.selectOne(book);
        //打印查询结果
        Log.i(TAG, resultBook.toString());
        showResultTextView.setText(resultBook.toString());
    }

    /**
     * 查询全部按钮
     *
     * @param view
     */
    public void selectAll(View view) {
        List<Book> bookList = bookCommonDAO.selectAll();
        StringBuilder sb = new StringBuilder();
        for (Book book : bookList) {
            sb.append(book.toString() + "\n");
        }
        showResultTextView.setText(sb.toString());
    }

    public void selectSome(View view) {
    }

    /**
     * 插入一条数据按钮
     *
     * @param view
     */
    public void addOne(View view) {
        //直接调数据层
        Book book = new Book();
        book.setAuthor("liuyihui");
        book.setName("liuyihui");
        bookCommonDAO.add(book);
    }

    public void updateById(View view) {
        Book book = new Book();
        book.setId(1);
        book = bookCommonDAO.selectOne(book);
        book.setAuthor("madaha");
        bookCommonDAO.updateById(book.getId(), book);
    }

    public void deleteById(View view) {
        Book book = new Book();
        book.setId(2);
        book = bookCommonDAO.selectOne(book);
        bookCommonDAO.deleteById(book.getId());
    }
}
