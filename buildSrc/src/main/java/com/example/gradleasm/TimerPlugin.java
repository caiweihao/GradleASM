package com.example.gradleasm;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class TimerPlugin implements Plugin<Project> {

    //当函数运行时间大于阀值时判定为耗时函数，单位ms
    public static long sThreshold = 100L;
    //packageName，只打印packageName包内的耗时函数
    public static String sPackageName = "";

    @Override
    public void apply(Project project) {


        TimerExtension timerExtension = project.getExtensions().create("timer", TimerExtension.class);
        project.afterEvaluate(project1 -> {System.out.println("timer packageName name is " + timerExtension.getPackageName());
            System.out.println("timer Threshold name is " + timerExtension.getThreshold());
        });
        //通过project实例获取android gradle plugin中的名为android的扩展实例
        AppExtension appExtension = (AppExtension) project.getExtensions().getByName("android");
        //调用android的扩展实例即appExtension的registerTransform方法往android gradle plugin中注册我们自定义的Transform
        appExtension.registerTransform(new TimeCostTransform());
    }
}
