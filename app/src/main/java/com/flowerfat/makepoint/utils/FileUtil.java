package com.flowerfat.makepoint.utils;

import android.app.Application;
import android.os.Environment;

import com.flowerfat.makepoint.MyApplication;

import org.apache.http.util.EncodingUtils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by bigflower on 2018/3/17.
 */

public class FileUtil {
    public static final String FILE_NAME = "point_list.txt";

    //写数据
    public static void writeFile(String fileName, String writestr){
        try{
            FileOutputStream fout = MyApplication.getInstance().openFileOutput(fileName, MODE_PRIVATE);
            byte [] bytes = writestr.getBytes();
            fout.write(bytes);
            fout.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
//        SpInstance.get().pString(FILE_NAME, writestr);
    }
    //读数据
    public static String readFile(String fileName){
        String result = null;
        try {
            FileInputStream fileInputStream;
            fileInputStream = MyApplication.getInstance().openFileInput(fileName);

            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte[] bufffer = new byte[fileInputStream.available()];
            int len = 0;
            while ((len = fileInputStream.read(bufffer)) != -1) {
                bout.write(bufffer, 0, len);
            }
            byte[] content = bout.toByteArray();
            result = new String(content);

            fileInputStream.close();
        } catch (Exception e){

        }
//        result = SpInstance.get().gString(FILE_NAME);

        return result;
    }
}
