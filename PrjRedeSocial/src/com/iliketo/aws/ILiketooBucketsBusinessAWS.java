package com.iliketo.aws;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import HardCore.Common;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CreateBucketRequest;
import com.amazonaws.services.s3.model.GetBucketLocationRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.coremedia.iso.IsoFile;
import com.coremedia.iso.boxes.MovieBox;
import com.coremedia.iso.boxes.MovieHeaderBox;
import com.iliketo.exception.VideoILiketoException;


public class ILiketooBucketsBusinessAWS {

	static final Logger log = Logger.getLogger(ILiketooBucketsBusinessAWS.class);
	
	private static String bucketName = "";
	private final static String usuario = "dev@iliketoo.com.br";
	private final static String access_key_id = "AKIAJRT64JUHTJSPWZEQ";
	private final static String secret_access_key = "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";
	private final static String endpoint = "s3-us-west-2.amazonaws.com";
	
	public static final String LINK_BUCKET_PRODUCAO = "http://iliketoo.aws.midia" + "." + endpoint;
	public static final String LINK_BUCKET_PILOTO = "http://iliketoo.aws.midia-piloto" + "." + endpoint;
	public static final String AWS_PRODUCAO = "AWS-producao";
	public static final String AWS_PILOTO = "AWS-piloto";
	
	public boolean IS_LOCAL_STORAGE_AMAZON = false;
	private AmazonS3Client amazonS3client;	
	
	public ILiketooBucketsBusinessAWS(String localArmazenamento){
		if(localArmazenamento.equalsIgnoreCase(AWS_PRODUCAO)){
			bucketName = "iliketoo.aws.midia";
			this.IS_LOCAL_STORAGE_AMAZON = true;
			this.amazonS3client = obterCredenciaisCliente();
			//this.criarBucket();
			//this.criarDiretorioNoBucket("upload");
		}
		if(localArmazenamento.equalsIgnoreCase(AWS_PILOTO)){
			bucketName = "iliketoo.aws.midia-piloto";
			this.IS_LOCAL_STORAGE_AMAZON = true;
			this.amazonS3client = obterCredenciaisCliente();
			//this.criarBucket();
			//this.criarDiretorioNoBucket("upload");
		}
	}
	
	public void setConfigBucketPolicy(String myBucket){
		log.info("AWS - Metodo setConfigBucketPolicy() trying...");
		String policyText = 
		"{"
		    +"\"Version\": \"2012-10-17\","
		    +"\"Id\": \"http better policy\","
		    +"\"Statement\": ["
		        +"{"
		            +"\"Sid\": \"AddPerm\","
		            +"\"Effect\": \"Allow\","
		            +"\"Principal\": \"*\","
		            +"\"Action\": [\"s3:GetObject\"],"
		            +"\"Resource\": [\"arn:aws:s3:::" +myBucket+ "/*\"]"
		        +"}"
		    +"]"
		+"}";
		this.amazonS3client.setBucketPolicy(myBucket, policyText);
		log.info("AWS - Set new config policy success bucket name: " + bucketName + " - config: " + policyText);
	}
	
