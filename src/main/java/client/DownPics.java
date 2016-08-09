package client;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import service.DownPicTask;
import db.DbExecMgr;

public class DownPics
{
    ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
            .newFixedThreadPool(10);

    public DownPics()
    {

    }

    public static void main(String[] args)
    {
        new DownPics().download();
    }

    public void download()
    {
        Map map = DbExecMgr
                .getSelectAllSqlMap("SELECT ID,IMAGE,NAME from JVIDEO ORDER BY ID ASC limit 10000 offset 40000");
        int size = map.size();
        Map rowMap;
        System.out.println(size);
        for (int i = 1; i <= size; i++)
        {
            rowMap = (Map) map.get(i);
            final String name = String.valueOf(rowMap.get("ID"));
            final String image = String.valueOf(rowMap.get("IMAGE"));
            executor.execute(new DownPicTask(name, image));
        }
        executor.shutdown();

        // BlockingQueue<Runnable> queue = executor.getQueue();
        // System.out.println(queue.size());
        // while (executor.getActiveCount() > 0)
        // {
        // for (Runnable runnable : queue)
        // {
        // DownPicTask downPicTask = (DownPicTask) runnable;
        // long runningTime = downPicTask.getRunningTime();
        // if(runningTime > 1000 * 30)
        // {
        // System.out.println(downPicTask.getCurrThreadName()
        // + "工作超时:" + runningTime);
        // BasicFileUtil.writeFileString("overtime.txt",
        // downPicTask.getCurrThreadName() + "工作超时:"
        // + runningTime + "\r\n", "GBK", true);
        // }
        // System.out.println(downPicTask.getCurrThreadName() + "本次运行时间:"
        // + runningTime);
        // }
        // try
        // {
        // Thread.sleep(3000);
        // System.out.println();
        // System.out.println();
        // }
        // catch (InterruptedException e)
        // {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
        // }
    }

}
