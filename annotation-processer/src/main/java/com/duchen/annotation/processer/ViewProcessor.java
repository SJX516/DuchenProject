package com.duchen.annotation.processer;

import com.duchen.annotation.BindView;
import com.duchen.annotation.ContentView;
import com.google.auto.service.AutoService;

import java.io.IOError;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class ViewProcessor extends AbstractProcessor {

    //日志打印类
    private Messager messager;

    //元素工具类
    private Elements elementsUtils;

    //保存所有的要生成的注解文件信息
    private Map<String, ProxyInfo> mProxyMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        messager = processingEnvironment.getMessager();
        elementsUtils = processingEnvironment.getElementUtils();

        Map<String, String> map = processingEnvironment.getOptions();
        messager.printMessage(Diagnostic.Kind.NOTE, "processingEnvironment.getOptions()");
        for (String key : map.keySet()) {
            messager.printMessage(Diagnostic.Kind.NOTE, "key:" + key + "  value:" + map.get(key));
        }
    }

    //用来设置支持的注解类型
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
        supportTypes.add(ContentView.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.NOTE, "process...");
        mProxyMap.clear();

        Set<? extends Element> elesWidthBind = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elesWidthBind) {
            checkAnnotationValid(element, BindView.class);
            VariableElement variableElement = (VariableElement) element;
            TypeElement typeElement = (TypeElement) variableElement.getEnclosingElement();
            String fqClassName = typeElement.getQualifiedName().toString();
            ProxyInfo proxyInfo = mProxyMap.get(fqClassName);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(elementsUtils, typeElement);
                mProxyMap.put(fqClassName, proxyInfo);
            }
            BindView bindView = element.getAnnotation(BindView.class);
            int id = bindView.value();
            proxyInfo.injectVariables.put(id, variableElement);
        }

        Set<? extends Element> contentAnnotations = roundEnvironment.getElementsAnnotatedWith(ContentView.class);
        for (Element element : contentAnnotations) {
            TypeElement typeElement = (TypeElement) element;
            String fqClassName = typeElement.getQualifiedName().toString();
            ProxyInfo proxyInfo = mProxyMap.get(fqClassName);
            if (proxyInfo == null) {
                proxyInfo = new ProxyInfo(elementsUtils, typeElement);
                mProxyMap.put(fqClassName, proxyInfo);
            }
            ContentView contentView = element.getAnnotation(ContentView.class);
            proxyInfo.contentViewId = contentView.value();
        }

        // 循环生成源文件
        for (String key : mProxyMap.keySet()) {
            ProxyInfo proxyInfo = mProxyMap.get(key);
            try {
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(proxyInfo.getProxyClassFullName(),
                        proxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                error(proxyInfo.getTypeElement(), "Unable to write injector for type %s: %s ", proxyInfo.getTypeElement(), e.getMessage());
            }
        }

        return true;
    }

    private boolean checkAnnotationValid(Element element, Class<BindView> bindViewClass) {
        if (element.getKind() != ElementKind.FIELD) {
            error(element, "%s() must can be delared on field.", bindViewClass.getSimpleName());
            return false;
        }
        if (ClassValidator.isPrivate(element)) {
            error(element, "%s() can not be private.", bindViewClass.getSimpleName());
            return false;
        }
        return true;
    }

    private void error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.NOTE, message, element);
    }
}
