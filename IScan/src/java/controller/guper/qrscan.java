/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.guper;

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
 * @author Fandi CS
 */
@WebServlet(name = "qrscan", urlPatterns = {"/qrscan"})
public class qrscan extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
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
     *
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
     *
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
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
