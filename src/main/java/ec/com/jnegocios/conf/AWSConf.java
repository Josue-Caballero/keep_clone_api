
package ec.com.jnegocios.conf;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * AWSConf
 */
@Configuration
public class AWSConf {

	@Value("${do.space.region}")
	private String region;

	@Value("${do.space.credentials.key}")
	private String key;

	@Value("${do.space.credentials.secret}")
	private String secret;

	@Value("${do.space.endpointBucket}")
	private String endpointBucket;

	@Bean
	public AmazonS3 getBucketClient() {

		BasicAWSCredentials auth = new BasicAWSCredentials(key, secret);

		AmazonS3 client = AmazonS3ClientBuilder.standard()
			.withEndpointConfiguration( new EndpointConfiguration(endpointBucket, region) )
			.withCredentials( new AWSStaticCredentialsProvider(auth))
			.build();
		
		return client;

	}
	
}
