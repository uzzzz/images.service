package images.service.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import images.service.data.Referer;

@Controller
@RequestMapping("/p")
public class ProxyController {

	@Autowired
	private Referer referer;

	@RequestMapping()
	public void image(HttpServletRequest request, HttpServletResponse response) {
		String url = request.getQueryString();

		InputStream in = null;
		OutputStream out = null;
		try {
			URL u = new URL(url);
			String host = u.getHost();
			String protocol = u.getProtocol();
			String ref = referer.getByHost(host);
			ref = protocol + "://" + (StringUtils.hasLength(ref) ? ref : host);
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("referer", ref);
			in = conn.getInputStream();
			out = response.getOutputStream();
			IOUtils.copy(in, out);
			out.flush();
		} catch (Exception e) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
	}
}
