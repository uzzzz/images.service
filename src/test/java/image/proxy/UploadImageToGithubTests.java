package image.proxy;

import images.service.ImagesServiceApplication;
import images.service.data.GithubFileObject;
import images.service.data.GithubFileUploader;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ImagesServiceApplication.class)
public class UploadImageToGithubTests {

	@Autowired
	private RestTemplate rest;

	@Autowired
	private GithubFileUploader githubFileUploader;

	@Test
	public void upload() {
		String imageOriginUrl = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png";
		try {
			String url = githubFileUploader.upload(imageOriginUrl);
			System.out.println(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
