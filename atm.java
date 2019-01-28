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
        System.out.println("Finished creating account, please log in");
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
    System.out.println(accnum);
    System.out.println("Enter your pin number: ");
    short pinNum = scan.nextShort();
    account a = new account();
    if(a.check(accnum, pinNum) == 0){
      System.out.println("Login successful");
    }
    else if(a.check(accnum, pinNum) == 1){
      System.out.println("Account not found");
      //closes program
      System.exit(0);
    }
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

  int check(String accNum, short pinNum){
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
  for (int i = 0; i < accounts.length ; i++ ) {
    if(accounts[i].AccNum.equals(accNum) &&
       accounts[i].PinNum == pinNum){return 0;}
  }
  //return false cause no match
  return 1;
 }
}//end of account class

/*//calculations
class Math{
  float balance;
}

class elem extends Math{

}
*/
