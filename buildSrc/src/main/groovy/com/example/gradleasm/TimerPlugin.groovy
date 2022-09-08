package com.example.gradleasm

import org.gradle.api.Plugin
import org.gradle.api.Project;

public class TimerPlugin implements Plugin<Project>{
    @Override
    void apply(Project target) {
        println("TimerPlugin apply")
        def extensionDemo = target.extensions.create("timer", TimerExtension)
        target.afterEvaluate {
            println("Hello ${extensionDemo.name}")
        }
    }
}