package com.eason.inout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by eason on 16-11-21.
 */

public class SecondActivity extends AppCompatActivity {
    private static String[] action = {"听到","看到","想到"};
    private static String[] result = {
            "心里好像堵住了一般",
            "会想起她的温柔细语，想起她的顾盼浅笑",
            "想到那时的欢愉，今日的孤单",
            "可以理解她，却没办法原谅自己",
            "会想到她"
    };

    private static int[] pic = {R.mipmap.second,R.mipmap.second2};

    private static final int MAX_VISITNUM = 5;

    private Button button;
    private TextView text;
    private TextView hint;
    private EditText editText;
    private ImageView imageView;

    private int tindex = 0;

    private int visitIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        button = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);
        editText = (EditText) findViewById(R.id.editText);
        hint = (TextView) findViewById(R.id.hint);
        imageView = (ImageView) findViewById(R.id.imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = editText.getText().toString();
                if(m.equals("")){
                    Toast.makeText(SecondActivity.this,"请输入能引起你恋爱回忆的事物",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SecondActivity.this,FirstActivity.class);
                intent.putExtra("material",m);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    protected void update(){
        visitIndex++;
        if(visitIndex>MAX_VISITNUM){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setMessage("从怪圈中解脱吧！")
                    .setPositiveButton("解脱", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(SecondActivity.this,EndActivity.class);
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

        Intent intent = getIntent();
        String material = intent.getStringExtra("material");
        int index = intent.getIntExtra("action",0);

        text.setText("分手之后，每每"+
                action[index]+
                material+
                ","+
                result[(int)(Math.random()*result.length)]);

        tindex = (int)(Math.random())*action.length;
        hint.setText("从此再也看不到她了，你常常"+
                action[tindex]+
                "?");

        imageView.setImageResource(pic[(int)(Math.random()*pic.length)]);
    }
}
