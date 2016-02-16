package Press;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
 

import javax.Faces.application.FacesMessage;
import javax.Faces.bean.ManagedBean;
import javax.Faces.bean.RequestScoped;
import javax.Faces.context.FacesContext;
 

import org.primefaces.model.UploadedFile;
 
@ManagedBean
@RequestScoped
public class ImageStoreBean {
 
    private UploadedFile file;
     
   
    public void storeImage() {
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/itcuties?user=uploader&password=password");
            
            connection.setAutoCommit(false);
             
            
            PreparedStatement statement = connection.prepareStatement("INSERT INTO files (file) VALUES (?)");
            
            statement.setBinaryStream(1, file.getInputstream());
             
           
            statement.executeUpdate();
             
           
            connection.commit();    
            connection.close();
             
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Upload success", File.getFileName() + " is uploaded."); 
            FacesContext.getCurrentInstance().addMessage(null, msg);
             
        } catch (Exception e) {
            e.printStackTrace();
             
            FacesMessage errorMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Upload error", e.getMessage()); 
            FacesContext.getCurrentInstance().addMessage(null, errorMsg);
        }
         
    }
 
    public UploadedFile getFile() {
        return file;
    }
    public void setFile(UploadedFile file) {
        this.file = file;
    }  
}
