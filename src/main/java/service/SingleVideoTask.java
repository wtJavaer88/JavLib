package service;

import bean.JVideo;
import dao.DbSaveDao;

public class SingleVideoTask implements Runnable
{
    String url;

    public SingleVideoTask(String url)
    {
        this.url = url;
    }

    public void run()
    {
        Logger.logGlobal("线程:" + Thread.currentThread().getName() + " url:"
                + url + "开始!");

        try
        {
            JVideo jVideo = new VideoService().getVideo(url);
            try
            {
                DbSaveDao.insertWithVideo(jVideo);
                System.out.println("线程:" + Thread.currentThread().getName()
                        + " url:" + url + "保存完毕!");
                Logger.logGlobal("线程:" + Thread.currentThread().getName()
                        + " url:" + url + "保存完毕!");
            }
            catch (Exception e)
            {
                Logger.logGlobal(jVideo.getId() + "插入数据库失败:" + e.getMessage());
                e.printStackTrace();
            }

        }
        catch (Exception e)
        {
            Logger.logGlobal("线程:" + Thread.currentThread().getName() + "异常:"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

}