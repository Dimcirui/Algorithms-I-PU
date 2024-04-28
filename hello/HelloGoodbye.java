public class HelloGoodbye {
    public static void main(String[] args) {
        System.out.print("Hello ");
        System.out.print(args[0] + " and ");
        System.out.println(args[1] + ".");
        
        System.out.print("Goodbye ");
        System.out.print(args[1] + " and ");
        System.out.println(args[0] + ".");
    }
}