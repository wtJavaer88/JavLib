package client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import service.VideosTask;
import db.DbExecMgr;

public class CheckNotFoundStar
{
    public static void main(String[] args)
    {
        Map map = DbExecMgr
                .getSelectAllSqlMap("SELECT ID,NAME from JSTAR ORDER BY ID ASC ");
        int size = map.size();
        Map rowMap;
        System.out.println(size);
        List<String> allStars = new ArrayList<String>();
        for (int i = 1; i <= size; i++)
        {
            rowMap = (Map) map.get(i);
            final String starId = String.valueOf(rowMap.get("ID"));
            allStars.add(starId);
        }

        File[] starFiles = new File("D:\\txt\\javlib\\star\\").listFiles();
        for (File file : starFiles)
        {
            String star = file.getName().replace(".txt", "");
            if(allStars.contains(star))
            {
                allStars.remove(star);
            }
        }
        System.out.println(allStars);
        for (String starId : allStars)
        {
            check(starId);
        }
        executor.shutdown();

    }

    static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
            .newFixedThreadPool(20);

    public static void check(String starId)
    {
        try
        {
            executor.execute(new VideosTask(starId, starId));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
