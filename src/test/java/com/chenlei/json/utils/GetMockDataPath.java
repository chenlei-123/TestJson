package com.chenlei.json.utils;

/**传递一个类的名称进来，返回mockdata的路径
 * Created by Administrator on 2017/3/14.
 */
public class GetMockDataPath {
    public static String getPath(Class aClass){
        String name = aClass.getName();
        String substring = name.substring(26);
        String simpleName = aClass.getSimpleName();
        String substring1 = substring.substring(0, substring.length() - simpleName.length());
        String mock = ".proxymock";
        String path = mock+substring1;
        String s = path.replaceAll("\\.", "\\/");
        String projectRootPath = System.getProperty("user.dir");
        return projectRootPath + s;
    }
}
