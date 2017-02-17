package com.application.base.utils.common;


import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
	static int countFiles = 0;// 声明统计文件个数的变量
    static int countFolders = 0;// 声明统计文件夹的变量
	private static String SLASH = "/";
	private static String DOT=".";
	/**
	 * 读取项目中Resources文件夹下面的文件
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readerResourcesFile(String filePath) {
		try {
			StringBuffer sb = new StringBuffer();
			FileReader freader = new FileReader(new File(
					PathUtil.getClassResources() + "/" + filePath));
			BufferedReader buffer = new BufferedReader(freader);
			String str_line = buffer.readLine();
			while (str_line != null) {
				sb.append(str_line);
				sb.append("\n");
				str_line = buffer.readLine();
			}
			buffer.close();
			freader.close();

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//写入文件
	public static boolean writeFile(String filePath , String content) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				String array[] = filePath.split(SLASH);
				StringBuffer buff = new StringBuffer();
				for(int i=0;i<array.length;i++){
					buff.append(array[i]).append(SLASH);
					if(i>0 && array[i].indexOf(DOT) == -1){
						String path = buff.toString().substring(0, buff.toString().length());
						File mkdirFile = new File(path);
						if(!mkdirFile.exists()  && !mkdirFile.isDirectory()) {
							mkdirFile.mkdir();
							System.out.println("创建文件夹："+path+" 成功");
						}
					}
				}
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//写入文件
	public static boolean writeFileNew(String filePath , String content) {
			try {
				File file = new File(filePath);
				if (!file.exists()) {
					file.createNewFile();
				}
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(content);
				bw.close();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return false;
		}
	
	public static File[] searchFile(File folder, final String fileName) {// 递归查找包含关键字的文件
		List<String> pathList = new ArrayList<String>();
        File[] subFolders = folder.listFiles(new FileFilter() {// 运用内部匿名类获得文件
            public boolean accept(File pathname) {// 实现FileFilter类的accept方法
                if (pathname.isDirectory() || (pathname.isFile() && pathname.getName().toLowerCase().equals(fileName.toLowerCase()))){// 目录或文件包含关键字
                	return true;
                }
                return false;
            }
        }); 
        List<File> result = new ArrayList<File>();// 声明一个集合
        for (int i = 0; i < subFolders.length; i++) {// 循环显示文件夹或文件
            if (subFolders[i].isFile()) {// 如果是文件则将文件添加到结果列表中
            	pathList.add(subFolders[i].getAbsolutePath());
                result.add(subFolders[i]);
            } else {// 如果是文件夹，则递归调用本方法，然后把所有的文件加到结果列表中
                File[] foldResult = searchFile(subFolders[i], fileName);
                for (int j = 0; j < foldResult.length; j++) {// 循环显示文件
                	pathList.add(foldResult[j].getAbsolutePath());
                }
            }
        }
        File files[] = new File[result.size()];// 声明文件数组，长度为集合的长度
        result.toArray(files);// 集合数组化
        return files;
    }
	/**
	 * 创建目录
	 * @param destDirName
	 *            目标目录名
	 * @return 目录创建成功返回true，否则返回false
	 */
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {
			destDirName = destDirName + File.separator;
		}
		// 创建单个目录
		if (dir.mkdirs()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myDelFile = new File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();

		}

	}

	/**
	 * @param folderPath 文件路径 (只删除此路径的最末路径下所有文件和文件夹)
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); 	// 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			myFilePath.delete(); 		// 删除空文件夹
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除指定文件夹下所有文件
	 * @param path 文件夹完整绝对路径
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);	// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);	// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 读取到字节数组0
	 * 
	 * @param filePath //路径
	 * @throws IOException
	 */
	public static byte[] getContent(String filePath) throws IOException {
		File file = new File(filePath);
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length
				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		fi.close();
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file ，"+file.getName());
		}
		return buffer;
	}

	/**
	 * 读取到字节数组1
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(f));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;
			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}
			return bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			bos.close();
		}
	}

	/**
	 * 读取到字节数组2
	 * 
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray2(String filePath) throws IOException {

		File f = new File(filePath);
		if (!f.exists()) {
			throw new FileNotFoundException(filePath);
		}

		FileChannel channel = null;
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(f);
			channel = fs.getChannel();
			ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
			while ((channel.read(byteBuffer)) > 0) {
				// do nothing
				// System.out.println("reading");
			}
			return byteBuffer.array();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				channel.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				fs.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Mapped File way MappedByteBuffer 可以在处理大文件时，提升性能
	 * 
	 * @return
	 * @throws IOException
	 */
	public static byte[] toByteArray3(String filePath) throws IOException {

		FileChannel fc = null;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filePath, "r");
			fc = rf.getChannel();
			MappedByteBuffer byteBuffer = fc.map(MapMode.READ_ONLY, 0,
					fc.size()).load();
			//System.out.println(byteBuffer.isLoaded());
			byte[] result = new byte[(int) fc.size()];
			if (byteBuffer.remaining() > 0) {
				// System.out.println("remain");
				byteBuffer.get(result, 0, byteBuffer.remaining());
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				rf.close();
				fc.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 *共文件项目调用，决定将数据存入那张表中或在那张表中取
	 */
	public static int decideStoreTable(int id){
		int tableOrderNum=id%20+1;
		return tableOrderNum;
	}
}
