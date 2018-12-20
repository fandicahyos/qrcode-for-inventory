package controller.guper;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import static com.itextpdf.text.pdf.PdfName.URL;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import config.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.msql;
import config.database;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import libraries.report;
 
/**
 *
 * @author henky
 */
@WebServlet(name = "bcdemo", urlPatterns = {"/bcdemo"})
public class bcdemo extends HttpServlet {

    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    config vconfig = new config();
    Font bf2 = new Font(FontFamily.HELVETICA, 2);
    Font bf4 = new Font(FontFamily.HELVETICA, 4);  
    Font bf8 = new Font(FontFamily.HELVETICA, 8);  
    Font bf10 = new Font(FontFamily.HELVETICA, 10);  
    Font bf12 = new Font(FontFamily.HELVETICA, 12);  
    Font bf14 = new Font(FontFamily.HELVETICA, 14);  
    Font bf16 = new Font(FontFamily.HELVETICA, 16);
    Font bf8b = new Font (FontFamily.HELVETICA, 8, Font.BOLD);
    Font bf12b = new Font (FontFamily.HELVETICA,  12, Font.BOLD );
    Font bf12u = new Font (FontFamily.HELVETICA,  12, Font.BOLD | Font.UNDERLINE);
    Font bf14u = new Font (FontFamily.HELVETICA,  16, Font.BOLD | Font.UNDERLINE);
    Font bf14b = new Font(FontFamily.HELVETICA, 14, Font.BOLD);
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        database db = new database("WEBACCESS-REP_CEIR");
        
