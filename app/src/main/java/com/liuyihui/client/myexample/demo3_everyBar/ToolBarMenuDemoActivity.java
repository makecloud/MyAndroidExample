package com.liuyihui.client.myexample.demo3_everyBar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;


/**
 * 工具栏 菜单 demo
 */
public class ToolBarMenuDemoActivity extends AppCompatActivity {

    private static final String TAG = "ToolBarMenuDemoActivity";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//去掉本activity的ActionBar

        setContentView(R.layout.activity_tool_bar_menu);

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
                    Toast.makeText(ToolBarMenuDemoActivity.this, "点击了搜索按钮", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
