package image.proxy;

import images.service.ImagesServiceApplication;
import images.service.data.GithubFileUploader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

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
