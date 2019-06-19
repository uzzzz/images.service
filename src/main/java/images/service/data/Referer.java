package images.service.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

@Component
public class Referer {

	private Map<String, String> map;

	public String getByHost(String host) {
		if (map.containsKey(host)) {
			return map.get(host);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void loadReferers() throws IOException {
		ClassPathResource resource = new ClassPathResource("referers.json");
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(resource.getInputStream(), Charset.forName("UTF-8")));
		this.map = new Gson().fromJson(reader, Map.class);
	}
}
