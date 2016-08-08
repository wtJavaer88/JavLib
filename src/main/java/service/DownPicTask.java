package service;

import util.TextFormatUtil;
import util.UrlPicDownloader;

import com.wnc.basic.BasicFileUtil;

public class DownPicTask implements Runnable
{
    String saveFilePath;
    String imgsrc;

    final String saveDir = "D:\\txt\\javlib\\pic\\";

    public DownPicTask(String filename, String imgsrc)
    {
        this.saveFilePath = saveDir
                + TextFormatUtil.getValidTitle(String.valueOf(filename))
                + ".jpg";
        this.imgsrc = imgsrc;
    }

    public void run()
    {
        // 进来首先删除旧文件
        BasicFileUtil.deleteFile(saveFilePath);
        Logger.logGlobal(Thread.currentThread().getName() + "开始下载:" + imgsrc);
        long length = Long.MIN_VALUE;
        String errMsg = "";
        try
        {
            length = UrlPicDownloader.download(imgsrc, saveFilePath);
            System.out.println("文件大小" + length);
        }
        catch (Exception e)
        {
            errMsg = e.getMessage();
            e.printStackTrace();
        }

        if(BasicFileUtil.getFileSize(saveFilePath) == length)
        {
            Logger.logGlobal(Thread.currentThread().getName() + "下载成功:"
                    + imgsrc);
        }
        else
        {
            boolean b1 = BasicFileUtil.isExistFile(saveFilePath);
            boolean b2 = BasicFileUtil.deleteFile(saveFilePath);
            if(b1 && !b2)
            {
                errMsg = "删除失败";
            }
            Logger.logGlobal(Thread.currentThread().getName() + "下载未完成:"
                    + imgsrc + " errMsg:" + errMsg);
        }
    }
}