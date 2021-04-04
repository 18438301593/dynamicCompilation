# dynamicCompilation
## 部署项目
### 数据库
sql语句在/src/main/resources/sql/文件夹下，下来导入数据库即可（有默认三个测试数据）。
### 主启动类
test.dc.DcApplication<br>
启动完成后访问界面如下
![Image of Yaktocat](http://photo.jiajiajia.club/weblog/2021-04-02/2021-04-05_003633.jpg)
### java动态编译原理分析
参考博客 http://www.jiajiajia.club/blog/artical/0335vh/298
### 测试例题 答案
#### 排序问题
```java
import java.util.Scanner;

public class Test {
	public static int a[],step=0,s=0;
    /**
     * 递归函数
     * @param left 数组左区间开始
     * @param right 数组右区间结束
     */
    public static void quicksort(int left, int right) {
        int i, j,temp;
        if(left > right)
            return;
        temp = a[left]; //temp中存的就是基准数
        i = left;
        j = right;
        while(i != j){ //两探针没有碰头
            while(a[j] >= temp && i < j)//顺序很重要，要先从右边开始找
                j--;
            while(a[i] <= temp && i < j)//再找右边的
                i++;       
            if(i < j){ //交换两个数在数组中的位置
                a[i] = a[i]+a[j];
                a[j] = a[i]-a[j];
                a[i] = a[i]-a[j];
            }
        }
        //最终将基准数归位
        a[left] = a[i];
        a[i] = temp;
        s++;
        if(s>step) {//计算递归深度
            step=s;
        }
        quicksort(left, i-1);//继续递归处理左区间数据
        quicksort(i+1, right);//继续递归处理右区间数据
        s--;
    }
    public static void main(String[] args) {
    	Scanner s=new Scanner(System.in);
    	int n=s.nextInt();
    	a= new int[n];
    	for(int i=0;i<n;i++) {
    		a[i]=s.nextInt();
    	}
        quicksort(0,a.length-1);
        for(int i=0;i<a.length;i++)
            System.out.print(a[i] + " ");
    }
}
```
#### 渗透问题
```java
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
public class Test5Controller {
    //上下左右四个方向的增量
    static int move[][]=new int[][] {{0,0},{0,1},{1,0},{0,-1},{-1,0}};
    static int t[][]=null;
    public static void main(String[] args) {
    	Scanner s=new Scanner(System.in);
    	int n=s.nextInt();
    	int m=s.nextInt();
    	t=new int[n][m];
    	for(int i=0;i<t.length;i++) {
    		for(int j=0;j<t.length;j++) {
        		t[i][j]=s.nextInt();
        	}
    	}
        List<Node> li=new ArrayList<>();
        //先将开始时所有的墨水放入集合
        for(int i=0;i<t.length;i++) {
            for(int j=0;j<t[i].length;j++) {
                if(t[i][j]==2) {
                    li.add(new Node(i,j,2));
                }
            }
        }
        //总时间c
        int c=0;
        do {
            li=search(li);
            if(li.size()>0) {
                //如果集合里面有数据，那就向外扩散一次
                c++;
            }else {
                break;
            }
        }while(true);
         
        /**
         * 看是否所有的纸都被染色
         */
        for(int i=0;i<t.length;i++) {
            for(int j=0;j<t[i].length;j++) {
                if(t[i][j]==1) {
                    System.out.println("false");
                    return;
                }
            }
        }
        System.out.println(c);
    }
     
    public static List<Node> search(List<Node> list){
        List<Node> li=new ArrayList<>();
        /**
         * 遍历每个周围节点
         */
        for(Node n:list) {
            /**
             * 遍历每个节点的四个方向
             */
            for(int i=1;i<=4;i++) {
                int x=n.x+move[i][0];
                int y=n.y+move[i][1];
                //控制边界
                if(x<t.length&&y<t[0].length&&t[x][y]==1) {
                    t[x][y]=2;
                    li.add(new Node(x,y,t[x][y]));
                }
            }
        }
        return li;
    }
    /**
     *    模拟节点类
     */
    static class Node{
        public int x;//数组横坐标
        public int y;//数组纵坐标
        public int d;//数据
        public Node(int x, int y, int d) {
            super();
            this.x = x;
            this.y = y;
            this.d = d;
        }
        @Override
        public String toString() {
            return "Node [x=" + x + ", y=" + y + ", d=" + d + "]";
        }
    }
}
```
