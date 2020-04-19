package hunnuse.wyc.utils;

/**
 * @ClassName: StringUtil
 * @Description: 字符串匹配算法类
 * @author: tycw
 * @date: 2020/4/17  20:30
 */
public class StringUtil {
    /**
     * 生成next数组
     * @param str 模式串
     * @return
     */
    public int []getNext(String str){
        int n=str.length();
        int []next = new int[n+1];
        int i=0,j=-1;
        next[0]=-1;
        while (i<n)
        {
            if (j==-1||str.charAt(i)==str.charAt(j))
            {
                i++;j++;
                next[i]=j;
            }
            else
                j=next[j];
        }
        return next;
    }

    /**
     * kmp
     * @param s 主串
     * @param t 模式串
     * @param next next数组
     * @return
     */
    public int kmp(String s,String t,int next[])
    {
        int i=0,j=0;
        int slen=s.length();
        int tlen=t.length();
        while(i<slen&&j<tlen)
        {
            if(j==-1||s.charAt(i)==t.charAt(j))
            {
                i++;j++;
            }
            else
                j=next[j];
        }
        if(j==tlen)return i-j+1;
        return -1;
    }
}
