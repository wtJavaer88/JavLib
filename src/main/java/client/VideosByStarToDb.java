package client;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import service.VideosTask;
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
            }
            executor.shutdown();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
