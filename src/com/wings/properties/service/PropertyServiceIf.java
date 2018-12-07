package com.wings.properties.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

/**
 * 
 * 
 * 공통 Property 를 처리하는 서비스의 인터페이스
 * 
 * @author : skywarker
 * @Date : 2012. 4. 10.
 * 
 */
public interface PropertyServiceIf {

	public boolean getBoolean(String name);

	public boolean getBoolean(String name, boolean def);

	public double getDouble(String name);

	public double getDouble(String name, double def);

	public float getFloat(String name);

	public float getFloat(String name, float def);

	public int getInt(String name);

	public int getInt(String name, int def);

	public Iterator<?> getKeys();

	public Iterator<?> getKeys(String prefix);

	public long getLong(String name);

	public long getLong(String name, long def);

	public String getString(String name);

	public String getString(String name, String def);

	public String[] getStringArray(String name);

	public Vector<?> getVector(String name);

	public Vector<?> getVector(String name, Vector<?> def);

	public void refreshPropertyFiles() throws Exception;

	public Collection<?> getAllKeyValue();

}
