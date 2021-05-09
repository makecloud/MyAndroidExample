package com.liuyihui.httpserverdemo1;

import android.os.Bundle;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;


public class MainActivity extends RequestPermissionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestPermission(null);

        //定义一个http接口。然后就可以在浏览器访问了
        Spark.get("/hello", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                return "hello word";
            }
        });

        //



    }
}
