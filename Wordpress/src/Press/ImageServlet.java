package Press;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class ImageServlet extends HttpServlet {
 
    private static final long serialVersionUID = -6449908958106497977L;
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            byte[] imageBytes = null;
             
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/itcuties?user=uploader&password=password");
             
            PreparedStatement statement = connection.prepareStatement("SELECT id, file FROM files ORDER BY id DESC LIMIT 1");
             
            ResultSet rs = statement.executeQuery();
             
            while (rs.next()) { // This will run only once
                imageBytes = rs.getBytes("file");
            }
 
            connection.close();
             
            resp.getOutputStream().write(imageBytes);
            resp.getOutputStream().close();
             
        } catch (Exception e) {
            resp.getWriter().write(e.getMessage());
            resp.getWriter().close();
        }
         
    }  
}