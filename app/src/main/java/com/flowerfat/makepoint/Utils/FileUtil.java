package com.flowerfat.makepoint.Utils;

import android.content.Context;
import android.util.Log;

import com.flowerfat.makepoint.MyApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtil {

    /**
     * 创建文件
     *
     * @param path
     */
    public static void createPath(String path) {
        File filename = new File(path);
        try {
            if (!filename.exists()) {
                filename.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            filename = null;
        }
    }

    /**
     * 创建文件夹 （如果有文件和folderName重名，也不能创建）
     * @param path
     */
    public static void creatFolder(String path){
        File folder;
        folder = new File(path);
        if(!folder.exists()){
            // Notice: 在文件夹的目录结构中，如果有任意一级的目录不存在，则无法创建
//            folder.mkdir();
            // 用mkdirs() 就不会出现上面的问题，可以一路创建到底，所以以后就用这个了
            folder.mkdirs();
        }
    }

    /**
     * 写文件
     *
     * @param data     数据
     * @param fileName 文件名
     */
    public static void write(String data, String fileName) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = MyApplication.getInstance().openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读文件
     *
     * @param fileName 文件名
     * @return
     */
    public static String read(String fileName) {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = MyApplication.getInstance().openFileInput(fileName);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content.toString();
    }

    /**
     * 删除文件
     * 移除目录，目录下必须是空的（即文件夹非空，则不能删除）
     *
     * @param fileName
     */
    public static void delete(String fileName) {
        File file = new File("/data/data/"
                + MyApplication.getInstance().getPackageName() + "/files/"
                + fileName);
        if (file.exists()) {
            Log.e("文件操作", "文件!!!存在");
            file.delete();
        } else {
            Log.e("文件操作", "文件不存在");
        }
    }

    /**
     * 文件是否存在
     *
     * @param fileName 文件名
     * @return
     */
    public static boolean isExistByName(String fileName) {
        return new File("/data/data/"
                + MyApplication.getInstance().getPackageName() + "/files/"
                + fileName).exists();
    }

    public static boolean isExistByPath(String filePath) {
        return new File(filePath).exists();
    }

    /**
     * 更改文件名
     *
     * @param filePath
     * @param newFilePath
     */
    public static void rename(String filePath, String newFilePath) {
        File file = new File(filePath);
        file.renameTo(new File(newFilePath));
    }
}
