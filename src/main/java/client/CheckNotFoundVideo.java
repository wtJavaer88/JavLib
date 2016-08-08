package client;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import service.SingleVideoTask;

import com.wnc.string.PatternUtil;
import com.wnc.tools.FileOp;

public class CheckNotFoundVideo
{
    public static void main(String[] args)
    {
        List<String> list = FileOp.readFrom("D:\\txt\\javlib\\log\\log.txt",
                "UTF-8");

        for (String line : list)
        {
            if(line.startsWith("http://"))
                check(PatternUtil.getFirstPattern(line, "http:.*? ").trim());
        }
        executor.shutdown();
    }

    static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
            .newFixedThreadPool(20);

    public static void check(String url)
    {
        try
        {
            executor.execute(new SingleVideoTask(url));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
