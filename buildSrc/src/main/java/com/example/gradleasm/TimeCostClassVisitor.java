package com.example.gradleasm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class TimeCostClassVisitor extends ClassVisitor implements Opcodes {
    private String mPackage;//包名
    private String mCurClassName;//当前访问的类的全限定名
    private boolean isExcludeOtherPackage;//是否排除不属于package的类

    public TimeCostClassVisitor(ClassVisitor classVisitor) {
        super(ASM7, classVisitor);
        mPackage = TimerPlugin.sPackageName;
        if(mPackage.length() > 0){
            mPackage = mPackage.replace(".", "/");
        }
        isExcludeOtherPackage = mPackage.length() > 0;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        mCurClassName = name;
        System.out.println("TimeCostClassVisitor visit mCurClassName is " + mCurClassName);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        if(isExcludeOtherPackage){
            //如果该方法对应的类在package中就处理
            if(mCurClassName.startsWith(mPackage) && !"<init>".equals(name)){
                return new TimeCostMethodVisitor(methodVisitor, access, descriptor);
            }
        }else {
            if(!"<init>".equals(name)){
                return new TimeCostMethodVisitor(methodVisitor, access, descriptor);
            }
        }
        return methodVisitor;
    }
}
