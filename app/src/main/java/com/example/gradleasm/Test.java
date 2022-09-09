package com.example.gradleasm;

import android.util.Log;

public class Test {
    void test() {
        long startTime = System.currentTimeMillis();
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        if(costTime > 100){
          StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[0];//获得当前方法的StackTraceElement
           Log.e("TimeCost", String.format(
             "===> %s.%s(%s:%s)方法耗时 %d ms",
             thisMethodStack.getClassName(), //类的全限定名称
             thisMethodStack.getMethodName(),//方法名
             thisMethodStack.getFileName(),  //类文件名称
             thisMethodStack.getLineNumber(),//行号
             costTime                        //方法耗时
           )
               );
        }
    }
}
