package com.application.base.utils.common;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩和解压
 * 
 * @author bruce
 *
 */
public class ZipUtil {
	
	/**
	 * 压缩文件
	 * @param zipFileName : 压缩的zip名字.
	 * @param inputFile : 输入的文件名字.
	 * @throws Exception
	 */
	public static void zip(String zipFileName, String inputFile)
			throws Exception {
		File f = new File(inputFile);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		zip(out, f, null);
		System.out.println("zip done");
		out.close();
	}

	//文件压缩
	public static void zip(String zipFileName, List<String> inputFile)
			throws Exception {
		Iterator<String> iterator = inputFile.iterator();
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
		for (; iterator.hasNext();) {
			File f = new File(iterator.next());
			zip(out, f, f.getName());
			System.out.println("zip done");
		}
		out.close();
	}

	/**
	 * 压缩主方法
	 * @param out 输出流
	 * @param f 文件
	 * @param base 字符
	 * @throws Exception
	 */
	private static void zip(ZipOutputStream out, File f, String base)
			throws Exception {
		System.out.println("zipping " + f.getAbsolutePath());
		if (f.isDirectory()) {
			File[] fc = f.listFiles();
			if (base != null)
				out.putNextEntry(new ZipEntry(base + "/"));
			base = (base == null ? "" : base + "/");
			for (int i = 0; i < fc.length; i++) {
				zip(out, fc[i], base + fc[i].getName());
			}
		} else {
			out.putNextEntry(new ZipEntry(base));
			FileInputStream in = new FileInputStream(f);
			int b;
			while ((b = in.read()) != -1)
				out.write(b);
			in.close();
		}
	}

	// 解压文件
	public static void unzip(String zipFileName, String outputDirectory)
			throws Exception {
		ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
		ZipEntry z;
		while ((z = in.getNextEntry()) != null) {
			String name = z.getName();
			if (z.isDirectory()) {
				name = name.substring(0, name.length() - 1);
				File f = new File(outputDirectory + File.separator + name);
				f.mkdir();
				System.out.println("MD " + outputDirectory + File.separator	+ name);
			} else {
				System.out.println("unziping " + z.getName());
				File f = new File(outputDirectory + File.separator + name);
				f.createNewFile();
				FileOutputStream out = new FileOutputStream(f);
				int b;
				while ((b = in.read()) != -1)
					out.write(b);
				out.close();
			}
		}
		in.close();
	}
}