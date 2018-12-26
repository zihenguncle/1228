package jx.com.day1_fanshe.annotation;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;

import jx.com.day1_fanshe.R;

public class InjectViewParser {

    public static void bind(Object object){
        try {
            parse(object);
        }catch (Exception e){

        }
    }

    public static void myparse(Object object) throws Exception {
        Class<?> aClass = object.getClass();
        View view = null;
        Field[] fields = aClass.getFields();
        for (Field field : fields){
            if(field.isAnnotationPresent(InjectAnnotation.class)){
                InjectAnnotation annotation = field.getAnnotation(InjectAnnotation.class);
                int value = annotation.value();
                if(value <0){
                    throw new Exception("error");
                }else {
                    field.setAccessible(true);
                    if(object instanceof View){
                        view = ((View) object).findViewById(value);
                    }else if (object instanceof Activity){
                        view = ((Activity) object).findViewById(value);
                    }
                    field.set(object,value);
                }

            }
        }
    }

    public static void parse(final Object object) throws Exception {
        Class<?> clazz = object.getClass();
        View view = null;
        //拿到所有的参数
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields){
            if(field.isAnnotationPresent(InjectAnnotation.class)){
                InjectAnnotation annotation = field.getAnnotation(InjectAnnotation.class);
                int id = annotation.value();
                if(id<0){
                    throw new Exception("error");
                }else {
                    field.setAccessible(true);
                    if(object instanceof View){
                        view = ((View) object).findViewById(id);
                    }else if(object instanceof Activity){
                        view = ((Activity) object).findViewById(id);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Log.i("Tag","78787878");
                            }
                        });
                    }
                    field.set(object,view);
                }
            }
        }
    }

}











