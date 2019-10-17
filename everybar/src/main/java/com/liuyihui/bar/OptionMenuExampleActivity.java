package com.liuyihui.bar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.oohlink.wifiprobe.everybar.R;

/**
 * 选项菜单
 */
public class OptionMenuExampleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu_example);
        setTitle("OptionMenuExampleActivity");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, Menu.FIRST, 0, "开始");
        menu.add(0, Menu.FIRST + 1, 0, "退出");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, "menu 1", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "menu 2", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:
                finish();
                break;

            default:
                break;
        }
        return true;
    }
}
