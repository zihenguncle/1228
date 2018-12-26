package jx.com.day1_fanshe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import jx.com.day1_fanshe.annotation.InjectAnnotation;
import jx.com.day1_fanshe.annotation.InjectViewParser;

public class MainActivity extends AppCompatActivity {

    @InjectAnnotation(R.id.gouzao)
    private Button gouzao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectViewParser.bind(this);
        InjectViewParser.bind(gouzao);
        /*gouzao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this,"haha",Toast.LENGTH_SHORT).show();
                *//*try {
                    Class clazz = DateBean.class;
                    Constructor constructor = clazz.getConstructor();
                    Object obj = constructor.newInstance();2
                    Field name = clazz.getField("name");.
                    name.setAccessible(true);
                    name.set(obj,"静静");
                    Log.i("Tag",name.get(obj)+"");
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*
            }
        });*/
    }
}
