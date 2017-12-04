package com.easzz.corejava.annotation;

/**
 * Created by Easzz on 07/07/2017.
 * 注解元素必须有确定的值，要么在定义注解的默认值中指定，要么在使用注解时指定，非基本类型的注解元素的值不可为null
 */
public class Apple {
    @FruitName("apple")
    private String appleName;


    @FruitColor(fruitColor = FruitColor.Color.RED)
    private String appleColor;

    @FruitProvider(id = 2015,name = "天天供应商",address = "中原路特一号")
    private String appleProvider;

    public String getAppleName() {
        return appleName;
    }

    public void setAppleName(String appleName) {
        this.appleName = appleName;
    }

    public String getAppleColor() {
        return appleColor;
    }

    public void setAppleColor(String appleColor) {
        this.appleColor = appleColor;
    }

    public void displayName(){
        System.out.println("水果的名字是：苹果！");
    }

    public Apple(String appleName, String appleColor) {
        this.appleName = appleName;
        this.appleColor = appleColor;
    }
}
