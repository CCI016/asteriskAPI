package endpoints;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import org.apache.commons.io.IOUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import orm.Prompt;

@Path("/file")
public class UploadFileService {

    @ConfigProperty(name = "sounds.directory")
    String soundsDirectory;

    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    @Transactional
    public Response uploadFile(MultipartFormDataInput input) {

        String fileName = "";
        String asteriskFileName = "";
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                asteriskFileName = getAsterName(fileName);
                System.out.println("FileName:" + fileName);
                System.out.println("Asterisk File Name" + asteriskFileName);
                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                byte [] bytes = IOUtils.toByteArray(inputStream);



                writeFile(bytes,soundsDirectory + asteriskFileName);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        Prompt prompt = new Prompt();
        prompt.ariName = asteriskFileName;
        prompt.originalName = fileName;
        prompt.persist();

        return Response.status(200).build();

    }

    /**
     * header sample
     * {
     * 	Content-Type=[image/png], 
     * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
     * }
     **/
    //get uploaded filename, is there a easy way in RESTEasy?
    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    public String getAsterName(String filename) {
        List<Prompt> prompts = Prompt.find("ORDER BY id DESC").list();
        if (prompts.size() == 0) {
            return ("asteriskAPI" + 1 + filename.substring(filename.length() - 4));
        }
        return ("asteriskAPI" + (prompts.get(0).id + 1) + filename.substring(filename.length() - 4));
    }

    //save to somewhere
    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }
}