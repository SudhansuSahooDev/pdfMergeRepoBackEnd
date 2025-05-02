package com.example.pdfmerge;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;


import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import java.util.Collection;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

@WebServlet("/merge")
@MultipartConfig
public class MergeServlet extends HttpServlet {

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "https://pdf-merge-front-end-i8rz.vercel.app");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "https://pdf-merge-front-end-i8rz.vercel.app");

        PDFMergerUtility merger = new PDFMergerUtility();

        Collection<Part> parts = request.getParts();

        ByteArrayOutputStream mergedOutput = new ByteArrayOutputStream();

        try {
            for (Part part : parts) {
                if (part.getContentType() != null && part.getContentType().equals("application/pdf")) {
                    merger.addSource(part.getInputStream());
                }
            }

            merger.setDestinationStream(mergedOutput);
            merger.mergeDocuments(null);

            byte[] mergedBytes = mergedOutput.toByteArray();

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=merged.pdf");
            response.setContentLength(mergedBytes.length);

            OutputStream out = response.getOutputStream();
            out.write(mergedBytes);
            out.flush();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PDF merging failed");
        } finally {
            mergedOutput.close();
        }
    }
}
