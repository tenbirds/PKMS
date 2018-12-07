/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wings.properties.service;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.collections.ExtendedProperties;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.Assert;

/**
 * 
 * 
 * 공통 Property 를 처리하는 서비스의 구현 클래스
 * 
 * @author : skywarker
 * @Date : 2012. 4. 10.
 * 
 */
public class PropertyService implements PropertyServiceIf, InitializingBean, DisposableBean, ResourceLoaderAware {

	private ExtendedProperties extendedProperties = null;
	private ResourceLoader resourceLoader = null;

	private Set<?> extFileName;
	private Map<?, ?> properties;

	/**
	 * boolean 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return boolean 타입의 값
	 */
	public boolean getBoolean(String name) {
		return getConfiguration().getBoolean(name);
	}

	/**
	 * boolean 타입의 프로퍼티 값 얻기(디폴트값을 입력받음)
	 * 
	 * @param name
	 *            프로퍼티키
	 * @param def
	 *            디폴트 값
	 * @return boolean 타입의 값
	 */
	public boolean getBoolean(String name, boolean def) {
		return getConfiguration().getBoolean(name, def);
	}

	/**
	 * double 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return double 타입의 값
	 */
	public double getDouble(String name) {
		return getConfiguration().getDouble(name);
	}

	/**
	 * double 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @param def
	 *            디폴트 값
	 * @return double 타입의 값
	 */
	public double getDouble(String name, double def) {
		return getConfiguration().getDouble(name, def);
	}

	/**
	 * float 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return Float 타입의 값
	 */
	public float getFloat(String name) {
		return getConfiguration().getFloat(name);
	}

	/**
	 * float 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @param def
	 *            디폴트 값
	 * @return float 타입의 값
	 */
	public float getFloat(String name, float def) {
		return getConfiguration().getFloat(name, def);
	}

	/**
	 * int 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return int 타입의 값
	 */
	public int getInt(String name) {
		return getConfiguration().getInt(name);
	}

	/**
	 * int 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @param def
	 *            디폴트 값
	 * @return int 타입의 값
	 */
	public int getInt(String name, int def) {
		return getConfiguration().getInt(name, def);
	}

	/**
	 * 프로퍼티 키 목록 읽기
	 * 
	 * @return Key를 위한 Iterator
	 */
	public Iterator<?> getKeys() {
		return getConfiguration().getKeys();
	}

	/**
	 * prefix를 이용한 키 목록 읽기
	 * 
	 * @param prefix
	 *            prefix
	 * @return prefix에 매칭되는 키목록
	 */
	public Iterator<?> getKeys(String prefix) {
		// getConfiguration().values();
		return getConfiguration().getKeys(prefix);
	}

	/**
	 * long 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return long 타입의 값
	 */
	public long getLong(String name) {
		return getConfiguration().getLong(name);
	}

	/**
	 * long 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @param def
	 *            디폴트 값
	 * @return long 타입의 값
	 */
	public long getLong(String name, long def) {
		return getConfiguration().getLong(name, def);
	}

	/**
	 * String 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return String 타입의 값
	 */
	public String getString(String name) {
		return getConfiguration().getString(name);
	}

	/**
	 * String 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @param def
	 *            디폴트 값
	 * @return String 타입의 값
	 */
	public String getString(String name, String def) {
		return getConfiguration().getString(name, def);
	}

	/**
	 * String[] 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return String[] 타입의 값
	 */
	public String[] getStringArray(String name) {
		return getConfiguration().getStringArray(name);
	}

	/**
	 * Vector 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @return Vector 타입의 값
	 */
	public Vector<?> getVector(String name) {
		return getConfiguration().getVector(name);
	}

	/**
	 * Vector 타입의 프로퍼티 값 얻기
	 * 
	 * @param name
	 *            프로퍼티키
	 * @param def
	 *            디폴트 값
	 * @return Vector 타입의 값
	 */
	public Vector<?> getVector(String name, Vector<?> def) {
		return getConfiguration().getVector(name, def);
	}

	/**
	 * 전체 키/값 쌍 얻기
	 * 
	 * @return Vector 타입의 값
	 */
	public Collection<?> getAllKeyValue() {
		return getConfiguration().values();
	}

