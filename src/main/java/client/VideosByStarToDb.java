package client;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import service.JVideosService;
import service.Logger;
import bean.JVideo;
import dao.DbSaveDao;
import datahelper.AliasHelper;
import db.DbExecMgr;

public class VideosByStarToDb
{
    public VideosByStarToDb()
    {

    }

    public static void main(String[] args)
    {
        AliasHelper.init();
        new VideosByStarToDb().saveByStarId();
    }

    public void saveByStarId()
    {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
                .newFixedThreadPool(20);
        try
        {
            Map map = DbExecMgr
                    .getSelectAllSqlMap("SELECT ID,NAME from JSTAR ORDER BY ID ASC ");
            int size = map.size();
            Map rowMap;
            System.out.println(size);
            for (int i = 1; i <= size; i++)
            {
                rowMap = (Map) map.get(i);
                final String starId = String.valueOf(rowMap.get("ID"));
                final String starName = String.valueOf(rowMap.get("NAME"));
                executor.execute(new VideosTask(starId, starName));
                // List<JVideo> videos = new
                // JVideosService().getAllByStarId(starId);
                // System.out.println(starId + " videos数:" + videos.size());
            }
            executor.shutdown();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    class VideosTask implements Runnable
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
            Logger.logGlobal("线程:" + Thread.currentThread().getName()
                    + " Star:" + starId + "开始!");

            try
            {
                List<JVideo> videos = new JVideosService()
                        .getAllByStarId(starId);
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
                e.printStackTrace();
            }
        }

    }
}
