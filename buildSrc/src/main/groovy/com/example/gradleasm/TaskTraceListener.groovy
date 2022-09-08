package com.example.gradleasm

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

import java.util.function.Consumer

//自定义task.gradle在build.gradle引用
class TaskTraceListener implements TaskExecutionListener, BuildListener {
    private long timeMillis
    private Map<Long, String> taskMap = new HashMap()
    private List<Long> mTimeList = new ArrayList<>();

    @Override
    void beforeExecute(Task task) {
        timeMillis = System.currentTimeMillis();
        println("task " + task.name + " beforeExecute at " + timeMillis);
    }

    @Override
    void afterExecute(Task task, TaskState state) {
        long endTime = System.currentTimeMillis();
        long spendTime = endTime - timeMillis;
        println("task " + task.name + " afterExecute at " + endTime + " spendTime " + spendTime)
        if (spendTime > 50) {
            println("addTaskName " + task.name + " spend " + spendTime)
            if (taskMap.containsKey(spendTime)) {
                taskMap.put(spendTime, taskMap.get(spendTime) + "----" + getTaskInfo(task))
            } else {
                taskMap.put(spendTime, getTaskInfo(task));
            }
            mTimeList.add(spendTime)
        }
    }

    String getTaskInfo(Task task) {
        return "" + task.project + "/" + task.name
    }

    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    @Override
    void buildFinished(BuildResult result) {
        println("buildFinished" + taskMap.size())
        long sum = 0;
        mTimeList.sort(new Comparator<Long>() {
            @Override
            int compare(Long o1, Long o2) {
                return o1 - o2
            }
        })
        mTimeList.forEach(new Consumer<Long>() {
            @Override
            void accept(Long aLong) {
                sum += aLong;
                println("task " + taskMap.get(aLong) + " spendTime " + aLong)
            }
        })
        println("total " + sum)
    }

}

