package cn.wsjiu.nineSite;

public class Utils {
    public static String webRootPath;
    static {
      webRootPath = System.getProperty("realPath");
    }
    // 过滤器，过滤掉&&，插个空格，避免服务端参数拆分时的错误
    public static String datafilter(String target) {
        StringBuilder str = new StringBuilder();
        char a,b;
        if(target!=null&&target.length()>0) b=target.charAt(0);
        else return "";
        int len=target.length();
        for(int i=0,j=1;j<len;i++,j++){
            a=b;
            b=target.charAt(j);
            if(a=='&'&&b=='&') {
                str.append(a);
                str.append(" ");
            }else {
                str.append(a);
            }
        }
        str.append(target.charAt(len-1));
        return str.toString();
    }
}
