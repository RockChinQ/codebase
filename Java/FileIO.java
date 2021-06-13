package main.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FileIO {

    public static final int MAX_LENGTH=Integer.MAX_VALUE;

    static class FileTooBigException extends Exception{
        public FileTooBigException(){
            super();
        }
        public FileTooBigException(String message){
            super(message);
        }
        public FileTooBigException(String message, Throwable cause) {
            super(message, cause);
        }
        public FileTooBigException(Throwable cause) {
            super(cause);
        }
    }

    /**
     * Read specific file
     * @param filePath file
     * @return file content string
     * @throws Exception
     */
    public static String read(String filePath)throws Exception{
        return read(filePath,StandardCharsets.UTF_8);
    }

    /**
     * Use specific charset to read a file
     * @param filePath file
     * @param charset provided charset
     * @return file content string
     * @throws Exception
     */
    public static String read(String filePath, Charset charset)throws Exception{
        File file=new File(filePath);
        long fileLength=file.length();
        if (fileLength>=MAX_LENGTH){
            throw new FileTooBigException(file+" is bigger than MAX_LENGTH("+MAX_LENGTH+"):"+fileLength);
        }
        byte[] bufByte =new byte[(int)fileLength];
        FileInputStream fis=new FileInputStream(file);
        fis.read(bufByte);
        fis.close();
        return new String(bufByte, charset);
    }

    /**
     * Write to specific file
     * @param filePath file
     * @param content file content
     * @throws Exception
     */
    public static void write(String filePath,String content)throws Exception{
        write(filePath,content,StandardCharsets.UTF_8,false);
    }

    /**
     * Choose if provided content will be appended to the specific file
     * @param filePath file
     * @param content file content
     * @param append append to the end or not
     * @throws Exception
     */
    public static void write(String filePath,String content,boolean append)throws Exception{
        write(filePath, content,StandardCharsets.UTF_8, append);
    }

    /**
     * Provide all param to write a file
     * @param filePath file
     * @param content file content
     * @param charset charset
     * @param append append to the end or not
     * @throws Exception
     */
    public static void write(String filePath,String content,Charset charset,boolean append)throws Exception{
        if (!append) {
            File file = new File(filePath);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buf = content.getBytes(charset);
            fos.write(buf);
            fos.flush();
            fos.close();
        }else {
            write(filePath,read(filePath,charset)+content,charset,false);
        }
    }


}