	public void criarBucket(){
		
		log.info("AWS - Metodo criarBucket()");       
        try {
            if(!(this.amazonS3client.doesBucketExist(bucketName))){
            	// Note that CreateBucketRequest does not specify region. So bucket is 
            	// created in the region specified in the client.
            	log.info("AWS - New bucket name: " + bucketName);
            	this.amazonS3client.createBucket(new CreateBucketRequest(bucketName));
            }else{
            	log.info("AWS - Bucket name already exist: " + bucketName);
            }
            // Get location.
            String bucketLocation = this.amazonS3client.getBucketLocation(new GetBucketLocationRequest(bucketName));
            log.info("AWS - Bucket location = " + bucketLocation);
            
         } catch (AmazonServiceException ase) {
            log.error("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            log.error("Error Message:    " + ase.getMessage());
            log.error("HTTP Status Code: " + ase.getStatusCode());
            log.error("AWS Error Code:   " + ase.getErrorCode());
            log.error("Error Type:       " + ase.getErrorType());
            log.error("Request ID:       " + ase.getRequestId());
            throw ase;
        } catch (AmazonClientException ace) {
            log.error("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            log.error("Error Message: " + ace.getMessage());
            throw ace;
        }
    }
	
	private static AmazonS3Client obterCredenciaisCliente(){
		log.info("AWS - Metodo obterCredenciaisCliente()");
		ClientConfiguration cc = new ClientConfiguration()
	    .withMaxErrorRetry (15)
	    .withMaxConnections(100)
	    .withConnectionTimeout (60*60*1000);
		AmazonS3Client amazonS3client = new AmazonS3Client(new BasicAWSCredentials(access_key_id, secret_access_key), cc);
		amazonS3client.setEndpoint(endpoint);
		amazonS3client.setRegion(Region.getRegion(Regions.US_WEST_2));
		log.info("AWS - "+ amazonS3client.getS3AccountOwner());
		return amazonS3client;
	}
	
	public String uploadDeArquivosParaStorageAmazon(String keyFilename, FileItem fileItem) throws IOException{
		ObjectMetadata metadata = new ObjectMetadata();
		InputStream input = fileItem.getInputStream();
		metadata.setContentType(fileItem.getContentType());
		metadata.setContentLength(fileItem.getSize());
		
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, null);
		log.info("AWS - Put object trying...");
		this.amazonS3client.putObject(new PutObjectRequest(bucketName, keyFilename, input, metadata));
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
		return keyFilename;
	}
	
	public String uploadDeArquivosParaDiretorioStorageAmazon(String keyFilename, String folderName, FileItem fileItem) throws IOException{
		ObjectMetadata metadata = new ObjectMetadata();
		InputStream input = fileItem.getInputStream();
		metadata.setContentType(fileItem.getContentType());
		metadata.setContentLength(fileItem.getSize());
		
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, folderName);
		String pathFile = folderName +"/"+ keyFilename;
		log.info("AWS - Put object in folder trying...");
		this.amazonS3client.putObject(new PutObjectRequest(bucketName, pathFile, input, metadata));
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
		return keyFilename;
	}
	
