
   import java.awt.event.ActionEvent;
   import java.awt.event.ActionListener;
   import java.math.BigDecimal;
   import java.util.ArrayList;

   import java.text.DecimalFormat;

   import javax.swing.*;
   import java.awt.*;
   import java.awt.event.MouseAdapter;
   import java.awt.event.MouseEvent;
   import java.util.Random;
   import java.math.RoundingMode;

public class Main
{

    public static void main(String[] args) {
        
        ui ui = new ui();
    }
}








   class operation
   {
       static ArrayList test1=new ArrayList(); // 무조건 입력될 리스트
       static ArrayList test2_int=new ArrayList(); // 숫자만 입력될 리스트
       static ArrayList test3_String=new ArrayList(); // 연산자만 입력될 리스트
       //  static ArrayList test4_String=new ArrayList();
       static ArrayList bi_list=new ArrayList(); //bi 리스트

       public BigDecimal start()
       {


           re_num();
           chain();


           BigDecimal bi_num=new BigDecimal(String.valueOf(bi_list.get(0)));
           for(int i=0;i<test3_String.size();i++)
           {
               if(test3_String.get(i).equals("+"))
               {
                   bi_num=(bi_num.add((BigDecimal) bi_list.get(i+1)));// 더하기
                   System.out.println("더하기: "+bi_num+" = "+bi_num+" + "+bi_list.get(i+1));
               }
               if(test3_String.get(i).equals("-"))
               {
                   bi_num=(bi_num.subtract((BigDecimal) bi_list.get(i+1)));// 빼기
                  // System.out.println("빼기: "+bi_num+" = "+bi_num+" - "+bi_list.get(i+1));

               }
           }

           System.out.println(bi_num);
          return bi_num;



       }
       static  void re_num() // 숫자와 연산자 분리
       {
           String text="";
           for(int i=0;i<test1.size();i++)
           {
               if (!test1.get(i).equals("+")&&!test1.get(i).equals("-")&&!test1.get(i).equals("*")&&!test1.get(i).equals("/"))// 연산자를 걸러 숫자를 String에다 넣는다.
               {
                   System.out.println("실행 " + test1.get(i));
                   text +=test1.get(i);
               }


               if(test1.get(i).equals("+")||test1.get(i).equals("-")||test1.get(i).equals("*")||test1.get(i).equals("/"))// 선별된 숫자를 별도에 다른 리스트에 넣는다.
               {
                   test3_String.add(test1.get(i));
                   test2_int.add(Double.parseDouble(text));
                   text="";
               }

               if(test1.size()-1==i)// 마지막은 숫자로 간주하여 넣는다.
                   test2_int.add(Double.parseDouble(text));
           }
           for(int i=0;i<test2_int.size();i++) {
               // bi_tem[i]=new BigDecimal(String.valueOf(test2_int.get(i)));//숫자를 넣는다.
               BigDecimal tem=new BigDecimal(String.valueOf(test2_int.get(i)));
               bi_list.add(tem);
           }

           for(int i=0;i<test3_String.size();i++)
               System.out.println(test3_String.get(i));


       }

       static void chain() // 우선 순위 연산자 처리
       {

           for(int i=0;i<test3_String.size();i++)
           {
               if(test3_String.get(i).equals("/"))
               {

                   BigDecimal bi_tem =((BigDecimal) bi_list.get(i)).divide((BigDecimal) bi_list.get(i+1),10, RoundingMode.HALF_UP);// 나누기
                   System.out.println("나누기: "+bi_tem+" = "+bi_list.get(i)+" / "+bi_list.get(i+1));
                   {
                       bi_list.remove(i+1);

                       bi_list.remove(i);
                   }

                   bi_list.add(i,bi_tem); // 나누기 삽입

                   test3_String.remove(i); // 연산자 삭제하기
                   chain();

               }
           }


           for(int i=0;i<test3_String.size();i++)
           {
               if (test3_String.get(i).equals("*"))
               {
                   BigDecimal bi_tem =((BigDecimal) bi_list.get(i)).multiply((BigDecimal) bi_list.get(i+1));
                   System.out.println("곱하기: "+bi_tem+" = "+bi_list.get(i)+" * "+bi_list.get(i+1));
                   {
                       bi_list.remove(i+1);
                       bi_list.remove(i);

                   }
                   bi_list.add(i,bi_tem);// 곱하기 삽입

                   test3_String.remove(i); // 연산자 삭제하기
                   chain();

               }
           }
       }



   }

   class ui extends JFrame {
       String text ="";
       int panelSize_height=300;
       int panelSize_width=275;
       String operator[]={"0","1","2","3","4","5","6","7","8","9","C","+","x","-","/","=","게임","기록","."};
       DecimalFormat decimalFormat = new DecimalFormat("#,###");
      histori histori=new histori();
       static boolean histori_on=false;
       static int histori_of=0;
       public ui(){


           setTitle("계산기");
           setSize(panelSize_width,panelSize_height); // 생성

           operation oper=new operation();
           JPanel panel=new JPanel();


           setResizable(false);
           panel.setBackground(new Color(213,213,213));
           add(panel); // 색 지정

           JButton numver[]=new JButton[operator.length]; // 버튼 객체배열 생성
           JTextField textField = new JTextField(); // 20 columns wide
           textField.setFont(new Font(textField.getFont().getName(), textField.getFont().getStyle(), 30));
           textField.setHorizontalAlignment(JTextField.RIGHT);
           textField.setSize(278,95);
           textField.setLocation(-2,0);
           textField.setEditable(false);

           panel.add(textField);



           for(int i=0;i<operator.length;i++) //버튼 객체 넣기
               numver[i]=new JButton(operator[i]);

           numver[0].addActionListener(new ActionListener() { // 숫자 0

               @Override
               public void actionPerformed(ActionEvent e) {
                 text+="0";
                 oper.test1.add(0);
                   textField.setText(text);

               }
           });

            numver[1].addActionListener(new ActionListener() { // 숫자 1
                @Override
                public void actionPerformed(ActionEvent e) {
                    text+="1";
                    oper.test1.add(1);
                    textField.setText(text);

                }
            });

           numver[2].addActionListener(new ActionListener() { // 숫자 2
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="2";
                   oper.test1.add(2);
                   textField.setText(text);
               }
           });

           numver[3].addActionListener(new ActionListener() { // 숫자 3
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="3";
                   oper.test1.add(3);
                   textField.setText(text);
               }
           });

           numver[4].addActionListener(new ActionListener() { // 숫자 4
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="4";
                   oper.test1.add(4);
                   textField.setText(text);
               }
           });


           numver[5].addActionListener(new ActionListener() { // 숫자 5
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="5";
                   oper.test1.add(5);
                   textField.setText(text);
               }
           });

           numver[6].addActionListener(new ActionListener() { // 숫자 6
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="6";
                   oper.test1.add(6);
                   textField.setText(text);
               }
           });

           numver[7].addActionListener(new ActionListener() { // 숫자 7
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="7";
                   oper.test1.add(7);
                   textField.setText(text);
               }
           });

           numver[8].addActionListener(new ActionListener() { // 숫자 8
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="8";
                   oper.test1.add(8);
                   textField.setText(text);
               }
           });

           numver[9].addActionListener(new ActionListener() { // 숫자 9
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="9";
                   oper.test1.add(9);
                   textField.setText(text);
               }
           });



           numver[10].addActionListener(new ActionListener() { //  c
               @Override
               public void actionPerformed(ActionEvent e) {
                   text="";
                   textField.setText("");
                   oper.test1.clear();
                   oper.test2_int.clear();
                   oper.test3_String.clear();
                   oper.bi_list.clear();
                   System.out.println("클리어");

               }
           });

           numver[11].addActionListener(new ActionListener() { // 숫자 +
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="+";
                   oper.test1.add("+");
                   textField.setText(text);
               }
           });

           numver[12].addActionListener(new ActionListener() { // 숫자 x
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="X";
                   oper.test1.add("*");
                   textField.setText(text);
               }
           });

           numver[13].addActionListener(new ActionListener() { // 숫자 -
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="-";
                   oper.test1.add("-");
                   textField.setText(text);
               }
           });

           numver[14].addActionListener(new ActionListener() { // 숫자 /
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+="/";
                   oper.test1.add("/");
                   textField.setText(text);
               }
           });

           numver[15].addActionListener(new ActionListener() { // 숫자 =  // 추가 적인 설계필요.
               @Override
               public void actionPerformed(ActionEvent e) {

                   BigDecimal tem = new BigDecimal(String.valueOf(oper.start().stripTrailingZeros()));


                   if(tem.scale()>0)
                   {
                       textField.setText(tem.toPlainString());

                        histori.add((text+"= "+tem.toPlainString()));


                   }else {
                       String formattedNumber = decimalFormat.format(tem);
                       textField.setText(formattedNumber);
                       histori.add(text+"= "+ formattedNumber);
                   }





               oper.bi_list.clear();
               oper.test2_int.clear();
               oper.test3_String.clear();

               }
           });


           numver[16].addActionListener(new ActionListener() { // "게임" 이벤트
               @Override
               public void actionPerformed(ActionEvent e) {
                   new Minesweeper();
               }
           }); // 이벤드 발생

           numver[17].addActionListener(new ActionListener() { // 기록 //스택을 만들어서 입력한것을 출력 하기
               @Override
               public void actionPerformed(ActionEvent e) {
                histori.hi(true);
                System.out.println("히스토리 \n작동");


               }
           });


           numver[18].addActionListener(new ActionListener() { //.
               @Override
               public void actionPerformed(ActionEvent e) {
                   text+=".";
                   oper.test1.add(".");
                   textField.setText(text);
               }
           });


           numver[10].setForeground(new Color(243, 1, 1));
            panel.add(numver[10]);






           numver[0].setBounds(-2,225, 145, 50);

           numver[7].setBounds(-2,180, 150/3, 50);
           numver[4].setBounds(-2,135, 150/3, 50);
           numver[1].setBounds(-2,90, 150/3, 50);

           numver[8].setBounds(45,180,150/3, 50);
           numver[5].setBounds(45,135, 150/3, 50);
           numver[2].setBounds(45,90, 150/3, 50);

           numver[9].setBounds(92,180, 150/3, 50);
           numver[6].setBounds(92,135, 150/3, 50);
           numver[3].setBounds(92,90, 150/3, 50);

           numver[10].setBounds(139,90, 98, 50);
           numver[11].setBounds(139,135, 150/3, 50);
           numver[13].setBounds(139,135+45,150/3,50);
           numver[14].setBounds(186,(135+45),150/3,50);
           numver[12].setBounds(186,135, 150/3, 50);

           numver[15].setBounds(139,(135+47)+43,50,50);
           numver[18].setBounds(186,(135+47)+43,50,50);

           numver[16].setBounds(233,135+46,43,90);
           numver[17].setBounds(233,93,43,90);

           for(int i=0;i<operator.length;i++) { // 버튼 add
               panel.add(numver[i]);

           }

           panel.setLayout(null);






           setVisible(true);



       }


       public void end()
       {
           this.setVisible(false);
       }
       public void run()
       {
           this.setVisible(true);
       }




   }
