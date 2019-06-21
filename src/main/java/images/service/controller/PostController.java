package images.service.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/post")
public class PostController {

	private static Logger log = LoggerFactory.getLogger(PostController.class);

	@Value("${belost.path}")
	private String belostPath;

	private String template = "---\r\n" //
			+ "layout: post\r\n" //
			+ "title: \"%s\"\r\n"//
			+ "date: %s\r\n" //
			+ "categories: %s\r\n"//
			+ "tags: %s\r\n" //
			+ "img: \"%s\"\r\n" //
			+ "---\r\n" //
			+ "%s";

	@PostMapping("belost")
	@ResponseBody
	public String belost( //
			String title, //
			String date, //
			String categories, //
			String tags, //
			@RequestParam(required = false, defaultValue = "") String img, //
			String content) {
		String post = String.format(template, title, date, categories, tags, img, content);
		try {
			String path = belostPath + "/source/_posts";
			String filename = title.replace(' ', '-') + ".html";
			File file = new File(path, filename);
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(new FileOutputStream(file, false), "UTF-8"));
			writer.write(post);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
		hexo_g();
		return "OK";
	}

	private void hexo_g() {
		try {
			long start = System.currentTimeMillis();

			File dir = new File(belostPath);
			String[] cmd = new String[] { "/bin/sh", "-c", "hexo g" };
			Process process = Runtime.getRuntime().exec(cmd, null, dir);

			// 记录dos命令的返回信息
			StringBuffer resStr = new StringBuffer();
			InputStream in = process.getInputStream();
			Reader reader = new InputStreamReader(in);
			BufferedReader bReader = new BufferedReader(reader);
			for (String res = ""; (res = bReader.readLine()) != null;) {
				resStr.append(res + "\n");
			}
			bReader.close();
			reader.close();

			// 记录dos命令的返回错误信息
			StringBuffer errorStr = new StringBuffer();
			InputStream errorIn = process.getErrorStream();
			Reader errorReader = new InputStreamReader(errorIn);
			BufferedReader eReader = new BufferedReader(errorReader);
			for (String res = ""; (res = eReader.readLine()) != null;) {
				errorStr.append(res + "\n");
			}
			eReader.close();
			errorReader.close();
			process.getOutputStream().close(); // 不要忘记了一定要关
			long end = System.currentTimeMillis();
			log.error("hexo g OK:" + (end - start) + "ms\nsuccess:" + resStr.toString() + "\nerror:" + errorStr);
		} catch (IOException ee) {
			log.error(ee.getMessage());
		}
	}
}
