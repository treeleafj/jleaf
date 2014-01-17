package org.search;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ClassData{

	static final private int MAGIC = 0xCAFEBABE;

	private int minorVersionNo;

	private int majorVersionNo;

	private int len;

	private String className;

	private String simpleClassName;

	private String filePath;

	private ClassData() {		

	}
	
	public static ClassData load(File file) throws Exception{
		DataInputStream in = null;
		try {
			in = new DataInputStream(new FileInputStream(file));
			if (in.readInt() != MAGIC) {
				// 不是一个.class文件
				throw new Exception("Not a class file");
			}

			ClassData classData = new ClassData();

			classData.minorVersionNo = in.readUnsignedShort();// 次版本号
			classData.majorVersionNo = in.readUnsignedShort();// 主版本号
			classData.len = in.readUnsignedShort();// 长度
			in.readByte();// CLASS=7
			in.readUnsignedShort();// 忽略这个地方
			in.readByte();// UTF8=1
			String name = in.readUTF();// 类的名字!!!
			classData.className = name.replaceAll("/", ".");

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

	public int getMinorVersionNo() {
		return minorVersionNo;
	}

	public int getMajorVersionNo() {
		return majorVersionNo;
	}

	public String getClassName() {
		return className;
	}

	public String getSimpleClassName() {
		return simpleClassName;
	}

	public String getFilePath() {
		return filePath;
	}

	public int getLen() {
		return len;
	}

}