	public String uploadDeArquivosParaStorageAmazon(String keyFilename, File file){
		log.info("AWS - Put object trying...");
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, null);
		this.amazonS3client.putObject(bucketName, keyFilename, file);
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
		return keyFilename;
	}
	
	public String uploadDeArquivosParaDiretorioStorageAmazon(String keyFilename, String folderName, File file){
		log.info("AWS - Put object in folder trying...");
		//nome do arquivo unico no bucket/diretorio
		keyFilename = this.randomizeFilenameUniqueObjectAmazon(keyFilename, folderName);
		String pathFile = folderName +"/"+ keyFilename;
		this.amazonS3client.putObject(bucketName,  pathFile, file);
		log.info("AWS - Put object in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
		return keyFilename;
	}
	
	public void deletaArquivosStorageAmazon(String keyFilename){
		log.info("AWS - Delete object trying...");
		this.amazonS3client.deleteObject(bucketName, keyFilename);
		log.info("AWS - Delete object success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
	}
	
	public void deletaArquivosDiretorioStorageAmazon(String keyFilename, String folderName){
		log.info("AWS - Delete object in folder trying...");
		String pathFile = folderName +"/"+ keyFilename;
		this.amazonS3client.deleteObject(bucketName, pathFile);
		log.info("AWS - Delete object success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
	}
	
	public void criarDiretorioNoBucket(String folderName) {
		log.info("AWS - Create folder in bucket trying...");
		String SUFFIX = "/";
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(0);
		InputStream emptyContent = new ByteArrayInputStream(new byte[0]);		
		PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, folderName + SUFFIX, emptyContent, metadata);
		this.amazonS3client.putObject(putObjectRequest);
		log.info("AWS - Create folder success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - folder: " + folderName);
	}
	
	public InputStream getObjectNoBucketAmazon(String keyFilename) throws IOException{
		log.info("AWS - getObject folder in bucket trying...");
		S3Object object = this.amazonS3client.getObject(new GetObjectRequest(bucketName, keyFilename));
		InputStream objectData = object.getObjectContent();
		objectData.close();
		log.info("AWS - getObject folder success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - keyFilename: " + keyFilename);
		return objectData;
	}
	
	public InputStream getObjectNoDiretorioBucketAmazon(String keyFilename, String folderName) throws IOException{
		log.info("AWS - getObject folder in bucket trying...");
		String pathFile = folderName +"/"+ keyFilename;
		S3Object object = this.amazonS3client.getObject(new GetObjectRequest(bucketName, keyFilename));
		InputStream objectData = object.getObjectContent();
		objectData.close();
		log.info("AWS - getObject folder success in endpoint: " + endpoint + " - bucketname: " + bucketName + " - path/filename: " + pathFile);
		return objectData;
	}
	
	public boolean jaExisteObjetoNoDiretorioBucketAmazon(String keyFilename, String folderName){
		try {
			if(folderName == null || folderName.isEmpty()){
				this.amazonS3client.getObjectMetadata(bucketName, keyFilename);
			}else{
				this.amazonS3client.getObjectMetadata(bucketName, folderName +"/"+ keyFilename);
			}
	    } catch(AmazonServiceException e) {
	    	log.info("AWS - jaExisteObjetoNoDiretorioBucketAmazon()... false");
	        return false;
	    }
		log.info("AWS - jaExisteObjetoNoDiretorioBucketAmazon()... true");
	    return true;
	}

	private String randomizeFilenameUniqueObjectAmazon(String keyFilename, String folderName){
		int randomize = 32;
		String extension = keyFilename.substring(keyFilename.lastIndexOf(".")+1);
		String filename = keyFilename.substring(0, keyFilename.lastIndexOf("."));
		String novoFilename = null;
		
		String randomfilename = "";
		for (int j=0; j<randomize; j++) {
			randomfilename = "" + randomfilename + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
		}
		novoFilename = filename + "_" + randomfilename +"."+ extension;
		
		while (this.jaExisteObjetoNoDiretorioBucketAmazon(novoFilename, folderName)){
			randomfilename = "";
			for (int j=0; j<randomize; j++) {
				randomfilename = "" + randomfilename + (char)('a' + Integer.parseInt(Common.numberformat("" + Math.random()*25, 0)));
			}
			novoFilename = filename + "_" + randomfilename +"."+ extension;
		}
		log.info("AWS - randomizeFilenameUniqueObjectAmazon()... only novoFilename: " + novoFilename);
		return novoFilename;
	}
	
	public boolean validateDurationVideo(String keyFilename, String folderName) throws VideoILiketoException {		
		double result = 0;
		try {			
			IsoFile isoFile = new IsoFile(folderName +"/"+ keyFilename);
			MovieBox moov = isoFile.getMovieBox();
			MovieHeaderBox m = moov.getMovieHeaderBox();
			long min = (m.getDuration() / m.getTimescale() / 60);
			long sec = (m.getDuration() / m.getTimescale() % 60);
			result = (min + (sec * 0.01));
			log.info("AWS - Duration video: " + result + " - keyFilename: " + keyFilename);
			isoFile.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//valida duracao 2:00 dois minutos
		if(result <= 2.00){
			return true;
		}else{
			//deleta arquivo fisicamente
			try {
				this.deletaArquivosDiretorioStorageAmazon(keyFilename, folderName);
				log.warn("AWS - Delete file success - video: " + folderName +"/"+ keyFilename);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//lanca exception de erro duracao video
			throw new VideoILiketoException("Video error duration");
		}		
	}
}
