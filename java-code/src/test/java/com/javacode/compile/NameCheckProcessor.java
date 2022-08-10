package com.javacode.compile;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes("*")
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class NameCheckProcessor extends AbstractProcessor {

    private NameCheck nameCheck;

    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        nameCheck = new NameCheck(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (roundEnv.processingOver()) {
            for (Element element : roundEnv.getRootElements()) {
                nameCheck.checkNames(element);
            }
        }
        return false;
    }
}
