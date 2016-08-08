package util;

import com.wnc.basic.BasicFileUtil;

public class TextFormatUtil
{
    public static String getValidTitle(String alt)
    {
        return alt.replace("\\", "-").replace("/", "-").replace("<", "-")
                .replace(">", "-").replace(":", "-").replace("|", "-")
                .replace("\"", "-").replace("?", "");
    }

    public static String getDownloadDir(String savePath, String title)
    {
        title = TextFormatUtil.getValidTitle(title);
        String downPath = savePath + title + "\\";
        if(title.contains(" "))
        {
            BasicFileUtil.deleteFolder(downPath);
            // BasicFileUtil.deleteFolder(downPath.substring(0,
            // downPath.lastIndexOf(" ")));
            downPath = downPath.replace(" ", "_");
        }
        return downPath;
    }
}