	/**
	 * egovProperties 얻기
	 * 
	 * @return Properties of requested Service.
	 */
	private ExtendedProperties getConfiguration() {
		return extendedProperties;
	}

	/**
	 * resource 변경시 refresh
	 */
	public void refreshPropertyFiles() throws Exception {

		String fileName = null;

		try {

			Iterator<?> it = extFileName.iterator();

			while (it != null && it.hasNext()) {
				// Get element
				Object element = it.next();
				String enc = null;

				if (element instanceof Map) {
					Map<?, ?> ele = (Map<?, ?>) element;
					enc = (String) ele.get("encoding");
					fileName = (String) ele.get("filename");
				} else {
					fileName = (String) element;
				}
				loadPropertyResources(fileName, enc);
			}

		} catch (Exception e) {
			throw e;

		}
	}

	/**
	 * Bean 초기화 함수로 최초 생성시 필요한 Property 세티처리
	 * 
	 * @throws Exception
	 *             fail to initialize
	 */
	public void afterPropertiesSet() throws Exception {
		try {

			extendedProperties = new ExtendedProperties();

			// 외부파일이 정의되었을때
			if (extFileName != null) {
				refreshPropertyFiles();
			}

			// ----------------------------------------------
			// "properties" 속성이 없는 경우 처리 (2010-11-01)
			// ----------------------------------------------
			if (properties == null) {
				return;
			}
			// //--------------------------------------------

			Iterator<?> it = properties.entrySet().iterator();
			while (it.hasNext()) {

				Map.Entry<?, ?> entry = (Map.Entry<?, ?>) it.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();

				if (key == null || key.equals("")) {
					throw new Exception("error.properties.check.essential");
				}

				extendedProperties.put(key, value);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * extFileName을 지정할 때 Attribute로 정의
	 * 
	 * @param extFileName
	 */
	public void setExtFileName(Set<?> extFileName) {
		this.extFileName = extFileName;
	}

	/**
	 * properties를 지정할 때 Attribute로 정의
	 * 
	 * @param properties
	 */
	public void setProperties(Map<?, ?> properties) {
		this.properties = properties;
	}

	/**
	 * 서비스 종료처리
	 */
	public void destroy() {
		extendedProperties = null;
	}

	/**
	 * 리소스 로더 세팅
	 */
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	/**
	 * 파일위치정보를 가지고 resources 정보 추출
	 * 
	 * @param location
	 *            파일위치
	 * @param encoding
	 *            Encoding 정보
	 * @throws Exception
	 */
	private void loadPropertyResources(String location, String encoding) throws Exception {

		if (resourceLoader instanceof ResourcePatternResolver) {
			try {
				Resource[] resources = ((ResourcePatternResolver) resourceLoader).getResources(location);

				loadPropertyLoop(resources, encoding);
			} catch (IOException ex) {
				throw new BeanDefinitionStoreException("Could not resolve Properties resource pattern [" + location + "]", ex);
			}
		} else {

			Resource resource = resourceLoader.getResource(location);
			loadPropertyRes(resource, encoding);
		}

	}

	/**
	 * 멀티로 지정된 경우 처리를 위해 LOOP 처리
	 * 
	 * @param resources
	 *            리소스정보
	 * @param encoding
	 *            인코딩정보
	 * @throws Exception
	 */
	private void loadPropertyLoop(Resource[] resources, String encoding) throws Exception {
		Assert.notNull(resources, "Resource array must not be null");
		for (int i = 0; i < resources.length; i++) {
			loadPropertyRes(resources[i], encoding);
		}
	}

	/**
	 * 파일 정보를 읽어서 egovProperties에 저장
	 * 
	 * @param resources
	 *            리소스정보
	 * @param encoding
	 *            인코딩정보
	 * @throws Exception
	 */
	private void loadPropertyRes(Resource resource, String encoding) throws Exception {
		ExtendedProperties egovProperty = new ExtendedProperties();
		egovProperty.load(resource.getInputStream(), encoding);
		extendedProperties.combine(egovProperty);
	}
}
