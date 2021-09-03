package com.trick.reflection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.reflections.scanners.*;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import static org.reflections.ReflectionUtils.*;

/**
 * 1. 获取某个类型的全部子类
 * 2. 只要类型、构造器、方法，字段上带有特定注解，便能获取带有这个注解的全部信息（类型、构造器、方法，字段）
 * 3. 获取所有能匹配某个正则表达式的资源
 * 4. 获取所有带有特定签名的方法，包括参数，参数注解，返回类型
 * 5. 获取所有方法的名字
 * 6. 获取代码里所有字段、方法名、构造器的使用
 */
@DisplayName("reflection 测试类")
public class ReflectionTest {

	@Test
	public void test() throws NoSuchMethodException {
		Reflections reflections = new Reflections(
				new ConfigurationBuilder()
						.setUrls(ClasspathHelper.forPackage("com.trick"))
						.setScanners(
								new SubTypesScanner(), // 默认
								new TypeAnnotationsScanner(), // 默认
								new MethodAnnotationsScanner(),
								new ResourcesScanner(),
								new FieldAnnotationsScanner(),
								new MethodParameterScanner(),
								new MethodParameterNamesScanner()
						)
						.filterInputsBy(new FilterBuilder().includePackage("java.lang"))
		);

		Set<Class<? extends BeanFactory>> subTypesOf = reflections.getSubTypesOf(BeanFactory.class);
		Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Service.class);
		Set<String> resources = reflections.getResources(Pattern.compile(".*\\.properties"));
		Set<Method> methodsAnnotatedWith = reflections.getMethodsAnnotatedWith(Override.class);
		Set<Constructor> constructorsAnnotatedWith = reflections.getConstructorsAnnotatedWith(PostConstruct.class);
		Set<Field> fieldsAnnotatedWith = reflections.getFieldsAnnotatedWith(Autowired.class);
		Set<Method> methodsMatchParams = reflections.getMethodsMatchParams(String.class, String.class);
		Set<Method> methodsReturn = reflections.getMethodsReturn(void.class);
		Set<Method> methodsWithAnyParamAnnotated = reflections.getMethodsWithAnyParamAnnotated(Autowired.class);
		List<String> methodParamNames = reflections.getMethodParamNames(Object.class.getMethod("equals"));
		Set<Member> equals = reflections.getMethodUsage(Object.class.getMethod("equals"));
	}

	@Test
	public void utilTest() {
		Set<Method> methods = getAllMethods(
				Object.class,
				withModifier(Modifier.PUBLIC),
				withPrefix("wait"),
				withParameters(Long.class)
		);
		Set<Field> fields = getAllFields(
				ArrayList.class,
				withAnnotation(Autowired.class),
				withTypeAssignableTo(Number.class)
		);
	}

}
