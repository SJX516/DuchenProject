package com.duchen.annotation.processer;

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

public class ProxyInfo {

    private String packageName;

    private String proxyClassName;

    private TypeElement typeElement;

    public Map<Integer, VariableElement> injectVariables = new HashMap<>();

    public int contentViewId;

    public static final String PROXY = "ViewInject";

    public ProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(typeElement);

        String packageName = packageElement.getQualifiedName().toString();
        String className = ClassValidator.getClassName(typeElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + "$$" + PROXY;
    }

    public String generateJavaCode() {
        StringBuilder builder = new StringBuilder();
        builder.append("//Generated code. Do not modify!\n");
        builder.append("package ").append(packageName).append(";\n\n");

        builder.append("import com.duchen.annotation.*;\n\n");

        builder.append("public class ").append(proxyClassName).append(" implements ").append(ProxyInfo.PROXY).append
                ("<").append(typeElement.getQualifiedName()).append(">");
        builder.append("{\n");

        generateMethods(builder);

        builder.append("\n");
        builder.append("}\n");
        return builder.toString();
    }

    private void generateMethods(StringBuilder builder) {
        builder.append("@Override\n");
        builder.append("public void inject(").append(typeElement.getQualifiedName()).append(" host, Object source) " +
                "{\n");

        StringBuilder ifBuilder = new StringBuilder();
        StringBuilder elseBuilder = new StringBuilder();

        for (int id : injectVariables.keySet()) {
            VariableElement variableElement = injectVariables.get(id);
            String name = variableElement.getSimpleName().toString();
            String type = variableElement.asType().toString();

            ifBuilder.append("host.").append(name).append(" = ");
            ifBuilder.append("(").append(type).append(")(((android.app.Activity)source).findViewById(").append(id)
                    .append("));");

            elseBuilder.append("host.").append(name).append(" = ");
            elseBuilder.append("(").append(type).append(")(((android.view.View)source).findViewById(").append(id)
                    .append("));");


        }
        // if
        builder.append(" if(source instanceof android.app.Activity) {\n");
        // 设置ContentView
        if (contentViewId != 0) {
            builder.append("host.setContentView(").append(contentViewId).append(");\n");
        }
        builder.append(ifBuilder);
        // else
        // 如果是View类型，不用设置ContentView
        builder.append("\n}\nelse {\n");
        builder.append(elseBuilder);
        builder.append("\n}\n");
        builder.append("};");
    }

    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }
}
