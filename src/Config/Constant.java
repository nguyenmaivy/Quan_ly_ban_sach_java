
package Config;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author MZI
 */

public class Constant {
    private static final String DB_URL = "jdbc:sqlserver://LAPTOP-SLAV9SET\\NGPHONGDEV:1433;databaseName=quanlybansach;encrypt=true;trustServerCertificate=true;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "123";

    private Connection con;
    
    public boolean openConnection(){
        boolean result = false;
        
        //Hàm thiết lập kết nối với database
        try{
            // Đăng ký MySQL Driver với DriverManager (có nghĩa là ta có thể kết nối 2 database trong cùng 1 app này, chi tiết thì search ChatGPT)
//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            return true;
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return result;
    }
    
    
    //Hàm đóng kết nối với database
    public void closeConnection(){
        try{
            
            if(con!=null)
                con.close();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    //Hàm trả về đối tượng Connection
    public Connection getConnection() {
         try{
            // Đăng ký MySQL Driver với DriverManager (có nghĩa là ta có thể kết nối 2 database trong cùng 1 app này, chi tiết thì search ChatGPT)
//            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
//            System.out.println("Connection object: " + con); // Thêm dòng này
            return con;

        }catch(Exception e){
            e.printStackTrace();
        }

        return con;
    }
    
    //test connection
//    public static void main(String[] args) {
//        Constant db = new Constant();
//        if(db.openConnection()){
//            System.out.println("Kết nối thành công!");
//        } else{
//            System.out.println("Kết nối database thất bại!");
//        }
//        
//        if (db.getConnection() != null) {
//            System.out.println("Kết nối hợp lệ!");
//        } else {
//            System.out.println("Kiểm tra lại kết nối!");
//        }
//        
//        db.closeConnection();
//        System.out.println("Kết nối đóng!");
//    }
}

