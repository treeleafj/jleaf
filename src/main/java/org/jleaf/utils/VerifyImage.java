package org.jleaf.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * 验证码图片生成类
 * @author leaf
 */
public class VerifyImage {

	private int width = 100;// 图片宽度
	private int height = 36;// 图片高度

	public VerifyImage() {
	}

	public VerifyImage(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	/**
	 * 生成一个随机数的验证码
	 * @param count 生成的随机数
	 * @return
	 */
	public String createVerify(int count){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int ic = random.nextInt(58) + 65;
			if (ic > 90 && ic < 97) {
				i--;
				continue;
			}
			char c = (char) (ic);
			sb.append(c);
		}
		return sb.toString();
	}

	/**
	 * 生成一个随机数的验证码
	 * @return
	 */
	public String createVerify(){
		return  createVerify(4);
	}
	
	/**
	 * @param verify 验证码
	 * @param out 输出流
	 * @return 生成的验证码字符串
	 */
	public void createVerifyImage(String verify,OutputStream out) throws IOException {

		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = img.getGraphics();// 画板对象
		g.setColor(new Color(240, 240, 240));
		g.setFont(new Font("宋体", Font.ITALIC + Font.BOLD, 26));
		g.fillRect(0, 0, width, height);
		
		for (int i = 0; i < verify.length(); i++) {
			char c = verify.charAt(i);
			int red = (int) (Math.random() * 256);
			int green = (int) (Math.random() * 256);
			int blue = (int) (Math.random() * 256);
			g.setColor(new java.awt.Color(red, green, blue));
			g.drawString(String.valueOf(c), width / 4 * i, height / 2 + 8);
		}
		
		g.dispose();// 释放画板资源
		// 将图片以流的方式输出到response的输出流,即发送给浏览器
		ImageIO.write(img, "GIF", out);
	}
	
	public void setImageSize(int width,int height){
		this.width = width;
		this.height = height;
	}

}
