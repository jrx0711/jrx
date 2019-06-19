
package API.graphics;
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.event.*;
 
/**
 * The Game Of Life Applet.������Ϸ��ʾ��
 * boolean[][] table�������������ϸ��״�����Ƚ�char[][] table ����
 * int[][] neighbors���� ���յ���grid���Ƶ����ĸ������ӵ��ھ���Ŀ��
 * �÷���
 * ����������cell��ctrl+�����cell�ÿա�
 * ����space�л��߳�����/ֹͣ��
 * @����: yqj2065
 * @����: 09-01-30
 */
public class Gol extends Applet implements Runnable,MouseListener,MouseMotionListener,KeyListener{
     final int SIZE = 30;//��ά��Ϸ����Ĵ�С,��SIZE*SIZE������
     final int CELL_Size =20;//ÿ����ʽ�ı߳���Java����ϵ��λ��
    Color cell =new Color(0,0,0);
    Color space =new Color(226,0,226);    
    //������״�����������Ƿ�������
      boolean[][] table = new boolean[SIZE][SIZE];
    //���ӵ��ھ���Ŀ
     int[][] neighbors = new int[SIZE][SIZE];
    
    Thread animator;
    int delay;//�ӳ� 
    boolean running;//flag����ʶ�̵߳�����״��������������runningΪtrue�����û��жϣ�runningΪfalse��
 
    public void setTablecell(int i,int j)
    {
    	table[i][j]=true;
    }
    @Override public void run() {
        long tm = System.currentTimeMillis();
        while (Thread.currentThread() == animator) {  //����߳̾���animator
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
     * applet �������ڷ���
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
               g.fillRect(x * CELL_Size, y * CELL_Size, CELL_Size-1 , CELL_Size-1 );  //λ��+��С��ȫ��䣬�޼��
            }
    }
 
    /**
     * ��table�������Ƶ���neighbors���顣
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
     * nextWorld()���������档
     * ������Ϸ�ĺ����Ǽ������һ����table��������һ���Ķ�ά���硣
     * ����ÿһ��neighborsԪ��
     */
    public void nextWorld(){
        for (int r = 0; r < SIZE; r++){//row
            for (int c = 0; c < SIZE; c++){//col
                if (neighbors[r][c] == 3){
                    table[r][c] = true;
                }//if (neighbors[r][c] == 2) ���ı�table[r][c]��
                if (neighbors[r][c] < 2)
                    table[r][c] = false; 
                if (neighbors[r][c] >= 4)
                    table[r][c] = false;                 
                neighbors[r][c] = 0;      //������Χ��ϸ����������               
            }           
        }
    }
    /**
     * event handler 
     */
    public void mouseClicked(MouseEvent e){ }   
    public void mousePressed(MouseEvent e){       //�������¼�
        int cellX = e.getX()/CELL_Size;
        int cellY = e.getY()/CELL_Size;
        table[cellX][cellY] = !e.isControlDown();   //��ctrlʱ����ϸ������(�ڱ�ף�1��0);��ctrlʱ����ϸ�����(�ױ�ڣ�0��1)
        repaint();
    }
    public void mouseReleased(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseExited(MouseEvent e){}
    public void mouseDragged(MouseEvent e){    //�����ק�¼�
        this.mousePressed(e); 
    }
    public void mouseMoved(MouseEvent e){}     
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){      //��ѹ�ո��¼�
        if(e.getKeyChar()==' '){
            running = !running;
            repaint();
        }
    }
    public void keyReleased(KeyEvent e){}
}