class histori extends JFrame{
public static ArrayList history =new ArrayList();
    histori (){
        for(int i=0;i<5;i++)
        history.add("");
    }

    static void add(String valu)
    {
        history.add(valu);
    }
     static void hi(boolean b)
    {
        JFrame frame = new JFrame("UI Panel Example");

        // JPanel 객체 생성
             frame.setTitle("히스토리");

        // JFrame 설정
        frame.setBounds(500,200,300,300);
        frame.setResizable(false);

        JPanel panel1 = new JPanel(null);  // 레이아웃 매니저 해제





       JTextField textField1 = new JTextField();
       textField1.setFont(new Font(textField1.getFont().getName(), textField1.getFont().getStyle(), 30));
       textField1.setHorizontalAlignment(JTextField.RIGHT);
        textField1.setSize(300,70);
        textField1.setLocation(0,1);
       textField1.setEditable(false);
       textField1.setLayout(null);
        textField1.setText(String.valueOf(history.get(history.size()-1)));
        panel1.add(textField1);


        JTextField textField2 = new JTextField(); // 20 columns wide
        textField2.setFont(new Font(textField2.getFont().getName(), textField2.getFont().getStyle(), 30));
        textField2.setHorizontalAlignment(JTextField.RIGHT);
        textField2.setSize(300,70);
        textField2.setLocation(0,65);
        textField2.setEditable(false);
        textField2.setLayout(null);
        textField2.setText(String.valueOf(history.get(history.size()-2)));
        panel1.add(textField2);

        JTextField textField3 = new JTextField(); // 20 columns wide
        textField3.setFont(new Font(textField2.getFont().getName(), textField2.getFont().getStyle(), 30));
        textField3.setHorizontalAlignment(JTextField.RIGHT);
        textField3.setSize(300,70);
        textField3.setLocation(0,128);
        textField3.setEditable(false);
        textField3.setLayout(null);
        textField3.setText(String.valueOf(history.get(history.size()-3)));
        panel1.add(textField3);

        JTextField textField4 = new JTextField(); // 20 columns wide
        textField4.setFont(new Font(textField2.getFont().getName(), textField2.getFont().getStyle(), 30));
        textField4.setHorizontalAlignment(JTextField.RIGHT);
        textField4.setSize(300,70);
        textField4.setLocation(0,192);
        textField4.setEditable(false);
        textField4.setLayout(null);
        textField4.setText(String.valueOf(history.get(history.size()-4)));
        panel1.add(textField4);





        frame.add(panel1);



        frame.setVisible(true);
    }



}
 //지뢰 찾기 게임
   class Minesweeper extends JFrame {
       private final int SIZE = 9;
       private final int MINES = 10;
       private JButton[][] buttons = new JButton[SIZE][SIZE];
       private int[][] mines = new int[SIZE][SIZE];

       public Minesweeper() {
           setDefaultCloseOperation(EXIT_ON_CLOSE);
           setSize(400, 400);
           setTitle("지뢰 찾기 게임");
           setLayout(new GridLayout(SIZE, SIZE));

           generateMines();
           initButtons();
           setVisible(true);
       }

       private void generateMines() {
           Random random = new Random();
           int minesToSet = MINES;
           while (minesToSet > 0) {
               int x = random.nextInt(SIZE);
               int y = random.nextInt(SIZE);
               if (mines[x][y] != -1) {
                   mines[x][y] = -1;
                   minesToSet--;
               }
           }
       }

       private void initButtons() {
           for (int i = 0; i < SIZE; i++) {
               for (int j = 0; j < SIZE; j++) {
                   buttons[i][j] = new JButton();
                   buttons[i][j].addMouseListener(new MouseAdapter() {
                       public void mousePressed(MouseEvent e) {
                           for (int x = 0; x < SIZE; x++) {
                               for (int y = 0; y < SIZE; y++) {
                                   if (e.getSource() == buttons[x][y]) {
                                       if (mines[x][y] == -1) {
                                           buttons[x][y].setText("M");
                                           gameOver();
                                       } else {
                                           buttons[x][y].setText(String.valueOf(countMines(x, y)));
                                       }
                                   }
                               }
                           }
                       }
                   });
                   add(buttons[i][j]);
               }
           }
       }

       private int countMines(int x, int y) {
           int count = 0;
           for (int i = -1; i <= 1; i++) {
               for (int j = -1; j <= 1; j++) {
                   if (x + i >= 0 && x + i < SIZE && y + j >= 0 && y + j < SIZE && mines[x + i][y + j] == -1) {
                       count++;
                   }
               }
           }
           return count;
       }

       private void gameOver() {
           for (int x = 0; x < SIZE; x++) {
               for (int y = 0; y < SIZE; y++) {
                   if (mines[x][y] == -1) {
                       buttons[x][y].setText("M");
                   }
                   buttons[x][y].setEnabled(false);
               }
           }
       }


   }

