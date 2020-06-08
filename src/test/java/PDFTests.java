import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Meta;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.*;
import images.service.ImagesServiceApplication;
import images.service.data.GithubFileUploader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImagesServiceApplication.class)
public class PDFTests {


	@Test
	public void pdfTitle() throws IOException, DocumentException {
		String srcFile="/Users/feng/Desktop/123.pdf";
		String outFile="/Users/feng/Desktop/out.pdf";
		FileInputStream fis = new FileInputStream(srcFile);

		PdfReader reader = new PdfReader(srcFile);
		FileOutputStream out = new FileOutputStream(outFile);

		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, out);
		document.open();
		document.addTitle("ttttttitle");

		PdfContentByte cb = writer.getDirectContent();

		int totalPages = reader.getNumberOfPages();
		int pageOfCurrentReaderPDF = 0;
		while (pageOfCurrentReaderPDF <reader.getNumberOfPages()) {
			document.newPage();
			pageOfCurrentReaderPDF++;
			PdfImportedPage page = writer.getImportedPage(reader, pageOfCurrentReaderPDF);
			cb.addTemplate(page, 0, 0);
		}
		out.flush();
		document.close();
		out.close();
	}

	@Test
	public void pdfTitle2() throws IOException, DocumentException {
		String srcFile="/Users/feng/Desktop/123.pdf";
		String outFile="/Users/feng/Desktop/out.pdf";

		FileInputStream srcStream = new FileInputStream(srcFile);
		PdfReader reader = new PdfReader(srcStream);
		Document document = new Document();
		document.open();
		document.addTitle("ttttttitle");
		PdfCopy copy = new PdfCopy(document, new FileOutputStream(outFile));
		copy.addDocument(reader);
		copy.flush();
		copy.close();
		document.close();
	}

}
