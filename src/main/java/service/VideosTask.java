package service;

import java.util.List;

import bean.JVideo;
import dao.DbSaveDao;

public class VideosTask implements Runnable
{
    String starId;
    String starName;

    public VideosTask(String starId, String starName)
    {
        this.starId = starId;
        this.starName = starName;
    }

    public void run()
    {
        Logger.logGlobal("线程:" + Thread.currentThread().getName() + " Star:"
                + starId + "开始!");

        try
        {
            List<JVideo> videos = new JVideosService().getAllByStarId(starId);
            System.out.println(starName + " videos数:" + videos.size());
            Logger.logGlobal(starName + " videos数:" + videos.size());
            for (JVideo jVideo : videos)
            {
                try
                {
                    DbSaveDao.insertWithVideo(jVideo);
                }
                catch (Exception e)
                {
                    Logger.logGlobal(jVideo.getId() + "插入数据库失败:"
                            + e.getMessage());
                    e.printStackTrace();
                }
            }
            System.out.println("线程:" + Thread.currentThread().getName()
                    + " Star:" + starId + "保存完毕!");
            Logger.logGlobal("线程:" + Thread.currentThread().getName()
                    + " Star:" + starId + "保存完毕!");
        }
        catch (Exception e)
        {
            Logger.logGlobal("线程:" + Thread.currentThread().getName() + "异常:"
                    + e.getMessage());
            e.printStackTrace();
        }
    }

}