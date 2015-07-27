package book.data;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import book.business.Download;
import book.business.User;

public class ReportDB {
	
	// used org.apache.poi.ss.usermodel.Workbook for text extraction
	public static Workbook getUserEmail(){
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		String qString = "SELECT u FROM User u " + 
				"ORDER BY u.lastName";
		TypedQuery<User> q = em.createQuery(qString, User.class);
		List<User> users = null;
		try{
			users = q.getResultList();
		} catch(NoResultException e){
			System.err.println(e);
			return null;
		} finally{
			em.close();
		}
		
		// create the workbook, its worksheet, and its title row
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("User Email Report");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("The User Email Report");
		
		// create the header row
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("LastName");
		row.createCell(1).setCellValue("FirstName");
        row.createCell(2).setCellValue("Email");
        row.createCell(3).setCellValue("CompanyName");
        row.createCell(4).setCellValue("Address");
        row.createCell(6).setCellValue("City");
        row.createCell(7).setCellValue("State");
        row.createCell(8).setCellValue("Zip");
        row.createCell(9).setCellValue("Country");
        row.createCell(10).setCellValue("UserID");
        
        // create the data rows
        int i = 3;
        for(User u : users){
        	row = sheet.createRow(i);
        	row.createCell(0).setCellValue(u.getLastName());
        	row.createCell(1).setCellValue(u.getFirstName());
        	row.createCell(2).setCellValue(u.getEmail());
            row.createCell(3).setCellValue(u.getCompanyName());
            row.createCell(4).setCellValue(u.getAddress());
            row.createCell(6).setCellValue(u.getCity());
            row.createCell(7).setCellValue(u.getSt());
            row.createCell(8).setCellValue(u.getZip());
            row.createCell(9).setCellValue(u.getCountry());
            row.createCell(10).setCellValue(u.getUserId());
        	i++;
        }
        
        return workbook;
	}

	public static Workbook getDownloadedDetail(String startDate, String endDate){
		// get data from db
		EntityManager em = DBUtil.getEMFactory().createEntityManager();
		String qString = "SELECT d FROM Download d " + 
				"WHERE d.downloadDate >= :startDate AND " + 
				"d.downloadDate <= :endDate ORDER BY d.downloadDate DESC";
		TypedQuery<Download> q = em.createQuery(qString, Download.class);
		List<Download> downloads = null;
		try{
			downloads = q.getResultList();
		} catch(NoResultException e){
			System.err.println(e);
			return null;
		} finally{
			em.close();
		}
		
		// create the workbook, its worksheet, and its title row
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("Download Report");
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("The Download Report");
		
		// create the header row
		row = sheet.createRow(2);
		row.createCell(0).setCellValue("Start Date: " + startDate);
		row = sheet.createRow(3);
		row.createCell(0).setCellValue("End Date: " + endDate);
		row = sheet.createRow(5);
		row.createCell(0).setCellValue("Download Date");
		row.createCell(0).setCellValue("ProductCode");
		row.createCell(0).setCellValue("Email");
		row.createCell(0).setCellValue("FirstName");
		row.createCell(0).setCellValue("LastName");
		
		// create data rows
		int total = 0;
		int i =6;
		for(Download download : downloads){
			row = sheet.createRow(i);
			row.createCell(0).setCellValue(download.getDownloadDate().toString());
			row.createCell(1).setCellValue(download.getProductCode());
			row.createCell(2).setCellValue(download.getUser().getEmail());
	        row.createCell(3).setCellValue(download.getUser().getFirstName());
	        row.createCell(4).setCellValue(download.getUser().getLastName());
	        total++;
	        i++;
		}
		row = sheet.createRow(i+1);
		row.createCell(0).setCellValue("Total Number of Downloads: " + total);
		
		return workbook;
	}
	
}
