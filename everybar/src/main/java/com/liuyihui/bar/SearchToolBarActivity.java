package com.liuyihui.bar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.oohlink.wifiprobe.everybar.R;

/**
 * 这个案例目的是什么? 也没注释明白~ 真是的
 */
public class SearchToolBarActivity extends AppCompatActivity {

    private static final String TAG = "SearchToolBarActivity";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉本activity的ActionBar

        setContentView(R.layout.activity_search_tool_bar);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("首页");
        toolbar.setSubtitle("副标题");

        //toolbar使用一个菜单布局
        toolbar.inflateMenu(R.menu.toolbar_menu);
        //菜单布局点击事件
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int menuId = item.getItemId();
                //搜索按钮
                if (menuId == R.id.action_search) {
                    Toast.makeText(SearchToolBarActivity.this, "点击了搜索按钮", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }
}
