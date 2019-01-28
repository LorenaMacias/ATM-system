import java.util.Scanner;
import java.io.*;
import static java.lang.System.*;

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
  String name, AccNum, PinNum;
  float balance;
  //my constructor
  account(String n, float b, String a, String p){
    name = n;
    AccNum = a;
    PinNum = p;
    balance = b;
  }
  account(){}
  Scanner scan = new Scanner(System.in);
  void login(){
    System.out.println("Enter your account number: ");
    double accNum = scan.nextDouble();
    System.out.println("Enter your pin number: ");
    short pinNum = scan.nextShort();
    account a = new account();
    if(a.check()){
      System.out.println("Login succesfulk");
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

 bool check(String a, String b){
   try{
   FileReader fr = new FileReader("Accounts.txt");
   BufferedReader br = new BufferedReader(fr);

   String str, name, an, pn;
   float balance;
   short count = 1;
   while((str = br.readLine()) != null){
     if(count == 1) {name = str;}
     if(count == 2) {
       //converts from string to float
       balance = Float.parseFloat(str);
     }
     if(count == 3) {an = str;}
     if(count == 4) {pn = str; count = 1;}
     count++;
   }
   br.close();
   } catch (IOException e){
   out.println("File not found");
  }
 }
}//end of account class

/*
class Math{

}

class elem extends Math{

}
*/
