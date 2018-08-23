package dynamic_prog;

public class LongestRoadInMatrix
{
  private static final int END = 0;
  private static final int UP = 1;
  private static final int DOWN = 2;
  private static final int LEFT = 3;
  private static final int RIGHT = 4;
  private static final String[] dir = new String[] {"END", "UP", "DOWN", "LEFT", "RIGHT"};
  
  private static int cnt = 0;
  
  
  public static void run()
  {
    int[][] mat = getMatrix();
    int[][] d = initMatrix(mat.length, mat[0].length, 0);
    int[][] p = initMatrix(mat.length, mat[0].length, END);
    
    printMatrix(mat);
    printMatrix(d);
    
    int max = 0;
    int pos_i = -1;
    int pos_j = -1;
    for (int i = 0; i < mat.length; i++)
      for (int j = 0; j < mat[i].length; j++)
      {
        int tmp = computeRoad(i, j, mat, d, p);
        if (max < tmp)
        {
          max = tmp;
          pos_i = i;
          pos_j = j;
        }
      }    
    
    printMatrix(d);
    printMatrix(p);
    System.out.println("Max  ["+pos_i+", "+pos_j+"] = " + max);
    int[][] r = markRoad(pos_i, pos_j, p, 2);
    printMatrix(r);
    
    System.out.println("End  [" + mat.length +", "+ mat[0].length + "] = " + cnt);
  }
  
  public static int computeRoad(int i, int j, int[][] mat, int[][] d, int[][] p)
  {
    if (d[i][j] != 0)
      return d[i][j];
    cnt++;
    
    int val = 0;
    int tmp = 0;
    int next = END;
    
    // see LEFT
    if (j > 0 && mat[i][j] < mat[i][j-1])
      tmp = computeRoad(i, j-1, mat, d, p);
    if (tmp > val)
    {
      val = tmp;
      next = LEFT;
    }
    
    // see RIGHT
    if (j < (mat[i].length-1) && mat[i][j] < mat[i][j+1])
      tmp = computeRoad(i, j+1, mat, d, p);
    if (tmp > val)
    {
      val = tmp;
      next = RIGHT;
    }
    
    // see UP
    if (i > 0 && mat[i][j] < mat[i-1][j])
      tmp = computeRoad(i-1, j, mat, d, p);
    if (tmp > val)
    {
      val = tmp;
      next = UP;
    }
    
    // see DOWN
    if (i < (mat.length-1) && mat[i][j] < mat[i+1][j])
      tmp = computeRoad(i+1, j, mat, d, p);
    if (tmp > val)
    {
      val = tmp;
      next = DOWN;
    }
    
    val++;
    d[i][j] = val;
    p[i][j] = next;
    return val;
  }
  
  public static int[][] markRoad(int i, int j, int[][] p, int v)
  {
    int[][] r = initMatrix(p.length, p[0].length, 0);
    while (p[i][j] != END)
    {
      r[i][j] = v;
      if (p[i][j] == UP)
        i--;
      else if (p[i][j] == DOWN)
        i++;
      else if (p[i][j] == LEFT)
        j--;
      else if (p[i][j] == RIGHT)
        j++;
    }
    return r;
  }

  public static int[][] getMatrix()
  {
    int[][] mat = new int[][] {
      new int[] {1, 2, 0, 4, 5, 0, 0,  0, 0},
      new int[] {0, 3, 0, 6, 3, 1, 0, 12, 6},
      new int[] {0, 0, 4, 2, 0, 0, 0, 11, 7},
      new int[] {0, 8, 0, 1, 5, 4, 3, 10, 5},
      new int[] {0, 3, 0, 2, 0, 4, 2,  9, 0},
      new int[] {2, 5, 5, 3, 4, 5, 6,  7, 8},
      new int[] {1, 4, 2, 7, 0, 6, 0,  0, 0},
      new int[] {0, 1, 2, 8, 0, 7, 8,  9, 0}
    };
    return mat;
  }
  
  public static int[][] initMatrix(int n, int m, int v)
  {
    int[][] mat = new int[n][m];
    for (int i = 0; i < mat.length; i++)
      for (int j = 0; j < mat[i].length; j++)
        mat[i][j] = v;
    return mat;
  }
  
  public static void printMatrix(int[][] mat)
  {
    for (int i = 0; i < mat.length; i++)
    {
      for (int j = 0; j < mat[i].length; j++)
        System.out.print(mat[i][j] + " ");
      System.out.println("");
    }
    System.out.println("");
  }
  
  public static String direction(int i)
  {
    return dir[i];
  }
}
