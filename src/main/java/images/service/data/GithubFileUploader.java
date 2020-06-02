package images.service.data;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Component
public class GithubFileUploader {

    @Autowired
    private RestTemplate rest;

    public String upload(String imageOriginUrl) throws IOException {

        InputStream in = null;
        try {
            URL u = new URL(imageOriginUrl);
            String host = u.getHost();
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            in = conn.getInputStream();
            byte[] bytes = IOUtils.toByteArray(in);
            String content =  Base64.getEncoder().encodeToString(bytes);
            String filename = DigestUtils.md5DigestAsHex(imageOriginUrl.getBytes()) + System.currentTimeMillis() + ".png";
            String path = host + "/" + filename;
            String token = "75a8f5035a95ce9691e710c62b0f27cab41d8469";
            // 用户名、库名、路径
            String url = "https://api.github.com/repos/xxcode/img.ibz.bz/contents/docs/" + path ;
            GithubFileObject githubFileObject = GithubFileObject.create(imageOriginUrl, content, "GithubFileUploader", "GithubFileUploader@local.mbp");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.add("Authorization", "token " + token);
            HttpEntity<GithubFileObject> entity = new HttpEntity<>(githubFileObject, headers);
            ResponseEntity<String> responseEntity = rest.exchange(url, HttpMethod.PUT, entity, String.class);
            String resp = responseEntity.getBody();
            System.out.println(resp);
            return "https://img.ibz.bz/" + path;
        } catch (IOException e) {
            throw e;
        } finally {
            if(in !=null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}