    HttpSession session = request.getSession(true);
        String ses_kd_agen = (String) session.getAttribute("seskd_agen");
        String ses_company = (String) session.getAttribute("sescompany");
        String ses_kd_cust = (String) session.getAttribute("seskd_cust");
        String nama_material ="-";
        try {
            // String baseURL = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath())+"/";
            String baseURL = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), request.getContextPath()) + "/";
            vconfig.setbaseUrl(baseURL);  
            String baseurl=vconfig.baseUrl();
            
            System.out.println("report pdf");
            try {
                msql dofile = new msql(db);
                HashMap<String, String> sqlMap = new HashMap<>();
                ArrayList<Map.Entry<String, String>> sqlMap2 = new  ArrayList<>();
                String breadcrumb = "";
                String act = "";
                String msg = "";
                String sqlMessage = "";
                String sqlStatus = "";
                
                String sql = "select nama_material from master_material where kode_item = '"+request.getParameter("id")+"'";
                System.out.println(sql);
                ArrayList<HashMap> data=dofile.proses_select(sql);
                System.out.println(data);
                if(!data.isEmpty()) { 
                    HashMap menu_data=data.get(0);
                    nama_material = menu_data.get("NAMA_MATERIAL").toString();
                    System.out.println("-");
                    
                    data.remove(0);
                }
                
                System.out.println("nama_material"+nama_material);
                
                Date sysDate = new Date();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                //panjang kertas
                Rectangle pagesize = new Rectangle(325f, 300f); 
                //Rectangle pagesize = new Rectangle(850f, 700);
                //Document document = new Document(pagesize, 5f, 5f, 5f, 5f); 
                //Document document = new Document(PageSize.A4); 
                
                //ukuran margin kertas
                Document document = new Document(pagesize, 10f, 10f, 10f, 10f); 
                //Document document = new Document(PageSize.A4); 
                //Document document = new Document(pagesize, 5f, 5f, 5f, 5f); 

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = PdfWriter.getInstance(document, baos);
                //for footer
                //            HeaderAndFooter event = new HeaderAndFooter();
                //        writer.setPageEvent(event);
                // step 3
                document.open();



                // document.add(new Paragraph(String.format("Proforma - Delivery",bf16)));
                // document.add(Chunk.NEWLINE);    
                //document.add(new LineSeparator());

                report libreport = new report();   		
                PdfPTable table2 = new PdfPTable(3);
                table2.setWidthPercentage(100);
                table2.setWidths(new int[]{1,1,1});        

                 //table2.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                PdfPCell cell2;

                cell2 = new PdfPCell(new Phrase(" ", bf12b));

                String payment_yn = "";
                payment_yn = "y";
                //  payment_yn="Y"; 
                
                //insertcell(table2, cell2, bf16,3,1,"center", " ");

                //insertcell(table2, cell2, bf16,3,1,"center", "Data not found");
                //insertcell(table2, cell2, bf10,3,1,"center", payment_yn);
                //writer.setPageEvent(new Watermark());

          
                PdfPTable table4 = new PdfPTable(1);
                table4.setWidthPercentage(80);
                table4.setWidths(new int[]{1}); 
                table4.getDefaultCell().setBorder(Rectangle.NO_BORDER);
                int x=1;
                table2.deleteBodyRows();
                
                String vdata=request.getParameter("id");

                table4.deleteBodyRows();
                
                System.out.println(x);
                insertimagecell(table4, cell2, bf4,1,1,"left", baseURL+"assets/images/logo-kkt.jpg");
                libreport.insertcellborder(table4, cell2, libreport.font(6, " "),1,1,"center",  " ", "PT KALTIM KARIANGAU TERMINAL");
                insertqrcode(table4, cell2, bf4,1,1,"center", vdata); 

                insertbarcode(writer, table4, cell2, bf2,1,1,"center", vdata); 
                libreport.insertcellborder(table4, cell2, libreport.font(10, " "),1,1,"center",  " ", vdata);

                //libreport.insertcellborder(table4, cell2, libreport.font(12, " "),1,1,"center",  " ", vdata.get("CONTAINER_NO").toString());
                libreport.insertcellborder(table4, cell2, libreport.font(7, " "),1,1,"center",  " ", nama_material);
                libreport.insertcellborder(table4, cell2, libreport.font(4, " "),1,1,"center",  " ", "");
                libreport.insertcellborder(table4, cell2, libreport.font(7, " "),1,1,"center",  " ", "print at : "+dateFormat.format(sysDate));
                libreport.insertcellborder(table4, cell2, libreport.font(7, " "),1,1,"center",  " ", "");
                libreport.insertcellborder(table4, cell2, libreport.font(4, " "),1,1,"center",  " ", "Energi Cakrawala Buana");

                libreport.insertcellborder(table4, cell2, libreport.font(7, " "),1,1,"center",  " ", " ");
                libreport.insertcelltable(table2, table4);

                if (x==3) x=0;
                x++;
             
             
                System.out.println(x % 3);
                int y = 3 - x + 1;
                System.out.println(y);
                for(int xi = 0; xi < y; xi++) {
                    //table2.deleteBodyRows();
                   //insertqrcode(table2, cell2, bf10,1,1,"left", "123"); 
                    libreport.insertcellborder(table2, cell2, libreport.font(7, " "),1,1,"center",  " ", " ");

                }
               
             
            document.add(table2);
                
            
            document.close();
            
            
            
            document = new Document(PageSize.LETTER.rotate());

                        
 
            // setting some response headers
            response.setHeader("Expires", "0");
            response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            // setting the content type
            response.setContentType("application/pdf");
            // the contentlength
            response.setContentLength(baos.size());
            // write ByteArrayOutputStream to the ServletOutputStream
            OutputStream os = response.getOutputStream();
            baos.writeTo(os);
            os.flush();
            os.close();
            db.close();
            
         }
        catch(DocumentException e) {
            db.close();
            throw new IOException(e.getMessage());
        }
        
            
        } finally { 
             
            db.close();
        }
    }
public class Watermark extends PdfPageEventHelper {
 
        protected Phrase watermark = new Phrase("Inventory", new Font(FontFamily.HELVETICA, 40, Font.NORMAL, BaseColor.LIGHT_GRAY));
 
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            PdfContentByte canvas = writer.getDirectContentUnder();
            ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, watermark, 50, 500, 90);
        }
    }
