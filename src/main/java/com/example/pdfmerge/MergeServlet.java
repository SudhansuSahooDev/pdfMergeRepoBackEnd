package com.example.pdfmerge;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.apache.pdfbox.merging.PDFMergerUtility;

@WebServlet("/merge")
public class MergeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        Part file1 = request.getPart("file1");
        Part file2 = request.getPart("file2");

        InputStream input1 = file1.getInputStream();
        InputStream input2 = file2.getInputStream();

        PDFMergerUtility merger = new PDFMergerUtility();
        merger.addSource(input1);
        merger.addSource(input2);

        OutputStream out = response.getOutputStream();
        merger.setDestinationStream(out);
        merger.mergeDocuments(null);

        input1.close();
        input2.close();
        out.close();
    }
}