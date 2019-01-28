import java.util.Scanner;
import java.io.*;
import static java.lang.System.*;
import java.util.Vector;


class atm{
  public static void main(String args[]){
    //ask to log into account or create
    Scanner scan = new Scanner(System.in);
    System.out.println("Welcome, please enter 1 to log in or enter 9 to create an account");
    int num = scan.nextInt();
    account a = new account();
    switch (num){
      case 1:
        a.login();
        break;
      case 9:
        a.create();
        a.login();
        break;
      default:
        System.out.println("Invalid input");
    }//end of switch
  }//main
}//end of class atm

class account{
  String name, AccNum;
  short PinNum;
  float balance;
  //constructor
  account(String n, float b, String a, short p){
    name = n;
    AccNum = a;
    PinNum = p;
    balance = b;
  }
  //defauult constructor
   account(){
   name = "";
   AccNum = "";
   PinNum = 0;
   balance = 0;
}
  Scanner scan = new Scanner(System.in);
  void login(){
    System.out.println("Enter your account number: ");
    double accNum = scan.nextDouble();
    String accnum = String.format ("%.0f", accNum);
    System.out.println("Enter your pin number: ");
    short pinNum = scan.nextShort();
    account a = new account();
    a.check(accnum, pinNum);
  }//end of login function
  //creates an account
  void create(){
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter your full name: ");
    String name = scan.nextLine();
    System.out.println("Enter your bank account number: ");
    double accNum = scan.nextDouble();
    //convert from scientific notation to string
    String acNum = String.format ("%.0f", accNum);
    System.out.println("Enter your pin number: ");
    short pinNum = scan.nextShort();
    //this adds the person into the existing list
    BufferedWriter bw = null;
    try {
      bw = new BufferedWriter(new FileWriter("Accounts.txt", true));
      //this adds info to the file
      bw.write(name + "\r\n" + 0 + "\r\n" + acNum + "\r\n" + pinNum);
      bw.newLine();
      bw.flush();
   } catch (IOException e) {
     e.printStackTrace();
     } finally { //close the file
       if (bw != null) try {
         bw.close();
       } catch (IOException ioe2) {}
    } // end try/catch/finally
 }//end of create function

  void check(String accNum, short pinNum){
   //first we need to read in the file that contains accounts
   Vector<account> acc = new Vector<account>();
   try{
   FileReader fr = new FileReader("Accounts.txt");
   BufferedReader br = new BufferedReader(fr);
   //using most of these variables for constructor
   String str = "", name = "", an = "";
   short pn = 0;
   float balance = 0.0f;
   short count = 1; //,i = 0;
   //account[] accounts = new account[50];
   while((str = br.readLine()) != null){
     if(count == 1) {name = str;}
     else if(count == 2) {
       //converts from string to float
       balance = Float.parseFloat(str);
       //accounts[i].balance = balance;
     }
     else if(count == 3) {an = str;}
     else if(count == 4) {
     pn = Short.parseShort(str);
     //add person info to array
     acc.add(new account(name, balance, an, pn));
     //i++;
     count = 1;
     continue;
     }
     count++;
   }
   br.close();
   } catch (IOException e){
   out.println("File not found");
  }
  //create an array and copy contents from vector to array
  account[] accounts = new account[acc.size()];
  for (int i = 0; i < acc.size();i++) {
    accounts[i] = acc.get(i);
  };
  //now the REAL checking if account exists
  int index = 0;
  for (int i = 0; i < accounts.length ; i++ ) {
    if(accounts[i].AccNum.equals(accNum) &&
       accounts[i].PinNum == pinNum){
         index = i;
         System.out.println("Login successful");
         break;
       }
    else if(i == accounts.length - 1){
      System.out.println("Account not found");
      //closes program
      System.exit(0);
    }
  }
  money(accounts, index);
  }//endo of check class
  void money(account[] a, int i){
    Scanner scan = new Scanner(System.in);
    System.out.println("-----------------------------");
    System.out.println("Hello, " + a[i].name + " ");
    System.out.println("Balance: $" + a[i].balance);
    char ch;
    do{
    System.out.print("Enter W to Withdraw, D to Deposit, or E to Exit: ");

    ch = scan.next().charAt(0);
    switch (ch){
      case 'W':
      case 'w':{
        elem e = new elem();
        e.withdraw(a, i);
        break;
      }
      case 'D':
      case 'd':{
        elem e = new elem();
        e.deposit(a, i);
        break;
      }
      case 'E':
      case 'e':
        break;
      default:
        System.out.println("Invalid key");
        break;
    }
  }while(ch != 'E' && ch != 'e');
  System.out.println("Goodbye");
  }
}//end of account class

//calculations
class Math{
  float balance;
}
class elem extends Math{
  void withdraw(account[] a, int i){
    balance = a[i].balance;
    Scanner scan = new Scanner(System.in);
    System.out.println("How much would you like to withdraw? ");
    float withdraw = scan.nextFloat();
    if(balance < withdraw){
      System.out.println("You cannot withdraw $" + withdraw);
    }
    else if(balance > withdraw && withdraw > 0){
      balance -= withdraw;
      System.out.println("You're new balance is: $" + balance);
      System.out.println("-----------------------------");
      a[i].balance = balance;
      elem e = new elem();
      e.newBalance(a);
    }
  }//end of withdrawl method

  void deposit(account[] a, int i){
    balance = a[i].balance;
    Scanner scan = new Scanner(System.in);
    System.out.println("Enter value of cash to desposit: ");
    float deposit = scan.nextFloat();
    balance += deposit;
    System.out.println("New balance is: $" + balance);
    System.out.println("-----------------------------");
    a[i].balance = balance;
    elem e = new elem();
    e.newBalance(a);
  }//end of deposit method

  //deletes old file and makes new file with updated info
  void newBalance(account[] a){
    File file = new File("Accounts.txt");
    file.delete();
    try{
    //create new file
    FileWriter fw = new FileWriter("Accounts.txt");
    PrintWriter pw = new PrintWriter(fw);
    for (int j = 0; j < a.length; j++ ) {
        pw.println(a[j].name);
        pw.println(a[j].balance);
        pw.println(a[j].AccNum);
        pw.println(a[j].PinNum);
    }
    pw.close();
  } catch (IOException e){
    out.println("ERROR!");
  }
}//end of newBalance method
}//end of elem class
