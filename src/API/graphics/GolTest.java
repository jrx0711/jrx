package API.graphics;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class GolTest {
	
	 public static Gol l=new Gol();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++)
			l.table[i][j]=false;
		for(int i=0;i<30;i++)
			for(int j=0;j<30;j++)
				l.neighbors[i][j]=0;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}


	@Test
	public void testGetNeighbors1() {
		l.setTablecell(0, 0);
		l.setTablecell(1,1);
		l.setTablecell(2,2);
		l.getNeighbors();
		 assertEquals(2,l.neighbors[1][1]);
	}
	@Test
	public void testGetNeighbors2() {
		l.setTablecell(12, 12);
		l.setTablecell(12,13);
		l.setTablecell(12,14);
		l.setTablecell(13,12);
		l.setTablecell(13,14);
		l.setTablecell(14,12);
		l.setTablecell(14,13);
		l.setTablecell(14,14);
		l.getNeighbors();
		 assertEquals(8,l.neighbors[13][13]);  //�׺в��ԣ�������100%
	}

	@Test
	public void testNextWorld1() {
		l.setTablecell(9, 6);
		l.setTablecell(10,7);
		l.setTablecell(11,8);
		l.getNeighbors();
		l.nextWorld();
		assertEquals(true,l.table[10][7]);  //���ٽ���ϸ������Ϊ2ʱ������ԭ״̬
		
	}
	@Test
	public void testNextWorld2() {
		
		l.setTablecell(21,7);                //�ٽ�ϸ����״̬�������Լ�
		l.setTablecell(22,8);        
		l.getNeighbors();
		l.nextWorld();
		assertEquals(false,l.table[21][7]);  //���ٽ���ϸ������Ϊ1ʱ������ԭ״̬
		
	}
	@Test
	public void testNextWorld3() {
		l.setTablecell(12, 12);
		l.setTablecell(12,13);
		l.setTablecell(12,14);
		l.setTablecell(13,12);
		l.setTablecell(13,14);
		l.setTablecell(14,12);
		l.setTablecell(14,13);
		l.setTablecell(14,14);
		l.getNeighbors();
		l.nextWorld();
		assertEquals(false,l.table[13][13]);  //���ٽ���ϸ������Ϊ8>4ʱ����ϸ������
		
	}
	/*@Test
	public void testNextWorld3() {
		l.setTablecell(24, 12);
		l.setTablecell(24,13);
		l.setTablecell(24,14);
		l.setTablecell(25,12);
		l.setTablecell(25,14);
		l.setTablecell(26,12);
		l.setTablecell(26,13);
		l.setTablecell(26,14);
		l.getNeighbors();
		l.nextWorld();
		assertEquals(false,l.table[25][13]);  //���ٽ���ϸ������Ϊ8>4ʱ����ϸ������
		
	}*/
	
}
