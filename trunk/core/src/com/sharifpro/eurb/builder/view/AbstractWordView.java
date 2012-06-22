package com.sharifpro.eurb.builder.view;

import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import org.springframework.web.servlet.view.AbstractView;

/**
 * Convenient superclass for Word document views. Compatible with Apache POI 3.8
 * 
 * For working with the document in the subclass, see <a
 * href="http://jakarta.apache.org/poi/index.html">Jakarta's POI site</a>
 * 
 * @author <A HREF="mailto:m_dashti@ce.sharif.edu">Mohammad Dashti</A>
 * @see AbstractPdfView
 */
public abstract class AbstractWordView extends AbstractView {

	/** The content type for an Excel response */
	private static final String CONTENT_TYPE = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";

	/** The extension to look for existing templates */
	//private static final String EXTENSION = ".docx";

	/**
	 * Default Constructor. Sets the content type of the view to
	 * "application/vnd.ms-excel".
	 */
	public AbstractWordView() {
		setContentType(CONTENT_TYPE);
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}

	/**
	 * Renders the Word view, given the specified model.
	 */
	@Override
	protected final void renderMergedOutputModel(Map<String, Object> model,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		XWPFDocument document = new XWPFDocument();
		logger.debug("Created Word Document from scratch");

		buildWordDocument(model, document, request, response);

		// Set the content type.
		response.setContentType(getContentType());

		// Should we set the content length here?
		// response.setContentLength(workbook.getBytes().length);

		// Flush byte array to servlet output stream.
		ServletOutputStream out = response.getOutputStream();
		document.write(out);
		out.flush();
	}

	/**
	 * Subclasses must implement this method to create an Word XWPFDocument
	 * document, given the model.
	 * 
	 * @param model
	 *            the model Map
	 * @param workbook
	 *            the Word document to complete
	 * @param request
	 *            in case we need locale etc. Shouldn't look at attributes.
	 * @param response
	 *            in case we need to set cookies. Shouldn't write to it.
	 */
	protected abstract void buildWordDocument(Map<String, Object> model,
			XWPFDocument document, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

}
