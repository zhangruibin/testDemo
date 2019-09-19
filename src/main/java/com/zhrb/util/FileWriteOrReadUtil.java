package com.zhrb.util;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.UUID;


/**
 * 
 * @author 中国电信
 * 
 */
public class FileWriteOrReadUtil {

	/**
	 * 文件上传
	 * 
	 * @param filePath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @param databyte
	 *            文件流
	 * @return
	 * @throws Exception
	 *             基本系统异常
	 */
	public static boolean fileUpload(String filePath, String fileName,
			byte[] databyte) throws Exception {
		try {
			// 上传路径
			StringBuffer filaPathAndName = new StringBuffer();
			filaPathAndName.append(filePath).append(File.separator)
					.append(fileName);
			// 创建输出流
			FileOutputStream fos = null;
			// 文件写出主不服
			try {
				// 实类化文件类，构造文件路径
				File writeFile = new File(filePath);
				// 判断文件路径是否存在
				if (!writeFile.exists()) {
					// 创建文件
					boolean ismkdirs = writeFile.mkdirs();
					// 创建文件夹失败
					if (!ismkdirs) {
						return false;
					}
				}
				// 创建流
				fos = new FileOutputStream(filaPathAndName.toString());
				// 写入
				fos.write(databyte);
			} catch (IOException e) {
				// 返回为错误
				return false;
			} finally {
				// 释放
				fos.flush();
				// 关闭
				fos.close();
			}
		} catch (Exception e) {

			throw new RuntimeException("文件写入失败");
		}
		return true;
	}

	/**
	 * 文件读取
	 * 
	 * @param filePath
	 *            文件路径
	 * @param width
	 *            文件大小
	 * @param request
	 *            请求
	 * @return
	 * @throws Exception
	 *             基本系统异常
	 */
	public static boolean fileImagRead(HttpServletRequest request,
                                       HttpServletResponse response, String filePath, int width,
                                       int height, boolean cutFlg) throws Exception {
		// 设置字符集
		request.setCharacterEncoding("utf-8");
		// 设置头字符集
		response.setContentType("text/html;charset=utf-8");
		// 将图形写给浏览器
		response.setContentType("image/jpeg");
		// 发头控制浏览器不要缓存
		response.setDateHeader("expries", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		// 实类化文件类，构造文件路径
		File readFile = new File(filePath);
		// 读取文件流
		Image src = ImageIO.read(readFile); // 构造Image对象

		// 原始图片宽度
		int srcWidth = src.getWidth(null);
		// 原始图片高度
		int srcHeight = src.getHeight(null);

		// 最终图片长度
		int lastWidth = 0;
		// 最终图片高度
		int lastHeight = 0;

		// 是否裁剪
		if (cutFlg) {
			// 原始图片宽是否大于目标图片
			if (srcWidth > width) {
				lastWidth = width;
			} else {
				lastWidth = srcWidth;
			}
			// 原始图片高是否大于目标图片
			if (srcHeight > height) {
				lastHeight = height;
			} else {
				lastHeight = srcHeight;
			}
		} else {
			// 不需要裁剪赋值原始值
			lastWidth = srcWidth;
			lastHeight = srcHeight;
		}

		// 输出的图片流
		BufferedImage tag = new BufferedImage(lastWidth, lastHeight,
				BufferedImage.TYPE_INT_RGB);
		// 绘制缩小后的图
		tag.getGraphics().drawImage(src, 0, 0, lastWidth, lastHeight, null);

		// 将图片写给浏览器
		ImageIO.write(tag, "jpg", response.getOutputStream());

		return true;
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 *            文件路径
	 * 
	 * @return
	 * @throws Exception
	 *             基本系统异常
	 */
	public static boolean fileDelete(String filePath) throws Exception {
		// 实类化文件类，构造文件路径
		File readFile = new File(filePath);

		// 文件存在
		if (readFile.exists()) {
			// 删除文件
			//zhrb-20190529-修复漏洞
			//readFile.delete();
			if (!readFile.delete()){
				System.out.println("删除文件失败！");
			}
		}

		return true;
	}
	
	/**
	 * file转换byte[]
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] fileToByte(File file) throws IOException {
		byte[] fileStream = null;
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1048576);
		byte[] b = new byte[1048576];
		int n;
		while ((n = fis.read(b)) != -1) {
			bos.write(b, 0, n);
		}
		fis.close();
		bos.close();
		fileStream = bos.toByteArray();
		return fileStream;
	}
	
	/**
	 * 产生UUID（36位大写）
	 * @return String
	 */
	public static String getRandomUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "").toUpperCase();
	}
}
