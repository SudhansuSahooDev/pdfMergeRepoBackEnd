package com.example.pdfmerge;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;


public class MergeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        try {
        Part file1 = request.getPart("file1");
        Part file2 = request.getPart("file2");

        InputStream input1 = file1.getInputStream();
        InputStream input2 = file2.getInputStream();

        org.apache.pdfbox.multipdf.PDFMergerUtility merger = new org.apache.pdfbox.multipdf.PDFMergerUtility();
        merger.addSource(input1);
        merger.addSource(input2);

        OutputStream out = response.getOutputStream();
        merger.setDestinationStream(out);
        merger.mergeDocuments(null);

        input1.close();
        input2.close();
        out.close();
        }catch ( Exception e) {
        	System.out.println("Isssue");
        }
    }
}