private void insertcellborder(PdfPTable table2, PdfPCell cell2,Font font, int colspan,int rowspan, String align, String content){
    cell2 = new PdfPCell(new Phrase(content, font));
    cell2.setColspan(colspan);
    cell2.setRowspan(rowspan);
    //cell2.setHorizontalAlignment(align);
    if(align.equalsIgnoreCase("left")) cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
    if(align.equalsIgnoreCase("center")) cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        if(align.equalsIgnoreCase("right")) cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setBorder(Rectangle.BOTTOM);
    table2.addCell(cell2);
}
private void insertcell(PdfPTable table2, PdfPCell cell2,Font font, int colspan,int rowspan, String align, String content){
    cell2 = new PdfPCell(new Phrase(content, font));
    cell2.setColspan(colspan);
    cell2.setRowspan(rowspan);
    //cell2.setHorizontalAlignment(align);
    if(align.equalsIgnoreCase("left")) cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
    if(align.equalsIgnoreCase("center")) cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        if(align.equalsIgnoreCase("right")) cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setBorder(Rectangle.NO_BORDER);
    table2.addCell(cell2);
}

private void insertimagecell(PdfPTable table2, PdfPCell cell2,Font font, int colspan,int rowspan, String align, String content) throws BadElementException, MalformedURLException, IOException{
    cell2 = new PdfPCell(new Phrase(content, font));
    cell2.setColspan(colspan);
    cell2.setRowspan(rowspan);
  Image img = Image.getInstance(content);
         img.setBorder(Rectangle.NO_BORDER);
         img.setWidthPercentage(50);
         img.setAlignment(Element.ALIGN_CENTER);
        cell2.addElement(img);
    if(align.equalsIgnoreCase("left")) cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
    if(align.equalsIgnoreCase("center")) cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        if(align.equalsIgnoreCase("right")) cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setBorder(Rectangle.NO_BORDER);
    table2.addCell(cell2);
}
private void insertqrcode(  PdfPTable table2, PdfPCell cell2,Font font, int colspan,int rowspan, String align, String content) throws BadElementException, MalformedURLException, IOException{
    cell2 = new PdfPCell(new Phrase(content, font));
    cell2.setColspan(colspan);
    cell2.setRowspan(rowspan);
    Image img ;
  BarcodeQRCode qrcode = new BarcodeQRCode(content, 70, 70, null);
        img = qrcode.getImage();
        //img.setWidthPercentage(70);        
        img.setAlignment(Element.ALIGN_CENTER);
         cell2.addElement(img);
    if(align.equalsIgnoreCase("left")) cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
    if(align.equalsIgnoreCase("center")) cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        if(align.equalsIgnoreCase("right")) cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setBorder(Rectangle.NO_BORDER);
    table2.addCell(cell2);
}

private void insertbarcode( PdfWriter  writer, PdfPTable table2, PdfPCell cell2,Font font, int colspan,int rowspan, String align, String content) throws BadElementException, MalformedURLException, IOException{
    cell2 = new PdfPCell(new Phrase(content, font));
    cell2.setColspan(colspan);
    cell2.setRowspan(rowspan);
    Image img ;
  
    PdfContentByte cb = writer.getDirectContent();
    Barcode128 code128 = new Barcode128();
    
         code128.setCode(content);
         code128.setCodeType(Barcode128.CODE128);
         code128.setFont(null);
         Image code128Image = code128.createImageWithBarcode(cb, null, null);
         code128Image.setAbsolutePosition(10,700);
         code128Image.scalePercent(125);
         
        //img.setWidthPercentage(70);        
        code128Image.setAlignment(Element.ALIGN_CENTER);
         cell2.addElement(code128Image);
    if(align.equalsIgnoreCase("left")) cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
    if(align.equalsIgnoreCase("center")) cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        if(align.equalsIgnoreCase("right")) cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setBorder(Rectangle.NO_BORDER);
    table2.addCell(cell2);
}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}