package com.eason.inout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by eason on 16-11-21.
 */
//注意FirstActivity和SecondActivity都是singleTask模式，不了解的同学请度娘一下。
public class FirstActivity extends AppCompatActivity {
    private static String[] action = {"听到","看到","想到"};//谓语
    private static String[] result = {
            "心跳就会扑通扑通地乱跳",
            "一日不见如隔三秋","迫切的想要见她",
            "智商就变成了零",
            "会想起她的温柔细语，想起她的顾盼浅笑","都会觉得很满足",
            "都想要和她在一起","就在那一瞬间爱上了她"
    };//结果

    private static int[] pic = {R.mipmap.first,R.mipmap.first2};

    private static final int MAX_VISITNUM = 5;//该界面最大访问次数

    private Button button;
    private TextView text;
    private TextView hint;
    private EditText editText;
    private ImageView imageView;

    private int tindex = 0;//传递action的index

    private int visitIndex = 0;//界面的访问次数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);

        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);
        editText = (EditText) findViewById(R.id.editText);
        hint = (TextView) findViewById(R.id.hint);
        imageView = (ImageView) findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = editText.getText().toString();//获取edittext中输入的文本
                if(m.equals("")){//检查文本是否为空
                    Toast.makeText(FirstActivity.this,"请输入能引起你恋爱回忆的事物",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(FirstActivity.this,SecondActivity.class);//启动secondActivity
                intent.putExtra("material",m);//传递数据
                intent.putExtra("action",tindex);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        update();
    }
    //getIntent()方法只能获取一个activity第一次启动收到的intent，以后收到的intent需要通过这个回调方法更新intent。
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    //更新界面函数
    protected void update(){
        visitIndex++;

        if(visitIndex>MAX_VISITNUM){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage("从怪圈中解脱吧！")
                    .setPositiveButton("解脱", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(FirstActivity.this,EndActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            visitIndex=0;
                        }
                    });
            builder.create().show();
        }
        //获得传来的intent携带的数据
        Intent intent = getIntent();
        String material = intent.getStringExtra("material");
        int index = intent.getIntExtra("action",0);
        //显示数据，使用了随机数，方法是Math.random(),返回的随机数范围是[0,1),类型是double
        text.setText("和她在一起的时候，每每"+
                action[index]+
                material+
                ","+
                result[(int)(Math.random()*result.length)]);

        tindex = (int)(Math.random()*action.length);
        hint.setText("和她在一起时，你常常"+
                action[tindex]+
                "?");

        imageView.setImageResource(pic[(int)(Math.random()*pic.length)]);
    }
}
