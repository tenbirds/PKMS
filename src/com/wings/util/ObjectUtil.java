package com.wings.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 */
public class ObjectUtil {

	private static Log log = LogFactory.getLog(ObjectUtil.class);

	private ObjectUtil() {

	}

	/**
	 * 클래스명으로 객체를 로딩한다.
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public static Class<?> loadClass(String className) throws ClassNotFoundException, Exception {

		Class<?> clazz = null;
		try {
			clazz = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundException();
		} catch (Exception e) {
			throw new Exception(e);
		}

		if (clazz == null) {
			clazz = Class.forName(className);
		}

		return clazz;

	}

	/**
	 * 클래스명으로 객체를 로드한 후 인스턴스화 한다.
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public static Object instantiate(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
		Class<?> clazz;

		try {
			clazz = loadClass(className);
			return clazz.newInstance();
		} catch (ClassNotFoundException e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is can not instantialized.");
			throw new ClassNotFoundException();
		} catch (InstantiationException e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is can not instantialized.");
			throw new InstantiationException();
		} catch (IllegalAccessException e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is not accessed.");
			throw new IllegalAccessException();
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is not accessed.");
			throw new Exception(e);
		}
	}

	/**
	 * 클래스명으로 파라매터가 있는 클래스의 생성자를 인스턴스화 한다. 예) Class <?> clazz =
	 * EgovObjectUtil.loadClass(this.mapClass); Constructor <?>
	 * constructor = clazz.getConstructor(new Class []{DataSource.class,
	 * String.class}); Object [] params = new Object []{getDataSource(),
	 * getUsersByUsernameQuery()};
	 * 
	 * this.usersByUsernameMapping = (EgovUsersByUsernameMapping)
	 * constructor.newInstance(params);
	 * 
	 * @param className
	 * @return
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws Exception
	 */
	public static Object instantiate(String className, String[] types, Object[] values) throws ClassNotFoundException, InstantiationException, IllegalAccessException, Exception {
		Class<?> clazz;
		Class<?>[] classParams = new Class[values.length];
		Object[] objectParams = new Object[values.length];

		try {
			clazz = loadClass(className);

			for (int i = 0; i < values.length; i++) {
				classParams[i] = loadClass(types[i]);
				objectParams[i] = values[i];
			}

			Constructor<?> constructor = clazz.getConstructor(classParams);
			return constructor.newInstance(values);

		} catch (ClassNotFoundException e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is can not instantialized.");
			throw new ClassNotFoundException();
		} catch (InstantiationException e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is can not instantialized.");
			throw new InstantiationException();
		} catch (IllegalAccessException e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is not accessed.");
			throw new IllegalAccessException();
		} catch (Exception e) {
			if (log.isErrorEnabled())
				log.error(className + " : Class is not accessed.");
			throw new Exception(e);
		}
	}

	/**
	 * 객체가 Null 인지 확인한다.
	 * 
	 * @param object
	 * @return Null인경우 true / Null이 아닌경우 false
	 */
	public static boolean isNull(Object object) {
		return ((object == null) || object.equals(null));
	}

	public static void copyObjectValue(Object source, Object target, String... args) throws Exception {
		for (String name : args) {
			try {
				Field field = source.getClass().getDeclaredField(name);
				if (field != null) {
					Object value = ObjectUtil.getObjectFieldValue(source, field.getName());
					ObjectUtil.setObjectFieldValue(target, field.getName(), value);
				}
			} catch (Exception e) {
			}
		}
	}

	public static Object getObjectFieldValue(Object object, String fieldName) throws Exception {
		final Method[] methods = object.getClass().getMethods();
		for (int i = 0; i < methods.length; ++i) {
			if (isEqualsMethodName(methods[i].getName(), fieldName, "get")) {
				try {
					methods[i].setAccessible(true);
					return methods[i].invoke(object);
				} catch (IllegalAccessException ex) {
				}
			}
		}
		return null;
	}

	public static void setObjectFieldValue(Object object, String fieldName, Object value) throws Exception {
		final Method[] methods = object.getClass().getMethods();
		for (int i = 0; i < methods.length; ++i) {
			if (isEqualsMethodName(methods[i].getName(), fieldName, "set")) {
				try {
					methods[i].setAccessible(true);
					methods[i].invoke(object, value);
				} catch (IllegalAccessException ex) {
					throw ex;
				}
			}
		}
	}

	private static boolean isEqualsMethodName(String methodName, String fieldName, String type) {
		try {
			if (methodName.startsWith(type) && methodName.substring(3).toLowerCase().equals(fieldName.toLowerCase())) {
				return true;
			}
		} catch (Exception ex) {
		}
		return false;
	}
}
