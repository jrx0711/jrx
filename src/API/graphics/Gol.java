
package API.graphics;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
 
/**
 * The Game Of Life Applet.生命游戏演示。
 * boolean[][] table保存各个格子中细胞状况（比较char[][] table ）；
 * int[][] neighbors保存 按照当代grid所推导出的各个格子的邻居数目。
 * 用法：
 * 点击鼠标设置cell，ctrl+点击将cell置空。
 * 键盘space切换线程运行/停止。
 * @作者: yqj2065
 * @日期: 09-01-30
 */
public class Gol extends Applet implements Runnable,MouseListener,MouseMotionListener,KeyListener{
     final int SIZE = 30;//二维游戏世界的大小,共SIZE*SIZE个格子
     final int CELL_Size =20;//每个格式的边长，Java坐标系单位。
    Color cell =new Color(0,0,0);
    Color space =new Color(226,0,226);    
    //当代的状况，格子中是否有生命
      boolean[][] table = new boolean[SIZE][SIZE];
    //格子的邻居数目
     int[][] neighbors = new int[SIZE][SIZE];
    
    Thread animator;
    int delay;//延迟 
    boolean running;//flag。标识线程的运行状况，正在运行则running为true，被用户中断，running为false。
 
    public void setTablecell(int i,int j)
    {
    	table[i][j]=true;
    }
    @Override public void run() {
        long tm = System.currentTimeMillis();
        while (Thread.currentThread() == animator) {  //如果线程就是animator
            if (running == true) {
                getNeighbors();
                nextWorld();
                repaint();
            } 
            try {
                tm += delay;
                Thread.sleep(Math.max(0, tm - System.currentTimeMillis()));
            } catch (InterruptedException e) {
                break;
            }
        } 
    } // run
    
    /**
     * applet 生命周期方法
     */
    @Override public void init()  {
        animator = new Thread(this);
        delay = 100;
        running = false;
        //setBackground(Color.yellow);
        setBackground(new Color(199,237,204));
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
    }
    
    @Override public void start() {        
        animator.start();       
    }
 
    @Override public void stop()    {
        animator = null;    
    }
 
    @Override public void paint(Graphics g) {
        update(g);
    }
    
    @Override public void update (Graphics g) {
        for (int x = 0; x < SIZE; x++)
            for (int y = 0; y < SIZE; y++) {
               g.setColor(table[x][y]?cell:space);
               g.fillRect(x * CELL_Size, y * CELL_Size, CELL_Size-1 , CELL_Size-1 );  //位置+大小。全填充，无间隔
            }
    }
 
    /**
     * 从table数组中推导出neighbors数组。
     */
    public void getNeighbors() { 
        for (int r = 0; r < SIZE; r++){//row
            for (int c = 0; c < SIZE; c++){//col
                if(r-1 >= 0 && c-1 >= 0   && table[r-1][c-1] )neighbors[r][c]++;
                if(r-1 >= 0     && table[r-1][c])             neighbors[r][c]++;
                if(r-1 >= 0 && c+1 < SIZE && table[r-1][c+1])neighbors[r][c]++;
                if(c-1 >= 0   && table[r][c-1]) neighbors[r][c]++;
                if(c+1 < SIZE && table[r][c+1]) neighbors[r][c]++;
                if(r+1 < SIZE && table[r+1][c]) neighbors[r][c]++;
                if(r+1 < SIZE && c+1 < SIZE && table[r+1][c+1])    neighbors[r][c]++;
                if(r+1 < SIZE && c-1 >=0 && table[r+1][c-1])       neighbors[r][c]++;
            }
        }            
    }
    
    /**
     * nextWorld()，世代交替。
     * 生命游戏的核心是计算出下一代的table，产生新一代的二维世界。
     * 按照每一个neighbors元素
     */
    public void nextWorld(){
        for (int r = 0; r < SIZE; r++){//row
            for (int c = 0; c < SIZE; c++){//col
                if (neighbors[r][c] == 3){
                    table[r][c] = true;
                }//if (neighbors[r][c] == 2) 不改变table[r][c]。
                if (neighbors[r][c] < 2)
                    table[r][c] = false; 
                if (neighbors[r][c] >= 4)
                    table[r][c] = false;                 
                neighbors[r][c] = 0;      //格子周围活细胞数量置零               
            }           
        }
    }
    /**
     * event handler 
     */
    public void mouseClicked(MouseEvent e){ }   
    public void mousePressed(MouseEvent e){       //点击鼠标事件
        int cellX = e.getX()/CELL_Size;
        int cellY = e.getY()/CELL_Size;
        table[cellX][cellY] = !e.isControlDown();   //按ctrl时，活细胞变死(黑变白，1变0);无ctrl时，死细胞变活(白变黑，0变1)
        repaint();
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseDragged(MouseEvent e){    //鼠标拉拽事件
        this.mousePressed(e); 
    }
    public void mouseMoved(MouseEvent e){}     
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){      //按压空格事件
        if(e.getKeyChar()==' '){
            running = !running;
            repaint();
        }
    }
    public void keyReleased(KeyEvent e){}
}
