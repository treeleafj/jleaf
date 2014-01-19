package org.search;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 存放类型的一些基本数据
 * 
 * @author leaf
 * @date 2014-1-19 下午4:58:52
 */
public class ClassData {

	static final private int MAGIC = 0xCAFEBABE;

	/**
	 * 类名
	 */
	private String className;

	/**
	 * 类的简单名字
	 */
	private String simpleClassName;

	public ClassData() {
	}

	public static ClassData load(File file) throws Exception {
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(file));
			if (in.readInt() != MAGIC) {
				// 不是一个.class文件
				throw new Exception("Not a class file");
			}
			ClassData classData = new ClassData();
			in.readUnsignedShort();// 次版本号
			in.readUnsignedShort();// 主版本号
			in.readUnsignedShort();// 长度
			in.readByte();// CLASS=7
			in.readUnsignedShort();// 忽略这个地方
			in.readByte();// UTF8=1
			String name = in.readUTF();// 类的名字!!!
			classData.className = name.replaceAll("/", ".");
			int index = classData.className.lastIndexOf(".");
			classData.simpleClassName = classData.className
					.substring(index + 1);
			return classData;
		} catch (IOException e) {
			throw e;
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

	public static ClassData load(String filename) throws Exception {
		return load(new File(filename));
	}

	public String getClassName() {
		return className;
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public void setSimpleClassName(String simpleClassName) {
		this.simpleClassName = simpleClassName;
	}

	@Override
	public String toString() {
		return this.className;
	}

}