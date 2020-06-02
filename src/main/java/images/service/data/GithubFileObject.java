package images.service.data;

import java.io.Serializable;

public class GithubFileObject implements Serializable {

    public String message;
    public String content;
    public  Committer committer;

    public static class Committer implements Serializable{
        public String name;
        public String email;
    }

    public static GithubFileObject create(String message, String content,String name,String email) {
        GithubFileObject object = new GithubFileObject();
        object.message = message;
        object.content = content;
        Committer committer = new Committer();
        committer.name = name;
        committer.email = email;
        object.committer = committer;
        return object;
    }
}
