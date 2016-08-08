package client;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import service.DownPicTask;
import service.Logger;
import bean.JVideo;

import com.wnc.basic.BasicStringUtil;
import com.wnc.string.PatternUtil;

import db.DbExecMgr;

public class CheckNotFoundPic
{
    static Map<String, JVideo> nameImageMap = new HashMap<String, JVideo>();

    public static void main(String[] args)
    {
        Map map = DbExecMgr
                .getSelectAllSqlMap("SELECT ID,IMAGE,NAME from JVIDEO ORDER BY ID ASC ");
        int size = map.size();
        Map rowMap;
        System.out.println(size);
        List<String> allVideos = new ArrayList<String>();
        for (int i = 1; i <= size; i++)
        {
            rowMap = (Map) map.get(i);
            final String vId = String.valueOf(rowMap.get("ID"));
            final String vName = String.valueOf(rowMap.get("NAME"));
            final String vImage = String.valueOf(rowMap.get("IMAGE"));
            allVideos.add(vId);
            JVideo video = new JVideo();
            video.setName(vName);
            video.setImage(vImage);
            nameImageMap.put(vId, video);
        }

        File[] starFiles = new File("D:\\txt\\javlib\\pic\\").listFiles();
        for (File file : starFiles)
        {
            String video = PatternUtil
                    .getFirstPattern(file.getName(), ".*?\\s").trim();
            if(BasicStringUtil.isNullString(video))
            {
                Logger.logGlobal(file.getName() + "不合法");
            }
            else if(allVideos.contains(video))
            {
                allVideos.remove(video);
            }
        }
        System.out.println(allVideos.size());
        for (String vId : allVideos)
        {
            check(vId);
        }
        executor.shutdown();

    }

    static ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
            .newFixedThreadPool(10);

    public static void check(String vId)
    {
        JVideo jVideo = nameImageMap.get(vId);
        // System.out.println(jVideo.getName() + " " + jVideo.getImage());
        try
        {
            executor.execute(new DownPicTask(jVideo.getName(), jVideo
                    .getImage()));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
