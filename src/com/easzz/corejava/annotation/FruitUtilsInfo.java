package com.easzz.corejava.annotation;


import java.lang.reflect.Field;

/**
 * Created by Easzz on 07/07/2017.
 */
public class FruitUtilsInfo {
    public static void getFruitInfo(Class clazz){
        String strFruitName="水果名称为：";
        String strFruitColor="水果颜色为：";
        String strFruitProvider="水果供货商为：";
        Field[] declaredFields = clazz.getDeclaredFields();
        for(Field f:declaredFields){
            if (f.isAnnotationPresent(FruitName.class)){
                //判断此属性是否包含注解
                FruitName annotation = f.getAnnotation(FruitName.class);
                strFruitName+=annotation.value();
                System.out.println(strFruitName);
            }
            if(f.isAnnotationPresent(FruitColor.class)){
                FruitColor annotation = f.getAnnotation(FruitColor.class);
                strFruitColor+=annotation.fruitColor();
                System.out.println(strFruitColor);
            }
            if(f.isAnnotationPresent(FruitProvider.class)){
                FruitProvider annotation = f.getAnnotation(FruitProvider.class);
                strFruitProvider+=annotation.name()+"编号："+annotation.id()+"地址："+annotation.address();
                System.out.println(strFruitProvider);
            }
        }
    }
    public void getInfo(){
        FruitUtilsInfo.getFruitInfo(Apple.class);
    }
}
