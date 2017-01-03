import org.jclouds.ContextBuilder;
import org.jclouds.blobstore.BlobStore;
import org.jclouds.blobstore.BlobStoreContext;
import org.jclouds.blobstore.domain.Blob;
import org.jclouds.filesystem.reference.FilesystemConstants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class StartJcloudTest {

	private static final String FILE_NAME = "testFile";

	public static void main(String[] args) throws IOException {
		StartJcloudTest startJcloudTest = new StartJcloudTest();
		startJcloudTest.start();
	}

	private void start() throws IOException {
		// setup where the provider must store the files
		Properties properties = new Properties();
		properties.setProperty(FilesystemConstants.PROPERTY_BASEDIR, "/data");
		
		String containerName = "data-container";

		// get a context with filesystem that offers the portable BlobStore api
		BlobStoreContext context = ContextBuilder.newBuilder("filesystem")
				.overrides(properties)
				.buildView(BlobStoreContext.class);

		// create a container in the default location
		BlobStore blobStore = context.getBlobStore();
		blobStore.createContainerInLocation(null, containerName);

		// add blob
		System.out.println("- Create file");
		Blob blob = blobStore.blobBuilder(FILE_NAME).build();
		blob.setPayload("test data");
		blobStore.putBlob(containerName, blob);

		// get blob
		System.out.println("- Read file:");
		Blob testFile = blobStore.getBlob(containerName, FILE_NAME);
		InputStream inputStream = testFile.getPayload().openStream();

		Scanner s = new Scanner(inputStream).useDelimiter("\\A");
		System.out.println(s.hasNext() ? s.next() : "");

		//close context
		context.close();
	}
}
