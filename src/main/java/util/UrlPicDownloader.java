package util;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 使用URLConnection下载文件或图片并保存到本地。
 * 
 * @author 老紫竹(laozizhu.com)
 */
public class UrlPicDownloader
{
    public static void main(String[] args) throws Exception
    {
        download("http://www.laozizhu.com/images/logo.gif", "laozizhu.com.gif");
    }

    /**
     * 下载文件到本地
     * 
     * @param urlString
     *            文件地址
     * @param filename
     *            本地保存路径
     * @return 文件大小
     * @throws Exception
     */

    public static int download(String urlString, String filename)
            throws Exception
    {

        int totalLength = 0;
        InputStream is = null;
        // 输出的文件流
        OutputStream os = null;
        try
        {
            os = new FileOutputStream(filename);
            // 构造URL
            URL url = new URL(urlString);
            // 打开连接
            URLConnection con = url.openConnection();
            con.setConnectTimeout(20000);
            con.setReadTimeout(60000);

            // 输入流
            is = con.getInputStream();
            // 1K的数据缓冲
            byte[] bs = new byte[1024 * 10];
            // 读取到的数据长度
            int len;

            // 开始读取
            while ((len = is.read(bs)) != -1)
            {
                totalLength += len;
                os.write(bs, 0, len);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        finally
        {
            // 完毕，关闭所有链接
            if(os != null)
                os.close();
            if(is != null)
                is.close();
        }
        return totalLength;
    }
}